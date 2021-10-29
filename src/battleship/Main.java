package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int n, m;
        Game game = new Game();
        Scanner scn = new Scanner(System.in);
        int[] shipCounts = new int[5];
        boolean isInit = false;
        int torpedos = 0;
        if (args.length == 7 || args.length == 8) {
            n = Integer.parseInt(args[0]);
            m = Integer.parseInt(args[1]);
            if (!(n >= 10 && n <= 40 && m >= 10 && m <= 40)) {
                System.out.println("Sizes do not fit requirements. Try again");
                return;
            }
            shipCounts[0] = Integer.parseInt(args[2]);
            shipCounts[1] = Integer.parseInt(args[3]);
            shipCounts[2] = Integer.parseInt(args[4]);
            shipCounts[3] = Integer.parseInt(args[5]);
            shipCounts[4] = Integer.parseInt(args[6]);
            for (int i = 0; i < 5; i++) {
                if (shipCounts[i] < 0) {
                    System.out.println("Number of ships do not fit requirements. Try again.");
                    return;
                }
            }
            if (args.length == 8)
                torpedos = Integer.parseInt(args[7]);
        } else if (args.length == 0) {
            do {
                for (int i = 0; i < 5; i++) shipCounts[i] = 0;
                System.out.println("Input ocean size: height width (10 <= x <= 40)");
                n = scn.nextInt();
                m = scn.nextInt();
                if (!(n >= 10 && n <= 40 && m >= 10 && m <= 40)) {
                    System.out.println("Sizes do not fit requirements. Try again");
                    continue;
                }
                String[] shipNames = {"submarines", "destroyers", "cruisers", "battleships", "carriers"};
                for (int i = 0; i < 5;) {
                    System.out.println("Input number of " + shipNames[i] + ": ");
                    shipCounts[i] = scn.nextInt();
                    if (shipCounts[i] < 0) {
                        System.out.println("Number of ships do not fit requirements. Try again.");
                        continue;
                    }
                    i++;
                }
                System.out.println("Input number of torpedoes (null to disable torpedo firing mode):");
                torpedos = scn.nextInt();
                isInit = game.init(n, m, shipCounts, torpedos);
                if (!isInit)
                    System.out.println("Failed. Try another configuration.");
            } while (!isInit);
        } else {
            System.out.println("Wrong number of arguments.");
            return;
        }
        while (!game.isEnd()) {
            game.output();
            System.out.println("Input coordinates where to shoot: (T) x y");
            String[] curInput = scn.nextLine().split(" ");
            if (curInput.length != 2 && curInput.length != 3) {
                System.out.println("Wrong number of arguments.");
                continue;
            }
            int i, j;
            boolean tMode;
            if (curInput[0].equals("T")) {
                tMode = true;
                i = Integer.parseInt(curInput[2]);
                j = Integer.parseInt(curInput[1]);
            } else {
                tMode = false;
                i = Integer.parseInt(curInput[1]);
                j = Integer.parseInt(curInput[0]);
            }
            if (i < 0 || i >= n || j < 0 || j >= m) {
                System.out.println("Coordinates are out of bounds. Try again.");
                continue;
            }
            game.update(i, j, tMode);
        }
        System.out.println("You won.");
        System.out.println("Number of shots: " + game.getShots());
    }
}
