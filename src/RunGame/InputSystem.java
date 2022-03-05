package RunGame;

import Game.Snake;

import java.util.HashSet;

public interface InputSystem {
    void setSnake(Snake snake);

    /**
     * Prints an help menu for the game. Usually called when the {@code HELP} input has been received.
     */
    void printHelpMessage();


    /**
     * Get a direction to move a given {@code snake} to. Based on its current state.
     * @return One of: {@code NORTH, SOUTH, EAST, WEST}.
     */
    byte getDirection();

    /**
     * Get non-direction inputs from the user.
     *
     * @return Some of the following: {@code PAUSE, EXIT, RESET, HELP}.
     */
    HashSet<Byte> getInputs();
}
