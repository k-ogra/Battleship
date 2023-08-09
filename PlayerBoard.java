import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Class representing a player's own board that contains their ships
 */
public class PlayerBoard {
    final int BOARD_SIZE = 10;
    public final int A_ASCII_VALUE = 65;
    public ArrayList<Ship> ships;
    public String[][] board = new String[BOARD_SIZE][BOARD_SIZE];

    public HashSet<String> sunkenShips;

    /**
     * Creates a player's own board
     */
    public PlayerBoard() {
        for (String[] row: board) {
            Arrays.fill(row, "~");
        }
        this.ships = new ArrayList<>();
        this.sunkenShips = new HashSet<>();
    }

    /**
     * Prints a player's board containing their ships
     */
    public void printPlayerBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        int asciiValue = 65;
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((char) asciiValue + " ");
            asciiValue++;
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
                if (j == BOARD_SIZE - 1) {
                    System.out.print("\n");
                }
            }
        }
    }

    /**
     * Places a ship given two coordinates
     * @param firstCoordinate A coordinate for one end of the ship that should contain a letter and number
     * @param secondCoordinate A coordinate for one end of the ship that should contain a letter and number
     */
    public void placeShip(String firstCoordinate, String secondCoordinate) {
        int startRow = firstCoordinate.charAt(0) - A_ASCII_VALUE;
        int endRow = secondCoordinate.charAt(0) - A_ASCII_VALUE;
        int startColumn = Integer.parseInt(firstCoordinate.replaceAll("[^0-9]", "")) - 1;
        int endColumn = Integer.parseInt(secondCoordinate.replaceAll("[^0-9]", "")) - 1;

        if (startRow > endRow) {
            int temp = startRow;
            startRow = endRow;
            endRow = temp;
        }
        if (startColumn > endColumn) {
            int temp = startColumn;
            startColumn = endColumn;
            endColumn = temp;
        }

        if (startRow == endRow) {
            for (int i = startColumn; i <= endColumn; i++) {
                board[startRow][i] = "O";
            }
        }
        else if (startColumn == endColumn) {
            for (int i = startRow; i <= endRow; i++) {
                board[i][startColumn] = "O";
            }
        }
    }

    /**
     * Checks if an attack is a hit or a miss
     * @param attackCoordinate A single coordinate representing where an attack should go
     * @return A string that represents a hit as "X" or a miss as "M"
     */
    public String checkAttack(String attackCoordinate) {
        int row = attackCoordinate.charAt(0) - A_ASCII_VALUE;
        int column = Integer.parseInt(attackCoordinate.replaceAll("[^0-9]", "")) - 1;
        if (board[row][column].equals("O")) {
            System.out.println("A ship has been hit!");
            board[row][column] = "X";
            return "X";
        }
        else {
            System.out.println("Attack missed!");
            board[row][column] = "M";
            return "M";
        }
    }

    /**
     * Checks if the game is over by looking at the ships that are sunk
     * @return Boolean representing whether all ships are sunk or not
     */
    public boolean CheckGameOver() {
        int sunkCounter = 0;
        for (Ship ship : ships) {
            ship.checkIfSunk();
            if (ship.isSunk) {
                sunkCounter++;
                sunkenShips.add(ship.name);
            }
        }
        return sunkCounter == 5;

    }

}
