package Game;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class SnakePlayer implements Runnable {
    final long milliDelay;
    public Snake snake;  // TODO: fix access modifier
    InputSystem inputSystem;
    GraphicSystem graphicSystem;
    int fitness = 0;

    public SnakePlayer(int sizeX, int sizeY, long milliDelay, InputSystem inputSystem) {
        this(new Snake(sizeX, sizeY), milliDelay, inputSystem);
    }

    public SnakePlayer(Snake snake, long milliDelay, InputSystem inputSystem){
        this.snake = snake;
        if (milliDelay < 0) milliDelay = 0;
        this.milliDelay = milliDelay;
        this.inputSystem = inputSystem;
        this.graphicSystem = new noGraphics();
    }

    public SnakePlayer(int sizeX, int sizeY, long milliDelay, InputSystem inputSystem, GraphicSystem graphicSystem) {
        this(new Snake(sizeX, sizeY), milliDelay, inputSystem, graphicSystem);
    }

    public SnakePlayer(Snake snake, long milliDelay, InputSystem inputSystem, GraphicSystem graphicSystem) {
        this.snake = snake;
        if (milliDelay < 0) milliDelay = 0;
        this.milliDelay = milliDelay;
        this.inputSystem = inputSystem;
        this.graphicSystem = graphicSystem;
    }

    @Override
    public void run() {
        graphicSystem.setSnake(snake);
        graphicSystem.initialize();
        while (snake.getIsAlive()) {
            HashSet<Byte> inputs = inputSystem.getInputs(snake);
            if (inputs != null) {
                if (inputs.contains(InputSystem.EXIT)) break;

                if (inputs.contains(InputSystem.HELP)) inputSystem.printHelpMessage();
                if (inputs.contains(InputSystem.RESET)) {
                    snake = new Snake(snake.boardSize);
                    graphicSystem.setSnake(snake);
                }
                if (inputs.contains(InputSystem.PAUSE)) continue;
            }

            byte direction = inputSystem.getDirection(snake);
            snake.moveDirection(direction);

            graphicSystem.update();
            if (!graphicSystem.getIsGraphicOn()) break; // if the graphic system stopped, stop the game

            if (milliDelay > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(milliDelay);
                } catch (InterruptedException ignored) {
                }
            }

        }

        fitness = calcFitness();
    }

    private int calcFitness() {
        //fitness is based on length and lifetime
        int size = snake.getSize();
        int turnsAlive = snake.getTurnsAlive();
        if (size < 10)
            return (int) (turnsAlive * turnsAlive * Math.pow(2, size));

        //grows slower after 10 to stop fitness from getting stupidly big
        int fitness = turnsAlive * turnsAlive;
        fitness *= Math.pow(2, 10);
        fitness *= size - 9;
        return fitness;
    }

    public int getFitness() {
        return fitness;
    }
}
