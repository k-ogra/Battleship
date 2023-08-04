public class Ship {
    String name;
    int length;

    int startrow;
    int endrow;
    int startcolumn;
    int endcolumn;
    boolean beenPlaced;
    PlayerBoard playerBoard;


    boolean isSunk = false;

    public Ship(String name, int length, String firstcoordinate, String secondcoordinate, PlayerBoard playerBoard) {
        this.name = name;
        this.length = length;
        this.startrow = firstcoordinate.charAt(0) - 65;
        this.endrow = secondcoordinate.charAt(0) - 65;
        this.startcolumn = Integer.parseInt(firstcoordinate.replaceAll("[^0-9]", "")) - 1;
        this.endcolumn = Integer.parseInt(secondcoordinate.replaceAll("[^0-9]", "")) - 1;
        this.playerBoard = playerBoard;
    }

    public void CheckIfSunk() {
        int hitcounter = 0;
        if (startrow == endrow) {
            for (int i = startcolumn; i <= endcolumn; i++) {
                if (playerBoard.playerboard[startrow][i] == "X") {
                    hitcounter++;
                }

            }
            if (hitcounter == this.length) {
                isSunk = true;
            }
        }
        else if (startcolumn == endcolumn) {
            for (int i = startrow; i <= endrow; i++) {
                if (playerBoard.playerboard[i][startcolumn] == "X") {
                    hitcounter++;
                }
            }
            if (hitcounter == this.length) {
                isSunk = true;
            }
        }
    }

}
