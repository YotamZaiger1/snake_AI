package Game;

import java.util.ArrayList;
import java.util.HashSet;

public class Snake {
    final Pair boardSize;

    int turnsAlive;
    int size = 1;
    boolean isAlive = true;
    ArrayList<Pair> snakeIn = new ArrayList<>();
    HashSet<Pair> emptySpace = new HashSet<>();
    Pair headPos;

    Pair foodPos;
    boolean justAte;

    public Snake(Pair boardSize) {
        this(boardSize.x, boardSize.y);
    }

    public Snake(int sizeX, int sizeY) {
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

        foodPos = Helpers.randomChoice(emptySpace);
    }

    public void moveDirection(byte direction) {
        if (direction < 0 || direction > 3 || !isAlive) return;

        int addX = 0, addY = 0;
        switch (direction) {
            case InputSystem.NORTH -> addY = +1;
            case InputSystem.SOUTH -> addY = -1;
            case InputSystem.EAST -> addX = +1;
            case InputSystem.WEST -> addX = -1;
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

        // chek if ate food
        if (new_pos.equals(foodPos)) {
            justAte = true;
            foodPos = Helpers.randomChoice(emptySpace);
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
        return (HashSet) emptySpace.clone();
    }

    public ArrayList<Pair> getSnakeIn() {
        return new ArrayList<>(snakeIn);
    }

    public Pair getBoardSize() {
        return boardSize.clone();
    }
}
