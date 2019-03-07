package expression.exceptions;

public class ParsingException extends ParserException {

    public ParsingException(Exceptions e, int index, String expression) {
        super(e.toString() + ", at " + index + " in " + expression);
    }


    public ParsingException(Exceptions e, String data, int index, String expression) {
        super(e.toString() + ": " + data + ", at " + index + " in " + expression);
    }

    enum Exceptions {
        MISSED_ARGUMENT("Missed argument"),
        WRONG_BRACKET_SEQUENCE("Wrong bracket sequence"),
        MISSED_OPERATOR("Missed operator"),
        UNSUPPORTED_ENTRY("Unsupported entry");

        private String simpleName;

        Exceptions(String simpleName) {
            this.simpleName = simpleName;
        }

        @Override
        public String toString() {
            return simpleName;
        }
    }
}
