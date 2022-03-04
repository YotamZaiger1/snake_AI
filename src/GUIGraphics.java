import Game.GraphicSystem;
import Game.Pair;
import Game.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;

public class GUIGraphics extends JPanel implements Runnable, GraphicSystem {

    final int screenWidth;
    final int screenHeight;

    private Thread gameThread;
    private Snake gameSnake;
    private Pair tileSize;
    private final JFrame window = new JFrame();


    public GUIGraphics(int screenWidth, int screenHeight) {
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
    public void update() {
    }

    @Override
    public void setSnake(Snake snake) {
        this.gameSnake = snake;
        Pair boardSize = snake.getBoardSize();
        this.tileSize = new Pair(screenWidth / boardSize.x, screenHeight / boardSize.y);
    }

    @Override
    public boolean getIsGraphicOn() {
        return gameThread != null;
    }

}
