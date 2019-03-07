package expression.exceptions;

import expression.AbstractUnaryOperation;
import expression.CommonExpression;
import expression.Const;

public class Log10 extends AbstractUnaryOperation {
    protected Log10(CommonExpression operand) {
        super(operand);
        if (operand instanceof Const) {
            checkRange(operand.evaluate(0));
        }
    }

    @Override
    protected int makeOperation(int operand) {
        checkRange(operand);
        return log(operand, 10);
    }


    private void checkRange(int operand) {
        if (operand <= 0) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.LOGARITHM_ERROR, "base is non-positive");
        }
    }

    private int log(int a, int b) {
        int res = 0;
        while (a >= b) {
            res++;
            a /= b;
        }
        return res;
    }
}
