package Game;

import java.util.HashSet;
import java.util.Random;

public class Helpers {
    static private final Random random = new Random();

    /**
     * Returns a random {@code int} between 0 (inclusive) and {@code max - 1} (inclusive).
     * @param max max {@code int} that can be returned (exclusive).
     * @return random {@code int} between 0 (inclusive) and {@code max - 1} (inclusive).
     */
    public static int randomRange(int max) {
        float r = random.nextFloat();
        return (int) (r * max);
    }

    public static Pair randomChoice(HashSet<Pair> pairs){
        if (pairs == null) return null;
        return (Pair) pairs.toArray()[randomRange(pairs.size())];
    }
}
