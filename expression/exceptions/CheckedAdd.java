package expression.exceptions;

import expression.CommonExpression;

public class CheckedAdd extends AbstractCheckedBinaryOperation {
    public CheckedAdd(CommonExpression first, CommonExpression second) {
        super(first, second);
    }


    @Override
    protected int makeOperation(int first, int second) {
        if (checkRange(first, second, this)) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.OVERFLOW);
        }
        return first + second;
    }


}
