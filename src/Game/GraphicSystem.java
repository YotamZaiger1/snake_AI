package Game;

public interface GraphicSystem {
    /** Initialize the graphic. Called after the {@code setSnake} function had been called.
     */
    void initialize();

    /**
     * Called after each snake turn. May be used to update graphics due to changes in the game.
     **/
    void update();

    void setSnake(Snake snake);

    /**
     * @return if the graphic is still alive and running.
     */
    boolean getIsGraphicOn();
}
