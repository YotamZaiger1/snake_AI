package RunGame;

import Game.Player;
import Game.Snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;

public class KeyboardHandler implements KeyListener {
    private final HashSet<Integer> keysPressedID = new HashSet<>();
    private byte direction = Player.NORTH;

    public byte getDirection(Snake snake) {
        if (keysPressedID.contains(KeyEvent.VK_RIGHT)) {
            direction = Player.EAST;
        } else if (keysPressedID.contains(KeyEvent.VK_LEFT)) {
            direction = Player.WEST;
        } else if (keysPressedID.contains(KeyEvent.VK_UP)) {
            direction = Player.NORTH;
        } else if (keysPressedID.contains(KeyEvent.VK_DOWN)) {
            direction = Player.SOUTH;
        }

        return direction;
    }

    public HashSet<Byte> getInputs(Snake snake) {
        HashSet<Byte> keys = new HashSet<>();
        if (keysPressedID.contains(KeyEvent.VK_P)) {
            keys.add(Player.PAUSE);
        } else if (keysPressedID.contains(KeyEvent.VK_ESCAPE)) {
            keys.add(Player.EXIT);
        } else if (keysPressedID.contains(KeyEvent.VK_R)) {
            keys.add(Player.RESET);
        } else if (keysPressedID.contains(KeyEvent.VK_H)) {
            keys.add(Player.HELP);
        }

        return keys;
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
