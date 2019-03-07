package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.CommonExpression;

public abstract class AbstractCheckedBinaryOperation extends AbstractBinaryOperation {
    public AbstractCheckedBinaryOperation(CommonExpression firstI, CommonExpression secondI) {
        super(firstI, secondI);
    }


    @Deprecated
    @Override
    protected double makeOperation(double first, double second) {
        return 0;
    }


    private int abs(int a) {
        return a < 0 ? -a : a;
    }

    private boolean checkAbs(int a) {
        return a != Integer.MIN_VALUE;
    }

    /*
    * @return true if result is out of range
    * */
    protected boolean checkRange(final int first, final int second, AbstractCheckedBinaryOperation operation) {
        if (operation instanceof CheckedAdd) {

            if (first == 0 || second == 0) {
                return false;
            }
            if (first > 0 && second > 0 && Integer.MAX_VALUE - first >= second) {
                return false;
            }
            if (first < 0 && second > 0 ||
                    first > 0 && second < 0) {
                return false;
            }
            if (first < 0 && second < 0 && checkAbs(second)) {
                if (first >= Integer.MIN_VALUE + abs(second)) {
                    return false;
                }
            }
            return true;
        }
        if (operation instanceof CheckedDivide) {
            return first != 0 && first == Integer.MIN_VALUE && second == -1;
        }

        if (operation instanceof CheckedSubtract) {
            if ((first == 0 && checkAbs(second)) || second == 0) {
                return false;
            }
            if (first > 0 && second > 0) {
                return false;
            }
            if (first < 0 && second > 0 && first >= Integer.MIN_VALUE + second) {
                return false;
            }
            if (first > 0 && second < 0 && checkAbs(second)) {
                if (Integer.MAX_VALUE - first >= abs(second)) {
                    return false;
                }
            }
            return first >= 0 || second >= 0;
        }

        if (operation instanceof CheckedMultiply) {
            if (first == 0 || second == 0) {
                return false;
            }

            if (first > 0 && second > 0 && Integer.MAX_VALUE / second >= first) {
                return false;
            }

            if (first < 0 && second < 0) {
                if (!checkAbs(first) || !checkAbs(second)) {
                    return true;
                }
                int absFirst = abs(first);
                int absSecond = abs(second);
                if (Integer.MAX_VALUE / absSecond >= absFirst) {
                    return false;
                }
            }
            if (first > 0 && second < 0) {
                if (Integer.MIN_VALUE / first <= second) {
                    return false;
                }
            }
            if (first < 0 && second > 0) {
                if (Integer.MIN_VALUE / second <= first) {
                    return false;
                }
            }
        }


        if (operation instanceof Pow) {
            int a = first;
            for (int i = 1; i <= second; i++) {
                if (i <= second - 1 && (a > Integer.MAX_VALUE / abs(first) || Integer.MIN_VALUE / abs(first) > a)) {
                    return true;
                }
                a *= first;
            }
            return false;
        }


        return true;
    }


}
