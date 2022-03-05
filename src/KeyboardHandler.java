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
                Keys (affect only when the snake is alive):
                \t[UP]: Turn up.
                \t[DOWN]: Turn down.
                \t[RIGHT]: Turn right.
                \t[LEFT]: Turn left.
                
                \t[P]: Hold to pause the game.
                \t[ESC]: Exit the game.
                \t[R]: Restart the game.
                \t[H]: Print this help menu.
                """;
        System.out.println(message);
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
