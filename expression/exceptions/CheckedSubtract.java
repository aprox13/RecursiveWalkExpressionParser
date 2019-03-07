package expression.exceptions;

import expression.CommonExpression;

public class CheckedSubtract extends AbstractCheckedBinaryOperation {
    public CheckedSubtract(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected int makeOperation(int first, int second) {
        if (checkRange(first, second, this)) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.OVERFLOW, first + " - " + second);
        }
        return first - second;
    }
}
