package Game;

import java.util.HashSet;
import java.util.Random;

public class Helpers {
    static private final Random random = new Random();

    public static Pair randomChoice(HashSet<Pair> pairs){
        if (pairs == null) return null;
        return (Pair) pairs.toArray()[random.nextInt(pairs.size())];
    }
}
