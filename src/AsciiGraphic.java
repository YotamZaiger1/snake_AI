import Game.GraphicSystem;
import Game.Pair;
import Game.Snake;

public class AsciiGraphic implements GraphicSystem {
    private Snake snake;

    @Override
    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    @Override
    public boolean getIsGraphicOn() {
        return true;
    }

    @Override
    public void initialize() {
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
