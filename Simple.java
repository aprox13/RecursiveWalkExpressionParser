public class Simple {


    Simple() {
        run();
    }

    public static void main(String[] args) {

       /* ExpressionParser parser = new ExpressionParser();
        System.out.println(parser.parse("10").evaluate(0, 0,0));
    */
        expression.exceptions.ExpressionParser parser = new expression.exceptions.ExpressionParser();
        String exp = " x*y+(z-1   )/10";
        //((log10 ((((z) / (x)) + ((pow10 (813913062)) + (1687272696))) - ((425428707)))) / (((x)))) - ((x) + (242435711))
        String exp2 = "(2) * (2)";
        int y = 1978272111;
        int a = -492307943;
        System.out.println(parser.parse(exp));
        int i = 0;
    }

    void run() {
        System.out.println(reverseWords("                "));
    }

    public String reverseWords(String s) {
        if (s.length() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder(), tmp = new StringBuilder();

        int i = 0;
        while (i < s.length()) {

            while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
                i++;
            }
            int begin = i;
            while (i < s.length() && !Character.isWhitespace(s.charAt(i))) {
                i++;
            }
            if (begin != i) {
                sb.insert(0, s.substring(begin, i) + " ");
            }

        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    static class A {

    }

    static class B extends A {
    }

    static class C extends A {
    }

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    static class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            ListNode res = new ListNode(0);
            ListNode ans = res;
            int f, s, r = 0;
            while (l1 != null || l2 != null) {
                if (l1 != null) {
                    f = l1.val;
                } else {
                    f = 0;
                }
                if (l2 != null) {
                    s = l2.val;
                } else {
                    s = 0;
                }
                int v = r + f + s;
                res.val = v % 10;
                if (v > 9) {
                    r = v - 10;
                } else {
                    r = 0;
                }
                res.next = new ListNode(0);
                res = res.next;
                l1 = l1.next;
                l2 = l2.next;
            }
            return ans;
        }
    }

}
