package AI;

public class Matrix {
    public final int rows;
    public final int cols;

    private final double[][] values;

    public Matrix(int rows, int cols) {
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

    public Matrix(Matrix matrix){
        this(matrix.values);
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
     * create and return a random {@code rows}-by-{@code cols} matrix with values between 0 and 1.
     */
    public static Matrix random(int rows, int cols) {
        Matrix matrix = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix.values[i][j] = Math.random();
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
}
