package AI;

public class NeuralNetwork {
    private final Matrix[] wights;
    private final int[] layerSizes;

    public NeuralNetwork(int... layerSizes){
        if (layerSizes == null || layerSizes.length < 2) {
            throw new RuntimeException("There must be at least 2 layers.");
        }

        this.layerSizes = layerSizes;
        this.wights = new Matrix[layerSizes.length - 1];
        for (int i = 0; i < layerSizes.length - 1; i++) {
            wights[i] = Matrix.randomMatrix(layerSizes[i + 1], layerSizes[i]);
        }
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
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Matrix matrix: wights) {
            stringBuilder.append(matrix.toString()).append("\n\n");
        }
        return stringBuilder.toString();
    }
}
