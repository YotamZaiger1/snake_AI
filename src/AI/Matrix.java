package AI;

import Main.Utils;

public class Matrix {
    public final int rows;
    public final int cols;

    private final double[][] values;

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

    public static void assertDimensionsAdd(Matrix A, Matrix B){
        if (A.rows != B.rows || A.cols != B.cols) throw new RuntimeException("Invalid dimensions.");
    }

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

    public void mutate(double mutationRate, double mutationStrength){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (Math.random() <= mutationRate){
                    values[i][j] += Utils.randGaussian() * mutationStrength;
                }
            }
        }
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

    public Matrix clone(){
        return new Matrix(values);
    }
}
