import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class BalanceMethodConst {
    public RealMatrix getV(double x1, double x2, double y1, double y2, int nx, int ny, int kappa1) {
        double hx = (x2 - x1) / nx;
        double[] hxHelp = getHelpH(nx, hx);
        double[] x = getAxis(x1, nx, hx);
        double[] xHelp = new double[nx];
        for (int i = 0; i < nx; i++) {
            xHelp[i] = (x[i] + x[i + 1]) / 2;
        }

        double hy = (y2 - y1) / ny;
        double[] hyHelp = getHelpH(ny, hy);
        double[] y = getAxis(y1, ny, hy);
        double[] yHelp = new double[ny];
        for (int i = 0; i < ny; i++) {
            yHelp[i] = (y[i] + y[i + 1]) / 2;
        }

        double[][][] a = new double[ny][nx + 1][nx + 1];
        double[][][] b = new double[ny][nx + 1][nx + 1];
        double[][][] c = new double[ny + 1][nx + 1][nx + 1];
        double[][] g = new double[ny + 1][nx + 1];

        //уравнение
        for (int j = 1; j < ny; j++) {
            for (int i = 1; i < nx; i++) {
                a[j - 1][i][i] = -hxHelp[i] * k2(x[i], yHelp[j - 1]) / hy;
                b[j][i][i] = -hxHelp[i] * k2(x[i], yHelp[j]) / hy;

                c[j][i][i - 1] = -hyHelp[j] * k1(xHelp[i - 1], y[j]) / hx;
                c[j][i][i + 1] = -hyHelp[j] * k1(xHelp[i], y[j]) / hx;

                c[j][i][i] = -a[j - 1][i][i] - c[j][i][i - 1] - c[j][i][i + 1] - b[j][i][i];

                g[j][i] = hxHelp[i] * hyHelp[j] * f(x[i], y[j]);
            }
        }

        // 1 род сверху
        for (int i = 0; i < nx + 1; i++) {
            c[ny][i][i] = 1;
            g[ny][i] = g4(x[i]);
        }

        // 1 род снизу
        for (int i = 0; i < nx + 1; i++) {
            c[0][i][i] = 1;
            g[0][i] = g3(x[i]);
        }

        // 1 род справа
        for (int j = 0; j < ny + 1; j++) {
            c[j][nx][nx] = 1;
            g[j][nx] = g2(y[j]);
        }


        // 3 род слева
        for (int j = 1; j < ny; j++) {
            a[j - 1][0][0] = -hxHelp[0] * k2(x[0], yHelp[j - 1]) / hy; //u0 j-1
            b[j][0][0] = -hxHelp[0] * k2(x[0], yHelp[j]) / hy;  //u0 j+1

            c[j][0][1] = -hyHelp[j] * k1(xHelp[0], y[j]) / hx; //u1 j
            c[j][0][0] = -a[j - 1][0][0] - c[j][0][1] + hyHelp[j] * kappa1 - b[j][0][0]; //u0 j

            g[j][0] = hxHelp[0] * hyHelp[j] * f(x[0], y[j]) + hyHelp[j] * g1(y[j]); //right side
        }

        ArrayList<RealMatrix> aMatrix = new ArrayList<>(ny);
        ArrayList<RealMatrix> bMatrix = new ArrayList<>(ny);
        for (int i = 0; i < ny; i++) {
            aMatrix.add(MatrixUtils.createRealMatrix(a[i]));
            bMatrix.add(MatrixUtils.createRealMatrix(b[i]));
        }

        ArrayList<RealMatrix> cMatrix = new ArrayList<>(ny + 1);
        for (int i = 0; i < ny + 1; i++) {
            cMatrix.add(MatrixUtils.createRealMatrix(c[i]));
        }

        RealMatrix gMatrix = MatrixUtils.createRealMatrix(g);

        return Utils.threeDiagonalMatrixAlgorithm(aMatrix, bMatrix, cMatrix, gMatrix);
    }

    public double[][] getU(int i) {
        double[][] uExpected = new double[i + 1][i + 1];
        double h = (double) 1 / i;
        for (int j = 0; j < i + 1; j++) {
            for (int k = 0; k < i + 1; k++) {
                double x = 1 + k * h;
                double y = 1 + j * h;
                uExpected[j][k] = u(x, y);
            }
        }
        return uExpected;
    }

    protected double g1(double y) {
        return 1;
    }

    protected double g2(double y) {
        return 1;
    }

    protected double g3(double x) {
        return 1;
    }

    protected double g4(double x) {
        return 1;
    }

    protected double k1(double x, double y) {
        return 1;
    }

    protected double k2(double x, double y) {
        return 1;
    }

    protected double f(double x, double y) {
        return 0;
    }

    protected double u(double x, double y) {
        return 1;
    }

    private double[] getHelpH(int n, double h) {
        double[] hHelp = new double[n + 1];
        for (int i = 0; i < n + 1; i++) {
            if (i == 0 || i == n) {
                hHelp[i] = h / 2;
            } else {
                hHelp[i] = h;
            }
        }
        return hHelp;
    }

    private double[] getAxis(double l, int n, double h) {
        double[] array = new double[n + 1];
        for (int i = 0; i < n + 1; i++) {
            array[i] = l + i * h;
        }
        return array;
    }
}
