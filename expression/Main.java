package expression;

public class Main {

    public static void main(String[] args) {
       /* if(args.length != 1){
            System.err.printf("Expected 1 argument: \"x\" for expression x*x−2y+z. Found %d arguments.", args.length);
            return;
        }*/
        System.out.println(new Subtract(
                new Multiply(
                        new Variable("x"),
                        new Variable("x")
                ),
                new Subtract(
                        new Multiply(
                                new Const(2),
                                new Variable("y")
                        ), new Variable("z")
                )
        ).evaluate(2, 0, 10));
    }
}
