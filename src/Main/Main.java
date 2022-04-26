package Main;

import AI.Matrix;
import AI.NeuralNetwork;
import Game.SnakePlayer;

import java.io.IOException;

public class Main {
    public static void main(String[] argv) throws IOException, ClassNotFoundException {
        GUIGraphics guiGraphics = new GUIGraphics(500, 500, new KeyboardHandler(), true);

        SnakePlayer snakePlayer = new SnakePlayer(20, 20, 70, guiGraphics.getInputSystem(), guiGraphics);
        snakePlayer.play();
//
//        NeuralNetwork neuralNetwork = new NeuralNetwork(2, 2, 3, 100, 1, 3, 4);
//        System.out.println(neuralNetwork.predict(new Matrix(new double[][]{{0.7}, {0}})));


//        NeuralNetwork neuralNetwork = NeuralNetwork.generateFirstPopulation(1, new int[]{10,10,5})[0];
//        System.out.println(neuralNetwork.predict(new Matrix(10, 1)));
//        NeuralNetwork.saveNetwork(neuralNetwork, "res/test.tmp");
//        NeuralNetwork neuralNetwork1 = NeuralNetwork.loadNetwork("res/test.tmp");

//        System.out.println(neuralNetwork.equals(neuralNetwork1));

    }
}
