package battleship;

enum Direction {
    RIGHT,
    LEFT,
    UP,
    DOWN
}

public class Coordinates {
    public Coordinates(int x, int y) {
        i = x;
        j = y;
    }

    public Coordinates(Coordinates another) {
        i = another.i;
        j = another.j;
    }

    public int i;
    public int j;
}
