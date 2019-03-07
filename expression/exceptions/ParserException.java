package expression.exceptions;

public abstract class ParserException extends RuntimeException {

    private String exceptionMessage;

    public ParserException(String message) {
        this.exceptionMessage = message;
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }

    @Override
    public String getMessage() {
        return toString();
    }

    public void print() {
        System.err.println(this.toString());
    }
}
