package RunGame;

import Game.Pair;
import Game.Player;
import Game.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class GuiPlayer extends JPanel implements Runnable, Player {

    final int screenWidth;
    final int screenHeight;

    private Thread gameThread;
    private Snake gameSnake;
    private Pair tileSize;
    private final JFrame window = new JFrame();


    public GuiPlayer(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));

        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    private String getTitleMessage(boolean isAlive) {
        if (isAlive) {
            return "Snake Game (" + gameSnake.getSize() + ")";
        }
        return "Snake Game (" + gameSnake.getSize() + ") - Game Over";
    }

    @Override
    public void initialize() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(getTitleMessage(gameSnake.getIsAlive()));

        window.add(this);
        window.addKeyListener(new KeyboardHandler());
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        while (gameThread != null) {
            repaint();  // calls the paintComponent function
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);


        Graphics2D g2D = (Graphics2D) g;

        window.setTitle(getTitleMessage(gameSnake.getIsAlive()));
        int boardWidth = gameSnake.getBoardSize().x;
        int boardHeight = gameSnake.getBoardSize().y;
        HashSet<Pair> emptySpace = gameSnake.getEmptySpace();

        for (int i = 0; i < boardWidth; i++) {
            for (int j = 0; j < boardHeight; j++) {
                Pair pos = new Pair(i, j);
                if (!emptySpace.contains(pos)) {
                    if (pos.equals(gameSnake.getHeadPos())) {
                        g2D.setColor(Color.lightGray); // snake head
                    } else {
                        g2D.setColor(Color.GREEN);  // snake body
                    }
                } else if (pos.equals(gameSnake.getFoodPos())) {
                    g2D.setColor(Color.RED);  // food
                } else g2D.setColor(Color.BLACK);  // background

                g2D.fillRect(i * tileSize.x, j * tileSize.y, tileSize.x, tileSize.y);
            }
        }
        g2D.dispose();

    }

    @Override
    public byte getDirection() {
        return 0;
    }

    @Override
    public HashSet<Byte> getInputs() {
        return null;
    }

    @Override
    public void printHelpMessage() {
        final String message = """
                Keys:
                \t[UP]: move up.
                \t[DOWN]: move down.
                \t[RIGHT]: move right.
                \t[LEFT]: move left.

                \t[P]: pause the game. Press again to continue.
                \t[ESC]: exit the game.
                \t[R]: restart the game.
                \t[H]: print this help menu.
                """;
        System.out.println(message);
    }

    @Override
    public void update() {
        // The snake is being updated anyway with the `setSnake` function.
        // No more actions are necessary.
    }

    @Override
    public void setSnake(Snake snake) {
        this.gameSnake = snake;
        Pair boardSize = snake.getBoardSize();
        this.tileSize = new Pair(screenWidth / boardSize.x, screenHeight / boardSize.y);
    }

    @Override
    public boolean isAlive() {
        return gameThread != null;
    }

}
