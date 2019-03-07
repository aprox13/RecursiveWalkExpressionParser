package expression;

public abstract class AbstractBinaryOperation implements CommonExpression {

    @SuppressWarnings("WeakerAccess")
    protected CommonExpression first, second;

    @SuppressWarnings("WeakerAccess")
    public AbstractBinaryOperation(CommonExpression firstI, CommonExpression secondI) {
        this.first = firstI;
        this.second = secondI;
        data = null;
    }

    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    protected abstract double makeOperation(double first, double second);

    protected abstract int makeOperation(int first, int second);

    @Override
    public double evaluate(double x) {
        return makeOperation(first.evaluate(x), second.evaluate(x));
    }


    @Override
    public int evaluate(int x) {
        return makeOperation(first.evaluate(x), second.evaluate(x));
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return makeOperation(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }
}
