import AI.Matrix;
import AI.NeuralNetwork;
import Game.SnakePlayer;

public class Main {
    public static void main(String[] argv) {
        GUIGraphics guiGraphics = new GUIGraphics(500, 500, new KeyboardHandler());

        Thread snakePlayer = new Thread(new SnakePlayer(20, 20, 70, guiGraphics.getInputSystem(), guiGraphics));
        snakePlayer.start();

        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 2, 3, 100, 1, 3, 4);
        System.out.println(neuralNetwork.predict(new Matrix(new double[][]{{0.7}, {0}})));

    }
}
