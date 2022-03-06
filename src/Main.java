import AI.Matrix;
import AI.NeuralNetwork;

public class Main {
    public static void main(String[] argv) {
//        GUIGraphics guiGraphics = new GUIGraphics(500, 500, new KeyboardHandler());
//
//        Thread snakePlayer = new Thread(new SnakePlayer(20, 20, 70, guiGraphics.getInputSystem(), guiGraphics));
//        snakePlayer.start();

        Matrix A = Matrix.randomMatrix(10, 10);
        double[][] doubles = new double[][] {{1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}};
        Matrix B = new Matrix(doubles);

//        System.out.println(A.mul(B));
        NeuralNetwork neuralNetwork = new NeuralNetwork(10, 2, 3, 100, 1, 3, 4);
        System.out.println(neuralNetwork);

    }
}

