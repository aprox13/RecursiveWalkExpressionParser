package expression;

public class Negative extends AbstractUnaryOperation {

    public Negative(CommonExpression firstI) {
        super(firstI);
    }

    @Override
    protected int makeOperation(int operand) {
        return -operand;
    }

}
