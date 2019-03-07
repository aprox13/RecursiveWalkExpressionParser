package expression;

public class Variable implements CommonExpression {
    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private final String name;


    private String data = null;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @SuppressWarnings("WeakerAccess")
    public Variable(String name) {
        this.name = name;
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if(name.toLowerCase().equals("x")){
            return x;
        }
        if(name.toLowerCase().equals("y")){
            return y;
        }
        return z;
    }
}
