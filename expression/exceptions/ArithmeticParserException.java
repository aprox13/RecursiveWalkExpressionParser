package expression.exceptions;

public class ArithmeticParserException extends ParserException {

    public ArithmeticParserException(Exceptions e) {
        super(e.toString());
    }

    public ArithmeticParserException(Exceptions e, String message) {
        super(e.toString() + ": " + message);
    }


    enum Exceptions {
        OVERFLOW("overflow"),
        DIVISION_BY_ZERO("division by zero"),
        LOGARITHM_ERROR("Logarithm of empty or non-positive value"),
        POW_ERROR("Power exception"),
        LOG_BASE_IS_ONE("Logarithm base is 1");

        private final String name;

        Exceptions(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
