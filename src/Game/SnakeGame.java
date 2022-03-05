package Game;

import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class SnakeGame implements Runnable {
    final long milliDelay;
    Snake snake;
    Player player;


    private boolean isGamePaused = false;

    public SnakeGame(int sizeX, int sizeY, long milliDelay, Player player) {
        this(new Snake(sizeX, sizeY), milliDelay, player);
    }

    public SnakeGame(Snake snake, long milliDelay, Player player) {
        this.snake = snake;
        if (milliDelay < 0) milliDelay = 0;
        this.milliDelay = milliDelay;
        this.player = player;
    }

    @Override
    public void run() {
        player.initialize();
        player.setSnake(snake);
        while (snake.getIsAlive()) {
            HashSet<Byte> inputs = player.getInputs();
            if (inputs != null) {
                if (inputs.contains(Player.EXIT)) break;
                if (inputs.contains(Player.PAUSE)) {
                    isGamePaused = !isGamePaused;
                }
                if (inputs.contains(Player.HELP)) player.printHelpMessage();
                if (inputs.contains(Player.RESET)) {
                    snake = new Snake(snake.boardSize);
                    player.setSnake(snake);  // update to the new snake
                }
                if (isGamePaused) continue;
            }

            byte direction = player.getDirection();
            snake.moveDirection(direction);

            player.update();
            if (!player.isAlive()) break; // if the graphic system stopped, stop the game

            if (milliDelay > 0) {
                try {
                    TimeUnit.MILLISECONDS.sleep(milliDelay);
                } catch (InterruptedException ignored) {
                }
            }

        }
    }

}
