package expression.exceptions;

import expression.CommonExpression;

public class Pow extends AbstractCheckedBinaryOperation {
    public Pow(CommonExpression firstI, CommonExpression secondI) {
        super(firstI, secondI);
    }

    @Override
    protected int makeOperation(int first, int second) {

        if (first == second && first == 0) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.POW_ERROR, "zero pow and base");
        }

        if (second == 1) {
            return first;
        }

        if (second < 0) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.POW_ERROR, "negative pow");
        }

        if (first == 0) {
            return 0;
        }

        if (second == 0) {
            return 1;
        }

        if (checkRange(first, second, this)) {
            throw new ArithmeticParserException(ArithmeticParserException.Exceptions.OVERFLOW);
        }
        return pow(first, second);
    }

    private int pow(int first, int second) {
        int res = 1;
        while (second > 0) {
            res *= first;
            second--;
        }
        return res;
    }


}
