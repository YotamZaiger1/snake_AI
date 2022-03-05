import AI.Matrix;

public class Main {
    public static void main(String[] argv) {
//        RunGame.GuiPlayer guiGraphics = new RunGame.GuiPlayer(500, 500);
//
//        Thread snakePlayer = new Thread(new SnakeGame(20, 20, 70, new RunGame.KeyboardHandler(), guiGraphics));
//        snakePlayer.start();

        Matrix A = Matrix.random(10, 10);
        double[][] doubles = new double[][] {{1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}};
        Matrix B = new Matrix(doubles);

        System.out.println(A.mul(B));
    }
}

