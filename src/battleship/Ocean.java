package battleship;

public class Ocean {
    public Ocean(int n, int m) {
        ocean = new Ship[n][m];
        used = new boolean[n][m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                used[i][j] = false;
        this.n = n;
        this.m = m;
    }

    public boolean tryPlaceShip(ShipType type) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                for (int dir = 0; dir < 4; dir++){
                    Ship ship = new Ship(new Coordinates(i, j), Direction.values()[dir], type);
                    boolean isValid = true;
                    for (Coordinates cor : ship.getCoords())
                        isValid &= isCoordinatesValid(cor);
                    if (isValid) {
                        for (Coordinates cor : ship.getCoords())
                            ocean[cor.i][cor.j] = ship;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean tryPlaceShipRandom(ShipType type) {
        for (int tries = 0; tries < 50000; tries++) {
            int i = (int)(Math.random() * n);
            int j = (int)(Math.random() * m);
            int dir = (int)(Math.random() * 4);
            Ship ship = new Ship(new Coordinates(i, j), Direction.values()[dir], type);
            boolean isValid = true;
            for (Coordinates cor : ship.getCoords())
                isValid &= isCoordinatesValid(cor);
            if (isValid) {
                for (Coordinates cor : ship.getCoords())
                    ocean[cor.i][cor.j] = ship;
                return true;
            }
        }
        return false;
    }

    public boolean isCoordinatesValid(Coordinates coords) {
        boolean ans = coords.i >= 0 && coords.i < n &&
                      coords.j >= 0 && coords.j < m;
        int[] diArr = {0, 1, -1};
        int[] djArr = {0, 1, -1};
        for (int di : diArr)
            for (int dj : djArr) {
                int tempI = coords.i + di;
                int tempJ = coords.j + dj;
                if (tempI >= 0 && tempI < n &&
                    tempJ >= 0 && tempJ < m) {
                    ans &= ocean[tempI][tempJ] == null;
                }
            }
        return ans;
    }

    public boolean makeShot(int i, int j) {
        if (ocean[i][j] != null) {
            if (!used[i][j]) {
                ocean[i][j].shot();
                used[i][j] = true;
                return true;
            }
        }
        used[i][j] = true;
        return false;
    }

    public boolean kill(int i, int j) {
        if (ocean[i][j] != null) {
            if (!used[i][j]) {
                for (Coordinates coords : ocean[i][j].getCoords()) {
                    used[coords.i][coords.j] = true;
                }
                ocean[i][j].kill();
                return true;
            }
        }
        used[i][j] = true;
        return false;
    }

    public boolean isDead(int i, int j) {
        return ocean[i][j] != null && ocean[i][j].isDead();
    }

    public boolean isUsed(int i, int j) {
        return used[i][j];
    }

    public String nameOfShip(int i, int j) {
        if (ocean[i][j].getCoords().length == 5) return "Carrier";
        if (ocean[i][j].getCoords().length == 4) return "Destroyer";
        if (ocean[i][j].getCoords().length == 3) return "Cruiser";
        if (ocean[i][j].getCoords().length == 2) return "Battleship";
        if (ocean[i][j].getCoords().length == 1) return "Submarine";
        return "Unknown";
    }

    public void printOcean() {
        System.out.print("    ");
        for (int i = 0; i < n; i++)
            if (i < 10)
                System.out.print(i + "  ");
            else
                System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < n; i++) {
            if (i < 10) {
                System.out.print(i + "  ");
            } else {
                System.out.print(i + " ");
            }
            for (int j = 0; j < m; j++) {
                if (!used[i][j]) {
                    System.out.print(" ~ ");
                    continue;
                }
                if (ocean[i][j] == null) {
                    System.out.print(" \u00B7 ");
                    continue;
                }
                if (ocean[i][j].isDead()) {
                    System.out.print(" x ");
                    continue;
                }
                System.out.print(" + ");
            }
            System.out.print('\n');
        }
    }

    private Ship[][] ocean;
    private boolean[][] used;
    private int n, m;
}
