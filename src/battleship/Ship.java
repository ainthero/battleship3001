package battleship;

enum ShipType {
    SUBMARINE,
    DESTROYER,
    CRUISER,
    BATTLESHIP,
    CARRIER;

    static int GetLength(ShipType type) {
        if (type == ShipType.SUBMARINE) return 1;
        if (type == ShipType.DESTROYER) return 2;
        if (type == ShipType.CRUISER) return 3;
        if (type == ShipType.BATTLESHIP) return 4;
        if (type == ShipType.CARRIER) return 5;
        return -1;
    }
}

public class Ship {

    public Ship(Coordinates pos, Direction dir, ShipType type) {
        int length = ShipType.GetLength(type);
        coords = new Coordinates[length];
        lives = length;
        coords[0] = new Coordinates(pos);
        for (int i = 1; i < length; i++) {
            if (dir == Direction.DOWN) pos.i++;
            if (dir == Direction.UP) pos.i--;
            if (dir == Direction.RIGHT) pos.j++;
            if (dir == Direction.LEFT) pos.j--;
            coords[i] = new Coordinates(pos);
        }
    }

    public boolean isDead() {
        return lives == 0;
    }

    public void shot() {
        if (lives > 0)
            lives--;
    }

    public Coordinates[] getCoords() {
        return coords;
    }

    public void kill() {
        lives = 0;
    }


    private Coordinates[] coords;
    private int lives;
}
