package Matrix;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixTest {
    @Test
    void mult() {
        // Given a Matrix
        double[][] a_vals = {
                {1, 2, 3},
                {4, 5, 6}
        };
        Matrix matA = new Matrix(2, 3, a_vals);
        // And another Matrix
        double[][] b_vals = {
                {7, 8},
                {9, 10},
                {11, 12}
        };
        Matrix matB = new Matrix(3, 2, b_vals);
        // And the result matrix
        double[][] res = {
                {58, 64},
                {139, 154}
        };
        Matrix result = new Matrix(2, 2, res);
        // Multiply them together
        Matrix matC = matA.mult(matB);
        // Now assert if they are the same
        assertEquals(result, matC);
    }

    @Test
    void pow() {
        // Given an initial matrix
        double[][] vals = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15,16}
        };
        Matrix A = new Matrix(4, 4, vals);
        // And the expected value
        double[][] exp = {
                {90 , 100, 110, 120},
                {202, 228, 254, 280},
                {314, 356, 398, 440},
                {426, 484, 542, 600}
        };
        Matrix expected = new Matrix(4, 4, exp);
        // Get the matrix to the power of 2
        Matrix B = A.pow(2);
        // Assert if they are equal
        assertEquals(expected, B);

        // Given another expected array
        double[][] exp2 = {
                {3140, 3560, 3980, 4400},
                {7268, 8232, 9196, 10160},
                {11396, 12904, 14412, 15920},
                {15524, 17576, 19628, 21680}
        };
        Matrix expected2 = new Matrix(4, 4, exp2);
        // Get the matrix to the power of 3
        Matrix C = A.pow(3);
        // Assert if they are equal
        assertEquals(expected2, C);
    }

}