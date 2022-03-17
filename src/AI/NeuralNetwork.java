package AI;

public class NeuralNetwork {
    private final Matrix[] wights;
    private final int[] layerSizes;

    private Matrix outputVector;

    public NeuralNetwork(int... layerSizes){
        if (layerSizes == null || layerSizes.length < 2) {
            throw new RuntimeException("There must be at least 2 layers.");
        }

        this.layerSizes = layerSizes;
        this.wights = new Matrix[layerSizes.length - 1];
        for (int i = 0; i < layerSizes.length - 1; i++) {
            this.wights[i] = Matrix.randomMatrix(layerSizes[i + 1], layerSizes[i]);
        }
        this.outputVector = new Matrix(layerSizes[layerSizes.length - 1], 1);
    }

    public NeuralNetwork(Matrix... wights){
        if (wights == null || wights.length == 0) {
            throw new RuntimeException("Wights must contain at least 1 matrix.");
        }

        this.wights = wights;
        this.layerSizes = new int[wights.length + 1];
        this.layerSizes[0] = wights[0].cols;

        for (int i = 1; i < wights.length + 1; i++) {
            if (i != wights.length && wights[i - 1].rows != wights[i].cols) {
                throw new RuntimeException("Matrices sizes do not match.");
            }
            this.layerSizes[i] = wights[i - 1].rows;
        }
        this.outputVector = new Matrix(layerSizes[layerSizes.length - 1], 1);
    }

    private double activationFunction(double x){
        if (x < 0) return 0d;
        return x;
    }

    public int predict(Matrix inputVector){
        for (Matrix wight : wights) {
            inputVector = wight.mul(inputVector);
            for (int j = 0; j < inputVector.cols; j++) {
                inputVector.setXY(j, 0, activationFunction(inputVector.getXY(j, 0)));
            }
        }
        outputVector = inputVector;
        double max = outputVector.getXY(0, 0);
        int maxIndex = 0;
        for (int i = 1; i < outputVector.rows; i++) {
            double val = outputVector.getXY(i, 0);
            if (val > max) {
                max = val;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Matrix matrix: wights) {
            stringBuilder.append(matrix.toString()).append("\n\n");
        }
        return stringBuilder.toString();
    }

    public Matrix[] getWights() {
        return wights.clone();
    }

    public Matrix getOutputVector() {
        return outputVector.clone();
    }

    public int[] getLayerSizes() {
        return layerSizes.clone();
    }
}
