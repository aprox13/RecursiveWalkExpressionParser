package expression.exceptions;

import com.sun.deploy.util.StringUtils;
import expression.TripleExpression;

public class CustomTest {

    public static void main(String[] args) {
        String exp = "2**3*5-(----7)";
        ExpressionParser parser = new ExpressionParser();

        TripleExpression expression = parser.parse(exp);


        System.out.printf("%-4s %s\n", "x", "f(x) = " + exp);
        for (int i = 0; i < 1; i++) {
            int res;
            try {
                res = expression.evaluate(i, 0, 0);

                System.out.printf("%-4d %d\n", i, res);
            } catch (ParserException e) {
                System.out.printf("%-4d %s\n", i, e.toString());
            }
        }

    }


}
