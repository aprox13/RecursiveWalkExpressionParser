package expression.parser;

import expression.*;

public class ExpressionParser implements Parser {
    private String expression;
    private int size;
    private int baseIndex;

    private int val;
    private String name;

    private Token curToken;

    public ExpressionParser() {
        size = 0;
        baseIndex = 0;
        expression = "";
    }

    @Override
    public CommonExpression parse(String base) {
        expression = base;
        size = base.length();
        baseIndex = 0;
        curToken = Token.FAIL;
        while (baseIndex < size && curToken == Token.FAIL) {
            curToken = getToken();
        }
        if (curToken == Token.END) {
            return new Const(0);
        }
        return or(false);
    }

    private Token getToken() {
        skipWhiteSpace();
        if (baseIndex >= size) {
            return Token.END;
        }
        char ch = expression.charAt(baseIndex++);
        curToken = Token.findToken(ch);

        if (curToken != Token.FAIL) {
            return curToken;
        }
        if (Character.isLetter(ch)) {
            int start = baseIndex - 1;
            while (baseIndex < size &&
                    Character.isLetter(expression.charAt(baseIndex))) {
                baseIndex++;
            }
            String str = expression.substring(start, baseIndex);

            if (str.equals("count")) {
                return Token.COUNT;
            }
            if (str.equals("x") || str.equals("y")
                    || str.equals("z")) {
                name = str;
                return Token.VARIABLE;
            }
        }


        if (Character.isDigit(ch)) {
            int start = baseIndex - 1;
            while (baseIndex < size &&
                    Character.isDigit(expression.charAt(baseIndex))) {
                baseIndex++;
            }
            val = Integer.parseUnsignedInt(expression.substring(start, baseIndex));
            return Token.CONST;
        }
        return curToken;
    }

    private CommonExpression makeAddOrSub(boolean newToken) {
        CommonExpression left = makeMulOrDiv(newToken);

        do {
            switch (curToken) {
                case PLUS:
                    left = new Add(
                            left,
                            makeMulOrDiv(true)
                    );
                    break;
                case MINUS:
                    left = new Subtract(
                            left,
                            makeMulOrDiv(true)
                    );
                    break;
                default:
                    return left;
            }
        } while (true);
    }

    private CommonExpression makeMulOrDiv(boolean newToken) {
        CommonExpression left = primary(newToken);

        do {
            switch (curToken) {
                case MULTIPLY:
                    left = new Multiply(
                            left,
                            primary(true)
                    );
                    break;
                case DIVIDE:
                    left = new Divide(
                            left,
                            primary(true)
                    );
                    break;
                default:
                    return left;
            }
        } while (true);
    }

    private CommonExpression primary(boolean newToken) {
        if (newToken) {
            curToken = getToken();
        }

        switch (curToken) {
            case COUNT:
                return new Count(primary(true));
            case CONST:
                int v = val;
                curToken = getToken();
                return new Const(v);
            case VARIABLE:
                String n = name;
                curToken = getToken();
                return new Variable(n);
            case MINUS:
                return new Negative(primary(true));
            case NOT:
                return new Not(primary(true));
            case OPEN_BRACKET:
                CommonExpression e = or(true);
                if (curToken == Token.CLOSE_BRACKET) {
                    curToken = getToken();
                }
                return e;
            default:
                return new Const(0);
        }
    }

    private CommonExpression and(boolean newToken) {
        CommonExpression left = makeAddOrSub(newToken);

        do {
            switch (curToken) {
                case AND:
                    left = new And(
                            left,
                            makeAddOrSub(true)
                    );
                    break;
                default:
                        return left;
            }
        } while (true);
    }

    private CommonExpression xor(boolean newToken) {
        CommonExpression left = and(newToken);

        do {
            switch (curToken) {
                case XOR:
                    left = new Xor(
                            left,
                            and(true)
                    );
                    break;
                default:
                    return left;
            }
        } while (true);
    }

    private CommonExpression or(boolean newToken) {
        CommonExpression left = xor(newToken);

        do {
            switch (curToken) {
                case OR:
                    left = new Or(
                            left,
                            xor(true)
                    );
                    break;
                default:
                    return left;
            }
        } while (true);
    }

    private void skipWhiteSpace() {
        while (baseIndex < size &&
                Character.isWhitespace(expression.charAt(baseIndex))) {
            baseIndex++;
        }
    }


    private enum Token {
        PLUS('+'),
        OPEN_BRACKET('('),
        CLOSE_BRACKET(')'),
        MINUS('-'),
        MULTIPLY('*'),
        DIVIDE('/'),
        OR('|'),
        XOR('^'),
        AND('&'),
        NOT('~'),
        END((char) -1),
        FAIL(' '),
        VARIABLE(' '),
        CONST(' '),
        COUNT(' ');

        private final char key;

        Token(char key) {
            this.key = key;
        }

        static Token findToken(char ch) {
            for (Token t : Token.values()) {
                if (t.key == ch) {
                    return t;
                }
            }
            return Token.FAIL;
        }
    }
}
