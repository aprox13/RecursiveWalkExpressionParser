package expression.exceptions;

import expression.CommonExpression;
import expression.Negative;

public class CheckedNegate extends Negative {
    public CheckedNegate(CommonExpression firstI) {
        super(firstI);
    }

    @Override
    protected int makeOperation(int operand) {

        if (operand == Integer.MIN_VALUE) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.OVERFLOW, "-("+ operand + ")");
        }
        return super.makeOperation(operand);
    }
}
