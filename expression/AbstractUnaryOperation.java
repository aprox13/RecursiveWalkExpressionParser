package expression;

public abstract class AbstractUnaryOperation implements CommonExpression {

    private final CommonExpression operand;

    protected AbstractUnaryOperation(CommonExpression operand) {
        this.operand = operand;
    }

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    protected abstract int makeOperation(int operand);

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public int evaluate(int x) {
        return makeOperation(operand.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return makeOperation(operand.evaluate(x, y, z));
    }

}
