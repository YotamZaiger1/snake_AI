package Game;

import java.util.HashSet;

public interface InputSystem {
    byte NORTH = 0;
    byte SOUTH = 1;
    byte EAST = 2;
    byte WEST = 3;

    byte PAUSE = 10;
    byte EXIT = 11;
    byte RESET = 13;
    byte HELP = 12;

    /**
     * Get a direction to move a given {@code snake} to. Based on its current state.
     * @param snake The {@code Game.Snake} instance.
     * @return One of: {@code NORTH, SOUTH, EAST, WEST}.
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
