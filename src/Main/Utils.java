package Main;

import Game.Pair;

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
}
