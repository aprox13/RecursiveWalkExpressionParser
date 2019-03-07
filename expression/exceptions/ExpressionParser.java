package expression.exceptions;

import expression.*;
import expression.parser.Or;

public class ExpressionParser implements Parser {

    private int bracketsBalance = 0;
    private ParsingTokenizer tokenizer;


    private boolean isDoubleConstants(ParsingTokenizer.Token current, ParsingTokenizer.Token previous) {
        return current == ParsingTokenizer.Token.CONST && previous == ParsingTokenizer.Token.CONST;
    }

    private boolean isDoubleNotUnaryOperations(ParsingTokenizer.Token currentToken, ParsingTokenizer.Token previousToken) {
        return previousToken.isOperation() &&
                currentToken.isOperation() &&
                currentToken.notUnary();
    }

    private boolean isLastNotCloseBracket(ParsingTokenizer.Token currentToken, ParsingTokenizer.Token previousToken) {
        return currentToken == ParsingTokenizer.Token.END &&
                (previousToken.isOperation() || previousToken == ParsingTokenizer.Token.OPEN_BRACKET);
    }

    private boolean ifFirstNotUnaryOperationOrOpenBracket(ParsingTokenizer.Token currentToken, ParsingTokenizer.Token previousToken) {
        return (currentToken.isOperation() || currentToken == ParsingTokenizer.Token.CLOSE_BRACKET) &&
                currentToken.notUnary() &&
                previousToken == ParsingTokenizer.Token.UNDEFINED;
    }

    private boolean isNotUnaryOperationAfterBracket(ParsingTokenizer.Token currentToken, ParsingTokenizer.Token previousToken) {
        return currentToken.isOperation() &&
                currentToken.notUnary() &&
                previousToken == ParsingTokenizer.Token.OPEN_BRACKET;
    }

    private boolean isCloseBracketAfterOperation(ParsingTokenizer.Token currentToken, ParsingTokenizer.Token previousToken) {
        return previousToken.isOperation()
                && currentToken == ParsingTokenizer.Token.CLOSE_BRACKET;
    }

    private void check() {

        ParsingTokenizer.Token currentToken = tokenizer.getCurrentToken(),
                previousToken = tokenizer.getPreviousToken();

        if (currentToken == ParsingTokenizer.Token.OPEN_BRACKET) {
            bracketsBalance++;
        }
        if (currentToken == ParsingTokenizer.Token.CLOSE_BRACKET) {
            bracketsBalance--;
        }
        if (bracketsBalance < 0) {
            throw new ParsingException(ParsingException.Exceptions.WRONG_BRACKET_SEQUENCE, tokenizer.getBaseIndex() - 1, tokenizer.getExpression());
        }
        if (isDoubleConstants(currentToken, previousToken)) {
            throw new ParsingException(ParsingException.Exceptions.MISSED_OPERATOR, tokenizer.getBaseIndex() - 1, tokenizer.getExpression());
        }
        if (isDoubleNotUnaryOperations(currentToken, previousToken)) {
            throw new ParsingException(ParsingException.Exceptions.MISSED_ARGUMENT, tokenizer.getBaseIndex() - 1, tokenizer.getExpression());
        }
        if (isCloseBracketAfterOperation(currentToken, previousToken)) {
            throw new ParsingException(ParsingException.Exceptions.MISSED_ARGUMENT, tokenizer.getBaseIndex() - 1, tokenizer.getExpression());
        }
        if (ifFirstNotUnaryOperationOrOpenBracket(currentToken, previousToken)) {
            throw new ParsingException(ParsingException.Exceptions.MISSED_ARGUMENT, tokenizer.getBaseIndex() - 1, tokenizer.getExpression());
        }
        if (isNotUnaryOperationAfterBracket(currentToken, previousToken)) {
            throw new ParsingException(ParsingException.Exceptions.MISSED_ARGUMENT, tokenizer.getBaseIndex() - 1, tokenizer.getExpression());
        }
        if (isLastNotCloseBracket(currentToken, previousToken)) {
            throw new ParsingException(ParsingException.Exceptions.MISSED_ARGUMENT, tokenizer.getBaseIndex() - 1, tokenizer.getExpression());
        }
    }


    private void nextToken() {
        tokenizer.nextToken();
        check();
    }


    @Override
    public TripleExpression parse(String expression) throws ParserException {
        bracketsBalance = 0;
        tokenizer = new ParsingTokenizer(expression);
        nextToken();
        if (tokenizer.getCurrentToken() == ParsingTokenizer.Token.END) {
            return new Const(0);
        }
        CommonExpression result = lowPrimary(false);

        if (bracketsBalance == 0) {
            return result;
        }
        throw new ParsingException(ParsingException.Exceptions.WRONG_BRACKET_SEQUENCE, "missing " + bracketsBalance + " ')'", expression.length() - 1, expression);
    }


    private CommonExpression lowPrimary(boolean newToken) {
        return or(newToken);
    }

    private CommonExpression or(boolean newToken) {
        CommonExpression left = xor(newToken);

        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case OR:
                    left = new Or(left, xor(true));
                    break;
                default:
                    return left;
            }
        }
    }

    private CommonExpression xor(boolean newToken) {
        CommonExpression left = and(newToken);

        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case XOR:
                    left = new Xor(left, and(true));
                    break;
                default:
                    return left;
            }
        }
    }

    private CommonExpression and(boolean newToken) {
        CommonExpression left = addOrSubtract(newToken);

        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case AND:
                    left = new And(left, addOrSubtract(true));
                    break;
                default:
                    return left;
            }
        }
    }

    private CommonExpression addOrSubtract(boolean newToken) {
        CommonExpression left = multiplyOrDivide(newToken);

        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case PLUS:
                    left = new CheckedAdd(left, multiplyOrDivide(true));
                    break;
                case MINUS:
                    left = new CheckedSubtract(left, multiplyOrDivide(true));
                    break;
                default:
                    return left;
            }
        }
    }


    private CommonExpression multiplyOrDivide(boolean newToken) {
        CommonExpression left = logOrPow(newToken);

        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case MULTIPLY:
                    left = new CheckedMultiply(left, logOrPow(true));
                    break;
                case DIVIDE:
                    left = new CheckedDivide(left, logOrPow(true));
                    break;
                default:
                    return left;
            }
        }
    }

    private CommonExpression logOrPow(boolean newToken) {
        CommonExpression left = primary(newToken);
        while (true) {
            switch (tokenizer.getCurrentToken()) {
                case POW:
                    left = new Pow(left, primary(true));
                    break;
                case LOG:
                    left = new Log(left, primary(true));
                    break;
                default:
                    return left;
            }
        }
    }

    private CommonExpression primary(boolean newToken) {
        if (newToken) {
            nextToken();
        }


        switch (tokenizer.getCurrentToken()) {
            case COUNT:
                return new Count(primary(true));
            case CONST:
                int value = tokenizer.getLastValue();
                nextToken();
                return new Const(value);
            case VARIABLE:
                String name = tokenizer.getLastName();
                nextToken();
                return new Variable(name);
            case MINUS:
                return new Negative(primary(true));
            case NOT:
                return new Not(primary(true));
            case LOG10:
                return new Log10(primary(true));
            case POW10:
                return new Pow10(primary(true));
            case OPEN_BRACKET:
                CommonExpression expression = lowPrimary(true);
                if (tokenizer.getCurrentToken() == ParsingTokenizer.Token.CLOSE_BRACKET) {
                    nextToken();
                }
                return expression;
            default:
                return new Const(0);
        }


    }


}
