package expression.exceptions;

import expression.CommonExpression;

public class CheckedDivide extends AbstractCheckedBinaryOperation {
    public CheckedDivide(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected int makeOperation(int first, int second) {

        if (second == 0) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.DIVISION_BY_ZERO, first + " / " + 0);
        }

        if (checkRange(first, second, this)) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.OVERFLOW, first + " / " + second);
        }

        return first / second;
    }

}
