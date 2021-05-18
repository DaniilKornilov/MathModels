public class BalanceMethodNonLinear extends BalanceMethodConst {
    @Override
    protected double g1(double y) {
        return 2 * y + 1;
    }

    @Override
    protected double g2(double y) {
        return y + 2;
    }

    @Override
    protected double g3(double x) {
        return x + 1;
    }

    @Override
    protected double g4(double x) {
        return x + 2;
    }

    @Override
    protected double k1(double x, double y) {
        return x * x + y + 1;
    }

    @Override
    protected double k2(double x, double y) {
        return x + y * y + 1;
    }

    @Override
    protected double f(double x, double y) {
        return -2 * x - 2 * y;
    }

    @Override
    protected double u(double x, double y) {
        return x + y;
    }
}
