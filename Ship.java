/**
 * Class representing each ship in the game
 */
public class Ship {
    String name;
    int length;

    int startRow;
    int endRow;
    int startColumn;
    int endColumn;
    boolean beenPlaced;
    PlayerBoard playerBoard;

    public final int A_ASCII_VALUE = 65;
    boolean isSunk = false;

    /**
     * Creates a ship object
     * @param name Represents the name of a ship
     * @param length Represents the length of a ship
     * @param firstCoordinate A coordinate representing one end of a ship
     * @param secondCoordinate A coordinate representing one end of a ship
     * @param playerBoard Represents a player's own board for ships
     */
    public Ship(String name, int length, String firstCoordinate, String secondCoordinate, PlayerBoard playerBoard) {
        this.name = name;
        this.length = length;
        this.startRow = firstCoordinate.charAt(0) - A_ASCII_VALUE;
        this.endRow = secondCoordinate.charAt(0) - A_ASCII_VALUE;
        this.startColumn = Integer.parseInt(firstCoordinate.replaceAll("[^0-9]", "")) - 1;
        this.endColumn = Integer.parseInt(secondCoordinate.replaceAll("[^0-9]", "")) - 1;
        this.playerBoard = playerBoard;
    }

    /**
     * Checks if a ship is sunk
     */
    public void checkIfSunk() {
        int hitCounter = 0;
        if (startRow == endRow) {
            for (int i = startColumn; i <= endColumn; i++) {
                if (playerBoard.board[startRow][i].equals("X")) {
                    hitCounter++;
                }

            }
            if (hitCounter == this.length) {
                isSunk = true;
            }
        }
        else if (startColumn == endColumn) {
            for (int i = startRow; i <= endRow; i++) {
                if (playerBoard.board[i][startColumn].equals("X")) {
                    hitCounter++;
                }
            }
            if (hitCounter == this.length) {
                isSunk = true;
            }
        }
    }

}
