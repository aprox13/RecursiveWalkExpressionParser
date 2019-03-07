package expression.exceptions;

import expression.AbstractUnaryOperation;
import expression.CommonExpression;

public class Pow10 extends AbstractUnaryOperation {
    protected Pow10(CommonExpression operand) {
        super(operand);
    }

    @Override
    protected int makeOperation(int operand) {
        checkRange(operand);

        return pow(operand, false);
    }

    private void checkRange(int operand) {
        if (Math.log10(Integer.MAX_VALUE) < operand) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.OVERFLOW);
        }
        if (operand < 0) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.POW_ERROR, "negative pow");
        }
    }

    private int pow(int operand, boolean useMath) {
        if (useMath) {
            return (int) Math.pow(10, operand);
        } else {
            int res = 1;
            for (int i = 0; i < operand; i++) {
                res *= 10;
            }
            return res;
        }
    }

}
