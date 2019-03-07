package expression;

public class Add extends AbstractBinaryOperation {

    @SuppressWarnings("WeakerAccess")
    public Add(CommonExpression first, CommonExpression second) {
        super(first, second);
    }

    @Override
    protected double makeOperation(double first, double second) {
        return first + second;
    }

    @Override
    protected int makeOperation(int first, int second) {
        return first + second;
    }
}
