package AI;

import Main.Utils;

import java.util.Arrays;

public class Matrix {
    public final int rows;
    public final int cols;

    public final double[][] values;

    public Matrix(int rows, int cols) {
        if (rows < 0 || cols < 0) {
            throw new RuntimeException("Matrix dimensions must be positive.");
        }
        this.rows = rows;
        this.cols = cols;

        values = new double[rows][cols];
    }

    public Matrix(double[][] values) {
        this.rows = values.length;
        this.cols = values[0].length;

        this.values = new double[rows][cols];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(values[i], 0, this.values[i], 0, cols);
        }
    }

    /**
     * Assert that the matrices have valid dimensions for adding them together.
     */
    public static void assertDimensionsAdd(Matrix A, Matrix B){
        if (A.rows != B.rows || A.cols != B.cols) throw new RuntimeException("Invalid dimensions.");
    }

    /**
     * Assert that the matrices have valid dimensions for multiplying {@code A} by {@code B}.
     */
    public static void assertDimensionsMul(Matrix A, Matrix B){
        if (A.cols != B.rows) throw new RuntimeException("Invalid dimensions.");
    }

    public double getXY(int x, int y){
        return values[x][y];
    }

    public void setXY(int x, int y, double value){
        this.values[x][y] = value;
    }

    /**
     * create and return a random {@code rows}-by-{@code cols} matrix with values between -1 and 1.
     */
    public static Matrix randomMatrix(int rows, int cols) {
        Matrix matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.values[i][j] = (Math.random() - 0.5)*2;  // random number between -1 and 1
            }
        }
        return matrix;
    }

    // operations

    /**
     * @return C = this + matrix
     */
    public Matrix plus(Matrix matrix){
        assertDimensionsAdd(this, matrix);
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.values[i][j] = this.getXY(i, j) + matrix.getXY(i, j);
            }
        }

        return result;
    }

    /**
     * @return C = this - matrix
     */
    public Matrix minus(Matrix matrix){
        assertDimensionsAdd(this, matrix);
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result.values[i][j] = this.getXY(i, j) - matrix.getXY(i, j);
            }
        }

        return result;
    }

    /**
     * @return C = this x matrix
     */
    public Matrix mul(Matrix matrix){
        assertDimensionsMul(this, matrix);
        Matrix result = new Matrix(this.rows, matrix.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < matrix.cols; j++) {
                for (int k = 0; k < this.cols; k++) {
                    result.values[i][j] += this.getXY(i, k) * matrix.getXY(k, j);
                }
            }
        }

        return result;
    }

    public Matrix mutated(double mutationRate, double mutationStrength){
        Matrix mutation = new Matrix(this.rows, this.cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Math.random() < mutationRate){
                    mutation.values[i][j] = this.values[i][j] + Utils.randGaussian() * mutationStrength;
                }
            }
        }
        return mutation;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                stringBuilder.append("%9.4f".formatted(this.getXY(i, j)));
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return Arrays.deepEquals(values, matrix.values);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(values);
    }

    public Matrix clone(){
        return new Matrix(values);
    }
}
