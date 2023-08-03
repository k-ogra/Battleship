import java.util.Arrays;
public class PlayerBoard {
    final int BOARD_SIZE = 10;

    public Ship[] ships;
    public String[][] playerboard = new String[BOARD_SIZE][BOARD_SIZE];



    public PlayerBoard() {
        for (String[] row: playerboard) {
            Arrays.fill(row, "~");
        }
        ships = new Ship[5];

    }

    public void PrintPlayerBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        int asciiValue = 65;
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((char) asciiValue + " ");
            asciiValue++;
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(playerboard[i][j] + " ");
                if (j == BOARD_SIZE - 1) {
                    System.out.print("\n");
                }
            }
        }
    }

    public void PlaceShip(String firstcoordinate, String secondcoordinate) {
        int startrow = firstcoordinate.charAt(0) - 65;
        int endrow = secondcoordinate.charAt(0) - 65;
        int startcolumn = Integer.parseInt(firstcoordinate.replaceAll("[^0-9]", "")) - 1;
        int endcolumn = Integer.parseInt(secondcoordinate.replaceAll("[^0-9]", "")) - 1;
        // End MUST be greater than start
        if (startrow == endrow) {
            for (int i = startcolumn; i <= endcolumn; i++) {
                playerboard[startrow][i] = "O";
            }
        }
        else if (startcolumn == endcolumn) {
            for (int i = startrow; i <= endrow; i++) {
                playerboard[i][startcolumn] = "O";
            }
        }
    }

    public void Attacked(String attackcoordinate) {
        int row = attackcoordinate.charAt(0);
        int column = Integer.parseInt(attackcoordinate.replaceAll("[^0-9]", "")) - 1;
        if (playerboard[row][column] == "O") {
            System.out.println("A ship has been hit");
        }
        else {
            System.out.println("Attack missed");
        }
        playerboard[row][column] = "X";

    }

}
