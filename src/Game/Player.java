package Game;

import java.util.HashSet;

public interface Player {
    byte NORTH = 0;  // TODO: move into `SnakeGame`
    byte SOUTH = 1;
    byte EAST = 2;
    byte WEST = 3;

    byte PAUSE = 10;
    byte EXIT = 11;
    byte RESET = 13;
    byte HELP = 12;


    /**
     * Initialize the player. Called after the {@code setSnake} function had been called.
     */
    void initialize();

    /**
     * Get a direction to move a given {@code snake} to. Based on its current state.
     * @return One of: {@code NORTH, SOUTH, EAST, WEST}.
     */
    byte getDirection();

    /**
     * Get non-direction inputs from the user.
     * @return Some of the following: {@code PAUSE, EXIT, RESET, HELP}.
     */
    HashSet<Byte> getInputs();

    /**
     * Prints an help menu for the game. Usually called when the {@code HELP} input has been received.
     */
    void printHelpMessage();

    /**
     * Being called after each snake turn. Can be used to update graphics due to changes in the game.
     **/
    void update();

    void setSnake(Snake snake);

    /**
     * @return Is the player still alive and running.
     */
    boolean isAlive();
}
