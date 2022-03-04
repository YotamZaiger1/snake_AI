package Game;

public interface GraphicSystem {
    /** Initialize the graphic. Called after the {@code setSnake} function had been called.
     */
    void initialize();

    /**
     * Update graphics due to changes in the game.
     **/
    void update();

    void setSnake(Snake snake);

    /**
     * Return if the graphic is still running.
     * @return if the graphic is still running.
     */
    boolean getIsGraphicOn();
}
