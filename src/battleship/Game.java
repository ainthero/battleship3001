package battleship;

public class Game {

    public boolean init(int n, int m, int[] shipCount, int torpedos) {
        ocean = new Ocean(n, m);
        fleet = 0;
        this.torpedos = torpedos;
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < shipCount[i]; k++) {
                if (!ocean.tryPlaceShipRandom(ShipType.values()[i])) {
                    if (!ocean.tryPlaceShip(ShipType.values()[i])) {
                        return false;
                    }
                }
            }
            fleet += shipCount[i];
        }
        return true;
    }

    public void update(int i, int j, boolean torpedoMode) {
        if (ocean.isUsed(i, j)) {
            System.out.println("You have already shoot here.");
            return;
        }
        if (torpedoMode) {
            if (torpedos <= 0) {
                System.out.println("No torpedoes available.");
                return;
            }
            torpedos--;
            if (ocean.kill(i, j)) {
                System.out.println("hit");
            } else {
                System.out.println("miss");
            }
        } else {
            if (ocean.makeShot(i, j)) {
                System.out.println("hit");
            } else {
                System.out.println("miss");
            }
        }
        if (ocean.isDead(i, j)) {
            System.out.println("You just have sunk a " + ocean.nameOfShip(i, j));
            fleet--;
        }
        shots++;
    }

    public void output() {
        ocean.printOcean();
    }

    public boolean isEnd() {
        return fleet == 0;
    }

    public int getShots() {
        return shots;
    }

    private Ocean ocean;
    private int fleet;
    private int shots;
    private int torpedos;
}
