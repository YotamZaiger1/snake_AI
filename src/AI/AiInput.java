package AI;

import Game.InputSystem;
import Game.Snake;

import java.util.HashSet;

public class AiInput implements InputSystem {
    final NeuralNetwork neuralNetwork;


    public AiInput(NeuralNetwork neuralNetwork){
        this.neuralNetwork = neuralNetwork;
    }


    @Override
    public byte getDirection(Snake snake) {
        return 0;
    }

    @Override
    public HashSet<Byte> getInputs(Snake snake) {
        return null;
    }

    @Override
    public void printHelpMessage() {}
}
