package AI;

import Game.InputSystem;
import Game.Pair;
import Game.Snake;

import java.util.HashSet;

import static AI.Evolution.ALL_DIRECTIONS;

public class AiInput implements InputSystem {

    private byte currentDirection = Snake.NORTH;

    private final NeuralNetwork neuralNetwork;
    private final int turnsToLive;



    public AiInput(NeuralNetwork neuralNetwork, int turnsToLive){
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
            currentDirection = rotate(currentDirection, true);
        else if (prediction == 2)
            currentDirection = rotate(currentDirection, false);
        return currentDirection;
    }

    private byte rotate(byte direction, boolean right){
        if (right){
            switch (direction){
                case Snake.NORTH: return Snake.EAST;
                case Snake.EAST: return Snake.SOUTH;
                case Snake.SOUTH: return Snake.WEST;
                case Snake.WEST: return Snake.NORTH;
            }
        }
        return switch (direction) {
            case Snake.NORTH -> Snake.WEST;
            case Snake.EAST -> Snake.NORTH;
            case Snake.SOUTH -> Snake.EAST;
            case Snake.WEST -> Snake.SOUTH;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }

    private Matrix calcInputVector(Snake snake){
        Matrix inputVector = new Matrix(Evolution.inputSize, 1);
        int index = 0;

        Pair foodPos = snake.getFoodPos();
        Pair headPos = snake.getHeadPos();

        /*
        Is food in 8 directions:
        In rotations of 45 degrees, starting with the snake current direction,
        if there is food in the direction- 1, else- 0.
        */
        int startDirection;
        switch (currentDirection){  // according to ALL_DIRECTIONS order
            case Snake.NORTH -> startDirection = 0;
            case Snake.EAST -> startDirection = 2;
            case Snake.SOUTH -> startDirection = 4;
            case Snake.WEST -> startDirection = 6;
            default -> throw new IllegalStateException("Unexpected value: " + currentDirection);
        }

        for (int i = 0; i < 8; i++) {  // ALL_DIRECTIONS length
            Pair direction = ALL_DIRECTIONS[(i + startDirection) % ALL_DIRECTIONS.length];
            if (isPointOnVector(foodPos, headPos, direction))
                inputVector.setXY(index, 0, 1);
            else
                inputVector.setXY(index, 0, 0);

            index++;
        }

        /*
        The next value will be the angle between the head and the food.
         */
        Pair directionVector = ALL_DIRECTIONS[startDirection];  // the direction-vector which the snake currently looks to
        Pair foodTranslation = new Pair(foodPos.x - headPos.x, foodPos.y - headPos.y);  // redefine the origin to be the snake's head position
        double angle = directionVector.angle(foodTranslation);
        inputVector.setXY(index, 0, angle);
        index++;

        /*
        The next 8 values will be 8-direction distance from obstacles:
        In rotations of 45 degrees, starting with the snake current direction.
         */
        for (int i = 0; i < 8; i++) {
            Pair direction = ALL_DIRECTIONS[(i + startDirection) % ALL_DIRECTIONS.length];
            inputVector.setXY(index, 0, distanceFromObstacle(snake, direction));
            index++;
        }
        return inputVector;
    }

    /**
     * Checks if a given point seats on the line that made by a direction vector and started at {@code origin}.
     */
    private static boolean isPointOnVector(Pair point, Pair origin, Pair directionVector){
        float pointX = point.x - origin.x;
        float pointY = point.y - origin.y;
        float vectorX = directionVector.x;
        float vectorY = directionVector.y;

        if (pointX != 0f){
            if (vectorX == 0f)
                return false;
            return pointY / pointX == vectorY / vectorX;

        } else if (pointY != 0f){
            if (vectorY == 0f)
                return false;
            return pointX / pointY == vectorX / vectorY;
        }
        // point - origin = (0, 0)
        return true;
    }

    /**
     * @return The distance from the head of the snake to the closest obstacle in the given direction.
     */
    private int distanceFromObstacle(Snake snake, Pair directionVector){
        Pair pos = snake.getHeadPos();
        HashSet<Pair> emptySpace = snake.getEmptySpace();

        int dist = 0;
        // if pos is out of the board bounds it will not longer be in the emptySpace set
        while (emptySpace.contains(pos)){
            dist++;
            pos.x += directionVector.x;
            pos.y += directionVector.y;
        }

        return dist;
    }


    @Override
    public HashSet<Byte> getInputs(Snake snake) {
        if (snake.getTurnsAlive() > turnsToLive || !snake.getIsAlive()){
            HashSet<Byte> exit = new HashSet<>();
            exit.add(EXIT);
            return exit;
        }
        return null;
    }

    @Override
    public void printHelpMessage() {}
}
