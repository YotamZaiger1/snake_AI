package AI;

import Game.InputSystem;
import Game.Pair;
import Game.Snake;

import java.util.HashSet;

public class AiInput implements InputSystem {
    private static final Pair[] ALL_DIRECTIONS = new Pair[]{
            new Pair(0, 1),
            new Pair(1,1),
            new Pair(1,0),
            new Pair(1,-1),
            new Pair(0,-1),
            new Pair(-1,-1),
            new Pair(-1,0),
            new Pair(-1,1)
    };
    private byte currentDirection = Snake.NORTH;

    private final NeuralNetwork neuralNetwork;
    private final int turnsToLive;



    public AiInput(NeuralNetwork neuralNetwork, int turnsToLive){
        this.neuralNetwork = neuralNetwork;
        this.turnsToLive = turnsToLive;
    }


    @Override
    public byte getDirection(Snake snake) {
        // TODO: FINISH!
        Matrix inputVector = calcInputVector(snake);
        int prediction = neuralNetwork.predict(inputVector);
        return (byte) prediction;
    }

    // TODO: continue... calculate more inputs!
    private Matrix calcInputVector(Snake snake){
        Matrix inputVector = new Matrix(25, 1);

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
                inputVector.setXY(i, 0, 1);
            else
                inputVector.setXY(i, 0, 0);
        }

        /*
        The next value will be the angle between the head and the food.
         */
        Pair directionVector = ALL_DIRECTIONS[startDirection];  // the direction-vector which the snake currently looks to
        Pair foodTranslation = new Pair(foodPos.x - headPos.x, foodPos.y - headPos.y);  // redefine the origin to be the snake's head position
        double angle = directionVector.angle(foodTranslation);
        inputVector.setXY(9, 0, angle);



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

    @Override
    public HashSet<Byte> getInputs(Snake snake) {
        if (snake.getTurnsAlive() > turnsToLive){
            HashSet<Byte> exit = new HashSet<>();
            exit.add(EXIT);
            return exit;
        }
        return null;
    }

    @Override
    public void printHelpMessage() {}
}
