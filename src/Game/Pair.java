package Game;

import java.util.Objects;

public class Pair {
    public int x;
    public int y;

    public Pair(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int dotProd(Pair o){
        return this.x * o.x + this.y + o.y;
    }

    /**
     * @return The angle between {@code this} and {@code o}.
     */
    public double angle(Pair o){
        double norm = Math.sqrt(this.dotProd(this) * o.dotProd(o));
        double cosAngle = ((double) this.dotProd(o)) / norm;
        return Math.acos(cosAngle);
    }

    public Pair clone(){
        return new Pair(x, y);
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }

    public boolean equals(int x, int y){
        return this.x == x && this.y == y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return x == pair.x && y == pair.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
