package Game;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class SnakePlayer implements Runnable {
    final long milliDelay;
    Snake snake;
    InputSystem inputSystem;
    GraphicSystem graphicSystem;
    private boolean isGamePaused = false;

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
        inputSystem.initialize();
        graphicSystem.setSnake(snake);
        graphicSystem.initialize();
        while (snake.getIsAlive()) {
            HashSet<Byte> inputs = inputSystem.getInputs(snake);
            if (inputs != null) {
                if (inputs.contains(InputSystem.EXIT)) break;
                if (inputs.contains(InputSystem.PAUSE)) {
                    isGamePaused = !isGamePaused;
                }
                if (inputs.contains(InputSystem.HELP)) inputSystem.printHelpMessage();
                if (inputs.contains(InputSystem.RESET)) {
                    snake = new Snake(snake.boardSize);
                    graphicSystem.setSnake(snake);
                }
                if (isGamePaused) continue;
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
    }

}
