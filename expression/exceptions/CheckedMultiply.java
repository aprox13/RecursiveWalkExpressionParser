package expression.exceptions;

import expression.CommonExpression;

public class CheckedMultiply extends AbstractCheckedBinaryOperation {
    public CheckedMultiply(CommonExpression first, CommonExpression second) {
        super(first, second);
        //System.out.println(this.getClass().getSimpleName());

    }

    @Override
    protected int makeOperation(int first, int second) {
        if (checkRange(first, second, this)) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.OVERFLOW, first + " * " + second);
        }
        return first * second;
    }
}
