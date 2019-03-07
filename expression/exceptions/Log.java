package expression.exceptions;

import expression.CommonExpression;

public class Log extends AbstractCheckedBinaryOperation {
    public Log(CommonExpression firstI, CommonExpression secondI) {
        super(firstI, secondI);
    }

    @Override
    protected int makeOperation(int first, int second) {
        if (second <= 0 || first <= 0) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.LOGARITHM_ERROR, "non-positive base or value");
        }
        if (second == 1) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.LOG_BASE_IS_ONE);
        }
        if (first == 1) {
            return 0;
        }
        return log(first, second);
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
