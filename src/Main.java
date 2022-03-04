import AI.Matrix;
import Game.SnakePlayer;

public class Main {
    public static void main(String[] argv) {
        GUIGraphics guiGraphics = new GUIGraphics(500, 500);

        Thread snakePlayer = new Thread(new SnakePlayer(20, 20, 70, new KeyboardHandler(), guiGraphics));
        snakePlayer.start();

//        Matrix A = Matrix.random(10, 10);
//        double[][] doubles = new double[][] {{1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}};
//        Matrix B = new Matrix(doubles);
//
//        System.out.println(A.mul(B));
    }
}

