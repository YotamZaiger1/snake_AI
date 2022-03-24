package Game;

import Main.Utils;

import java.util.ArrayList;
import java.util.HashSet;

public class Snake {
    public static final byte NORTH = 0;
    public static final byte SOUTH = 1;
    public static final byte EAST = 2;
    public static final byte WEST = 3;

    final Pair boardSize;

    int turnsAlive;
    int size = 1;
    boolean isAlive = true;
    ArrayList<Pair> snakeIn = new ArrayList<>();
    HashSet<Pair> emptySpace = new HashSet<>();
    Pair headPos;

    Pair foodPos;
    boolean justAte;

    public Snake(int sizeX, int sizeY){
        this(sizeX, sizeY, 4);
    }

    public Snake(Pair boardSize){
        this(boardSize, 4);
    }

    public Snake(Pair boardSize, int startSize) {
        this(boardSize.x, boardSize.y, startSize);
    }

    public Snake(int sizeX, int sizeY, int startSize) {
        if (sizeX > 1 && sizeY > 1) {
            this.boardSize = new Pair(sizeX, sizeY);
        } else {
            this.boardSize = new Pair(10, 10);
        }

        headPos = new Pair(boardSize.x / 2, boardSize.y / 2);
        snakeIn.add(headPos);

        // initialize the empty space
        for (int i = 0; i < boardSize.x; i++) {
            for (int j = 0; j < boardSize.y; j++) {
                if (!headPos.equals(i, j))
                    emptySpace.add(new Pair(i, j));
            }
        }

        foodPos = Utils.randomChoice(emptySpace);
        for (int i = 0; i < startSize - 1; i++) {
            justAte = true;
            moveDirection(NORTH);
        }
    }

    public void moveDirection(byte direction) {
        if (direction < 0 || direction > 3 || !isAlive) return;

        int addX = 0, addY = 0;
        switch (direction) {
            case NORTH -> addY = +1;
            case SOUTH -> addY = -1;
            case EAST -> addX = +1;
            case WEST -> addX = -1;
        }

        Pair new_pos = new Pair(headPos.x + addX, headPos.y + addY);
        if (justAte) {
            justAte = false;
            size++;
        } else {
            emptySpace.add(snakeIn.get(0));
            snakeIn.remove(0);
        }

        // check boundaries
        if (new_pos.x >= boardSize.x || new_pos.y >= boardSize.y || new_pos.x < 0 || new_pos.y < 0
                || !emptySpace.contains(new_pos))
            isAlive = false;
        else {
            emptySpace.remove(new_pos);
            snakeIn.add(new_pos);
            headPos = new_pos;
            turnsAlive++;
        }

        // check if ate food
        if (new_pos.equals(foodPos)) {
            justAte = true;
            foodPos = Utils.randomChoice(emptySpace);
        }
    }

    //getters:
    public int getTurnsAlive() {
        return turnsAlive;
    }

    public boolean getIsAlive() {
        return isAlive;
    }

    public int getSize() {
        return size;
    }

    public Pair getHeadPos() {
        return headPos.clone();
    }

    public Pair getFoodPos() {
        return foodPos.clone();
    }

    public HashSet<Pair> getEmptySpace() {
        // Insecure! Making a copy causes thread-synchronization exceptions.
        return emptySpace;
    }

    public ArrayList<Pair> getSnakeIn() {
        return new ArrayList<>(snakeIn);
    }

    public Pair getBoardSize() {
        return boardSize.clone();
    }
}
