package expression.parser;

import expression.AbstractBinaryOperation;
import expression.CommonExpression;

public class Or extends AbstractBinaryOperation {

    public Or(CommonExpression firstI, CommonExpression secondI) {
        super(firstI, secondI);
    }

    @Override
    protected double makeOperation(double first, double second) {
        System.err.printf("Cannot make %f | %f, expected int, found double\n", first, second);
        return 0;
    }

    @Override
    protected int makeOperation(int first, int second) {
        return first | second;
    }
}
