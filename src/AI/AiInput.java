package AI;

import Game.InputSystem;
import Game.Pair;
import Game.Snake;
import Main.Utils;

import java.util.HashSet;

import static Main.Utils.ALL_DIRECTIONS;

public class AiInput implements InputSystem {
    public final static int inputSize = 8 + 1 + 8 + 8;
    private final NeuralNetwork neuralNetwork;
    private final int turnsToLive;
    private byte currentDirection = Snake.NORTH;


    public AiInput(NeuralNetwork neuralNetwork, int turnsToLive) {
        this.neuralNetwork = neuralNetwork;
        this.turnsToLive = turnsToLive;
    }


    @Override
    public byte getDirection(Snake snake) {
        Matrix inputVector = calcInputVector(snake);
        int prediction = neuralNetwork.predict(inputVector);

        /*
        prediction = 0  -> stay at the same direction
        prediction = 1  -> turn right
        prediction = 2  -> turn left
         */
        if (prediction == 1)
            currentDirection = Utils.rotate(currentDirection, true);
        else if (prediction == 2)
            currentDirection = Utils.rotate(currentDirection, false);
        return currentDirection;
    }


    private Matrix calcInputVector(Snake snake) {
        Matrix inputVector = new Matrix(inputSize, 1);
        int index = 0;

        Pair foodPos = snake.getFoodPos();
        Pair headPos = snake.getHeadPos();

        int startDirection;
        switch (currentDirection) {  // according to ALL_DIRECTIONS order
            case Snake.NORTH -> startDirection = 0;
            case Snake.EAST -> startDirection = 2;
            case Snake.SOUTH -> startDirection = 4;
            case Snake.WEST -> startDirection = 6;
            default -> throw new IllegalStateException("Unexpected value: " + currentDirection);
        }

        /*
        Is food in 8 directions:
        In rotations of 45 degrees, starting with the snake current direction,
        if there is food in the direction- 1, else- 0.
        */
        for (int i = 0; i < 8; i++) {  // ALL_DIRECTIONS length
            Pair direction = ALL_DIRECTIONS[(i + startDirection) % ALL_DIRECTIONS.length];
            if (Utils.isPointOnVector(foodPos, headPos, direction))
                inputVector.setXY(index, 0, 1);
            else
                inputVector.setXY(index, 0, 0);

            index++;
        }

        /*
        The next value will be the sine of the angle between the head and the food.
         */
        Pair directionVector = ALL_DIRECTIONS[startDirection];  // the direction-vector which the snake currently looks to
        Pair foodTranslation = new Pair(foodPos.x - headPos.x, foodPos.y - headPos.y);  // redefine the origin to be the snake's head position
        double angle = directionVector.angle(foodTranslation);
        inputVector.setXY(index, 0, Math.sin(angle));
        index++;

        /*
        The next 8 values will be 8-direction normalised distance from obstacles (including including snake body)
        In rotations of 45 degrees, starting with the snake current direction.
         */
        for (int i = 0; i < 8; i++) {
            Pair direction = ALL_DIRECTIONS[(i + startDirection) % ALL_DIRECTIONS.length];
            double distance = distanceFromObstacle(snake, direction, false);
            inputVector.setXY(index, 0, Math.pow(1d / distance, 1.5));
            index++;
        }


        /*
        The next 8 values will be 8-direction normalised distance from boundaries (NOT including including snake body):
        In rotations of 45 degrees, starting with the snake current direction.
         */
        for (int i = 0; i < 8; i++) {
            Pair direction = ALL_DIRECTIONS[(i + startDirection) % ALL_DIRECTIONS.length];
            double distance = distanceFromObstacle(snake, direction, true);
            inputVector.setXY(index, 0, Math.pow(1d / distance, 1.5));
            index++;
        }
        return inputVector;
    }


    @Override
    public HashSet<Byte> getInputs(Snake snake) {
        if (snake.getTurnsAlive() > turnsToLive || !snake.getIsAlive()) {
            HashSet<Byte> exit = new HashSet<>();
            exit.add(EXIT);
            return exit;
        }
        return null;
    }

    @Override
    public void printHelpMessage() {
    }

    /**
     * @return The distance from the head of the snake to the closest obstacle in the given direction.
     */
    private static int distanceFromObstacle(Snake snake, Pair directionVector, boolean allowCrossSnakeBody) {
        Pair pos = snake.getHeadPos();
        HashSet<Pair> emptySpace = snake.getEmptySpace();

        Pair boardSize = snake.getBoardSize();
        int dist = 0;

        do {
            dist++;
            pos.x += directionVector.x;
            pos.y += directionVector.y;
        }
        // if pos is out of the board bounds it will not longer be in the emptySpace set
        while ((
                allowCrossSnakeBody &&
                        (pos.x < boardSize.x && pos.x >= 0) &&
                        (pos.y < boardSize.y && pos.y >= 0)
        )
                || emptySpace.contains(pos));

        return dist;
    }
}
