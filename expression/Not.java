package expression;

public class Not extends AbstractUnaryOperation {


    public Not(CommonExpression operand) {
        super(operand);
    }

    @Override
    protected int makeOperation(int x) {
        return ~x;
    }

}
