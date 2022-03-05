package Game;

import java.util.HashSet;

public interface InputSystem {
    byte PAUSE = 10;
    byte EXIT = 11;
    byte RESET = 13;
    byte HELP = 12;

    /**
     * Get a direction to move a given {@code snake} to. Based on its current state.
     * @param snake The {@code Game.Snake} instance.
     * @return One of: {@code Snake.NORTH, Snake.SOUTH, Snake.EAST, Snake.WEST}.
     */
    byte getDirection(Snake snake);

    /**
     * Get non-direction inputs from the user.
     * @param snake The {@code Game.Snake} instance.
     * @return Some of the following: {@code PAUSE, EXIT, RESET, HELP}.
     */
    HashSet<Byte> getInputs(Snake snake);

    /**
     * Prints an help menu for the game. Usually called when the {@code HELP} input has been received.
     */
    void printHelpMessage();

}
