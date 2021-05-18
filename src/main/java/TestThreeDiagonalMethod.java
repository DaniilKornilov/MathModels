import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;

public class TestThreeDiagonalMethod {
    public static void testThreeDiagonalMethod() {
        double[][] e = {
                {1, 0, 0},
                {0, 1, 0},
                {0, 0, 1}
        };

        double[][] temp1 = {
                {2, 3, 0},
                {1, 2, 3},
                {0, 1, 2}
        };

        double[][] temp2 = {
                {1, 2, 3},
                {1, 2, 3},
                {1, 2, 3}
        };

        double[][] v_expected_1 = {
                {-0.25, -0.25, 1.25},
                {2.25, -1, 0.75},
                {-0.25, -0.25, 1.25}
        };

        ArrayList<RealMatrix> a = new ArrayList<>(2);
        a.add(MatrixUtils.createRealMatrix(e));
        a.add(MatrixUtils.createRealMatrix(e));

        ArrayList<RealMatrix> b = new ArrayList<>(2);
        b.add(MatrixUtils.createRealMatrix(e));
        b.add(MatrixUtils.createRealMatrix(e));

        ArrayList<RealMatrix> c = new ArrayList<>(3);
        c.add(MatrixUtils.createRealMatrix(temp1));
        c.add(MatrixUtils.createRealMatrix(temp1));
        c.add(MatrixUtils.createRealMatrix(temp1));

        RealMatrix g = MatrixUtils.createRealMatrix(temp2);

        System.out.println("Test 1:");
        RealMatrix v = Utils.threeDiagonalMatrixAlgorithm(a, b, c, g);
        RealMatrix vMatrixExpected = MatrixUtils.createRealMatrix(v_expected_1);
        RealMatrix result = v.subtract(vMatrixExpected);
        System.out.println(Utils.getMax(result));

        double[][] zeros = {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0}
        };

        double[][] temp3 = {
                {1, 0, 0, 0},
                {0, 2, 0, 0},
                {0, 0, 2, 0},
                {0, 0, 0, 1}
        };

        double[][] temp4 = {
                {2, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 2}
        };

        double[][] temp5 = {
                {2, 1, 0, 0},
                {1, 2, 1, 0},
                {0, 1, 2, 1},
                {0, 0, 1, 2}
        };

        double[][] temp6 = {
                {9, 9, 14, 22},
                {21, 36, 40, 28},
                {39, 36, 32, 32},
                {11, 12, 8, 4}
        };

        double[][] v_expected_2 = {
                {0, 1, 2, 3},
                {4, 5, 6, 7},
                {8, 7, 6, 5},
                {4, 3, 2, 1}
        };

        a = new ArrayList<>(3);
        a.add(MatrixUtils.createRealMatrix(temp3));
        a.add(MatrixUtils.createRealMatrix(temp4));
        a.add(MatrixUtils.createRealMatrix(zeros));

        b = new ArrayList<>(3);
        b.add(MatrixUtils.createRealMatrix(temp4));
        b.add(MatrixUtils.createRealMatrix(temp3));
        b.add(MatrixUtils.createRealMatrix(temp4));

        c = new ArrayList<>(4);
        c.add(MatrixUtils.createRealMatrix(temp5));
        c.add(MatrixUtils.createRealMatrix(temp5));
        c.add(MatrixUtils.createRealMatrix(temp5));
        c.add(MatrixUtils.createRealMatrix(temp5));

        g = MatrixUtils.createRealMatrix(temp6);

        System.out.println("Test 2:");
        v = Utils.threeDiagonalMatrixAlgorithm(a, b, c, g);
        vMatrixExpected = MatrixUtils.createRealMatrix(v_expected_2);
        result = v.subtract(vMatrixExpected);
        System.out.println(Utils.getMax(result));
    }
}
