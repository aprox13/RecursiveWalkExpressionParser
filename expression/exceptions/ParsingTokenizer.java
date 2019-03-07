package expression.exceptions;


public class ParsingTokenizer {

    private static String ABS_MIN;
    private static String MAX;
    private static int MAX_VALUE;
    private static int MIN_VALUE;
    private int baseIndex;

    private int lastValue;

    private String expression;
    private String lastName;


    private Token currentToken,
            previousToken;


    private ParsingTokenizer(){
        currentToken = previousToken = Token.UNDEFINED;
        lastName = "";
        lastValue = 0;
        ABS_MIN = Integer.toString(Integer.MIN_VALUE).substring(1);
        MAX = Integer.toString(Integer.MAX_VALUE);
        MAX_VALUE = Integer.MAX_VALUE;
        MIN_VALUE = Integer.MIN_VALUE;
    }

    ParsingTokenizer(String expression) {
        this();
        this.expression = expression;
    }


    public Token getCurrentToken() {
        return currentToken;
    }

    public void nextToken() {
        skipWhiteSpaces();
        previousToken = currentToken;
        if (baseIndex >= expression.length()) {
            currentToken = Token.END;

            return;
        }
        char current = expression.charAt(baseIndex++);
        currentToken = Token.parse(current);


        if (currentToken != Token.UNDEFINED) {
            if (baseIndex < expression.length() - 1) {
                char next = expression.charAt(baseIndex);
                if (next == current && current == '/') {
                    currentToken = Token.LOG;
                    baseIndex++;
                } else if (current == next && current == '*') {
                    baseIndex++;
                    currentToken = Token.POW;
                }
            }
            return;
        }

        if (Character.isLetter(current)) {
            nextName();
            return;
        }

        if (Character.isDigit(current)) {
            nextValue();
            return;
        }
        throw new ParsingException(ParsingException.Exceptions.UNSUPPORTED_ENTRY, "char '" + current + "'", baseIndex, expression);
    }


    private void nextName() {

        int begin = baseIndex - 1;
        while (baseIndex < expression.length() &&
                (Character.isDigit(expression.charAt(baseIndex)) ||
                        Character.isLetter(expression.charAt(baseIndex)))) {
            baseIndex++;
        }

        String name = expression.substring(begin, baseIndex);

        if (name.equals(Token.COUNT.description)) {
            currentToken = Token.COUNT;
            return;
        }

        if (name.equals(Token.LOG10.description)) {
            currentToken = Token.LOG10;
            return;
        }

        if (name.equals(Token.POW10.description)) {
            currentToken = Token.POW10;
            return;
        }

        if (name.equals("x") ||
                name.equals("y") ||
                name.equals("z")) {
            lastName = name;
            currentToken = Token.VARIABLE;
            return;
        }

        throw new ParsingException(ParsingException.Exceptions.UNSUPPORTED_ENTRY, "name: '" + name + "'", begin, expression);
    }

    private void nextValue() {

        int begin = baseIndex - 1;
        while (baseIndex < expression.length() &&
                Character.isDigit(expression.charAt(baseIndex))) {
            baseIndex++;
        }

        String num = expression.substring(begin, baseIndex);

        // String numString = (makeNegate ? "-" : "") + expression.substring(begin, baseIndex);
        try {
            lastValue = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            if (ABS_MIN.equals(num) &&
                    previousToken == Token.MINUS) {
                lastValue = MIN_VALUE;
            } else {
                throw new ArithmeticParserException(ArithmeticParserException.Exceptions.OVERFLOW);
            }
        }
        currentToken = Token.CONST;
    }


    private void skipWhiteSpaces() {
        while (baseIndex < expression.length() &&
                Character.isWhitespace(expression.charAt(baseIndex))) {
            baseIndex++;
        }
    }

    public int getLastValue() {
        return lastValue;
    }

    public String getLastName() {
        return lastName;
    }

    public Token getPreviousToken() {
        return previousToken;
    }

    public String getExpression() {
        return expression;
    }

    public int getBaseIndex() {
        return baseIndex;
    }


    enum Token {
        PLUS('+', "operator"),
        MINUS('-', "minus"),
        MULTIPLY('*', "operator"),
        DIVIDE('/', "operator"),
        POW10(' ', "pow10"),
        LOG10(' ', "log10"),
        POW(' ', "pow"),
        LOG(' ', "log"),
        COUNT(' ', "count"),
        AND('&', "and"),
        XOR('^', "xor"),
        OR('|', "or"),
        NOT('~', "not"),
        OPEN_BRACKET('(', "bracket"),
        CLOSE_BRACKET(')', "bracket"),
        VARIABLE(' ', "value"),
        CONST(' ', "value"),
        END(' ', "end"),
        UNDEFINED(' ', "fail");

        final char key;
        final String description;

        Token(char key, String meaning) {
            this.key = key;
            this.description = meaning;
        }

        static Token parse(char ch) {
            for (Token t : Token.values()) {
                if (t.key == ch) {
                    return t;
                }
            }
            return Token.UNDEFINED;
        }

        public boolean isOperation() {
            return this != END &&
                    this != UNDEFINED &&
                    this != CONST &&
                    this != VARIABLE &&
                    this != OPEN_BRACKET &&
                    this != CLOSE_BRACKET;
        }

        public boolean notUnary() {
            return this != MINUS &&
                    this != LOG10 &&
                    this != NOT &&
                    this != COUNT &&
                    this != POW10;
        }
    }

}
