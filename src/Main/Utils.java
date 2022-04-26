package Main;

import Game.Pair;
import Game.Snake;

import java.util.HashSet;
import java.util.Random;

public class Utils {
    static private final Random random = new Random();

    public static Pair randomChoice(HashSet<Pair> pairs){
        if (pairs == null) return null;
        return (Pair) pairs.toArray()[random.nextInt(pairs.size())];
    }

    public static double randGaussian(){
        return random.nextGaussian();
    }

    public static int sign(double n) {
        return n > 0 ? 1 : n < 0 ? -1 : 0;
    }

    /**
     * Checks if a given point seats on the line that made by a direction vector and started at {@code origin}.
     */
    public static boolean isPointOnVector(Pair point, Pair origin, Pair directionVector) {
        float pointX = point.x - origin.x;
        float pointY = point.y - origin.y;
        float vectorX = directionVector.x;
        float vectorY = directionVector.y;

        if (sign(pointX) != sign(vectorX) || sign(pointY) != sign(vectorY))
            return false;

        if (pointX != 0f) {
            if (vectorX == 0f)
                return false;
            return pointY / pointX == vectorY / vectorX;

        } else if (pointY != 0f) {
            if (vectorY == 0f)
                return false;
            return pointX / pointY == vectorX / vectorY;
        }
        // point - origin = (0, 0)
        return true;
    }

    public static byte rotate(byte direction, boolean right) {
        if (right)
            return switch (direction) {
                case Snake.NORTH -> Snake.EAST;
                case Snake.EAST -> Snake.SOUTH;
                case Snake.SOUTH -> Snake.WEST;
                case Snake.WEST -> Snake.NORTH;
                default -> throw new IllegalStateException("Unexpected value: " + direction);
            };

        return switch (direction) {
            case Snake.NORTH -> Snake.WEST;
            case Snake.EAST -> Snake.NORTH;
            case Snake.SOUTH -> Snake.EAST;
            case Snake.WEST -> Snake.SOUTH;
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };
    }
}
