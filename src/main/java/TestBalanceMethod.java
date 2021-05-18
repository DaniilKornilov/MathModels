import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

public class TestBalanceMethod {
    public static void testBalanceMethod() {
        System.out.println("---------------------------------");
        System.out.println("Const test: ");
        for (int i = 4; i < 256; i *= 2) {
            System.out.println("N = " + i);
            printResult(new BalanceMethodConst(), i, 1);
        }

        System.out.println("---------------------------------");
        System.out.println("Linear test: ");
        for (int i = 4; i < 256; i *= 2) {
            System.out.println("N = " + i);
            printResult(new BalanceMethodLinear(), i, 1);
        }

        System.out.println("---------------------------------");
        System.out.println("Non-linear test: ");
        for (int i = 4; i < 256; i *= 2) {
            System.out.println("N = " + i);
            printResult(new BalanceMethodNonLinear(), i, 3);
        }

        System.out.println("---------------------------------");
        System.out.println("Custom test: ");
        for (int i = 4; i < 256; i *= 2) {
            System.out.println("N = " + i);
            printResult(new BalanceMethodCustom(), i, 2);
        }
    }

    private static void printResult(BalanceMethodConst balanceMethodConst, int i, int kappa1) {
        RealMatrix u = MatrixUtils.createRealMatrix(balanceMethodConst.getU(i));
        RealMatrix v = balanceMethodConst.getV(1, 2, 1, 2, i, i, kappa1);
        RealMatrix result = v.subtract(u);
        System.out.println(Utils.getMax(result));
    }
}
