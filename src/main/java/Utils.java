import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class Utils {
    public static double getMax(RealMatrix realMatrix) {
        int N = realMatrix.getRowDimension();
        int M = realMatrix.getColumnDimension();
        double[][] matrix = realMatrix.getData();
        double maxElement = Math.abs(matrix[0][0]);
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                maxElement = Math.max(maxElement, Math.abs(matrix[i][j]));
            }
        }
        return maxElement;
    }

    public static RealMatrix threeDiagonalMatrixAlgorithm(ArrayList<RealMatrix> a, ArrayList<RealMatrix> b, ArrayList<RealMatrix> c, RealMatrix g) {
        int nY = c.size() - 1;

        ArrayList<RealMatrix> alpha = new ArrayList<>(nY);
        for (int i = 0; i < nY; i++) {
            alpha.add(MatrixUtils.createRealMatrix(new double[nY + 1][nY + 1]));
        }

        RealMatrix beta = MatrixUtils.createRealMatrix(new double[nY][nY + 1]);

        RealMatrix cInverted = new LUDecomposition(c.get(0)).getSolver().getInverse();
        alpha.set(0, cInverted.scalarMultiply(-1).multiply(b.get(0)));
        beta.setRowVector(0, cInverted.operate(g.getRowVector(0)));

        for (int i = 1; i < nY; i++) {
            RealMatrix k = new LUDecomposition(a.get(i - 1).multiply(alpha.get(i - 1)).add(c.get(i))).getSolver().getInverse();
            alpha.set(i, k.scalarMultiply(-1).multiply(b.get(i)));
            beta.setRowVector(i, k.operate(g.getRowVector(i).subtract(a.get(i - 1).operate(beta.getRowVector(i - 1)))));
        }

        RealMatrix v = MatrixUtils.createRealMatrix(new double[nY + 1][nY + 1]);
        RealMatrix temp = new LUDecomposition(a.get(a.size() - 1).multiply(alpha.get(nY - 1)).add(c.get(nY))).getSolver().getInverse();
        v.setRowVector(nY, temp.operate(g.getRowVector(g.getRowDimension() - 1).subtract(a.get(a.size() - 1).operate(beta.getRowVector(nY - 1)))));

        for (int i = nY - 1; i > -1; i--) {
            v.setRowVector(i, alpha.get(i).operate(v.getRowVector(i + 1)).add(beta.getRowVector(i)));
        }

        return v;
    }
}
