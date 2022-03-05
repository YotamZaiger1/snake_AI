package RunGame;

import Game.Pair;
import Game.Player;
import Game.Snake;

import java.util.HashSet;

public class AsciiGraphic implements Player {
    private Snake snake;

    @Override
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    @Override
    public boolean isAlive() {
        return true;
    }

    @Override
    public void initialize() {
    }

    @Override
    public byte getDirection() {
        return 0;
    }

    @Override
    public HashSet<Byte> getInputs() {
        return null;
    }

    @Override
    public void printHelpMessage() {

    }

    @Override
    public void update() {
        StringBuilder result = new StringBuilder();

        for (int y = 0; y < snake.getBoardSize().x; y++) {
            result.append("|");
            for (int x = 0; x < snake.getBoardSize().y; x++) {
                if (!snake.getEmptySpace().contains(new Pair(x, y))) {
                    result.append("#");
                } else if (snake.getFoodPos().equals(x, y)) {
                    result.append("@");
                } else result.append(" ");
            }
            result.append("|\n");
        }
        System.out.println(result);
    }
}
