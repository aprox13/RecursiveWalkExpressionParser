package expression;

public class Const implements CommonExpression {


    private String data = null;

    public String getData() {
        return data;

    }

    public void setData(String data) {
        this.data = data;
    }

    private Number number;

    public Const(Number number){
        this.number = number;
    }

    @Override
    public double evaluate(double x) {
        return number.doubleValue();
    }

    @Override
    public int evaluate(int x) {
        return number.intValue();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return number.intValue();
    }
}
