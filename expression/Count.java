package expression;

public class Count extends AbstractUnaryOperation {
    public Count(CommonExpression firstI) {
        super(firstI);
    }

    @Override
    protected int makeOperation(int operand) {
        return Integer.bitCount(operand);
    }

}
