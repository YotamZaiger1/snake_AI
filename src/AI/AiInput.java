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
        Matrix inputVector = calcInputs(snake);
        int prediction = neuralNetwork.predict(inputVector);
    }

    // TODO: continue... calculate more inputs!
    private Matrix calcInputs(Snake snake){
        Matrix inputVector = new Matrix(25, 1);

        Pair foodPos = snake.getFoodPos();
        Pair headPos = snake.getHeadPos();
        // is food in 8 directions:
        for (int i = 0; i < ALL_DIRECTIONS.length; i++) {
            if (isPointOnVector(foodPos, headPos, ALL_DIRECTIONS[i]))
                inputVector.setXY(i, 0, 1);
            else
                inputVector.setXY(i, 0, 0);
        }


        return inputVector;
    }

    //TODO: check if works
    /**
     * Checks if a given point seats on the line that made by a direction vector and a origin.
     */
    private boolean isPointOnVector(Pair point, Pair origin, Pair directionVector){
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
