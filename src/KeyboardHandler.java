import Game.InputSystem;
import Game.Snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class KeyboardHandler implements InputSystem, KeyListener {
    private final HashSet<Integer> keysPressedID = new HashSet<>();
    private byte direction = NORTH;

    @Override
    public byte getDirection(Snake snake) {
        if (keysPressedID.contains(KeyEvent.VK_RIGHT)) {
            direction = EAST;
        } else if (keysPressedID.contains(KeyEvent.VK_LEFT)) {
            direction = WEST;
        } else if (keysPressedID.contains(KeyEvent.VK_UP)) {
            direction = NORTH;
        } else if (keysPressedID.contains(KeyEvent.VK_DOWN)) {
            direction = SOUTH;
        }

        return direction;
    }

    @Override
    public HashSet<Byte> getInputs(Snake snake) {
        HashSet<Byte> keys = new HashSet<>();
        if (keysPressedID.contains(KeyEvent.VK_P)) {
            keys.add(PAUSE);
        } else if (keysPressedID.contains(KeyEvent.VK_ESCAPE)) {
            keys.add(EXIT);
        } else if (keysPressedID.contains(KeyEvent.VK_R)) {
            keys.add(RESET);
        } else if (keysPressedID.contains(KeyEvent.VK_H)) {
            keys.add(HELP);
        }

        return keys;
    }

    @Override
    public void printHelpMessage() {
        String message = """
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
    public void initialize() {
//        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        window.setResizable(false);
//
//        window.addKeyListener(this);
//        window.pack();
//
//        window.setLocationRelativeTo(null);
//        window.setVisible(true);
    }



    // *** key listener ***
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        keysPressedID.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressedID.remove(e.getKeyCode());
    }
}
