import java.util.Arrays;

public class AttackBoard {

    final int BOARD_SIZE = 10;

    public String[][] attackboard = new String[BOARD_SIZE][BOARD_SIZE];

    public AttackBoard() {
        for (String[] row: attackboard) {
            Arrays.fill(row, "~");
        }
    }

    public void Attack(String attackcoordinate, String attackedsymbol) {
        int row = attackcoordinate.charAt(0) - 65;
        int column = Integer.parseInt(attackcoordinate.replaceAll("[^0-9]", "")) - 1;
        attackboard[row][column] = attackedsymbol;
    }

    public void PrintAttackBoard() {
        System.out.println("  1 2 3 4 5 6 7 8 9 10");
        int asciiValue = 65;
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print((char) asciiValue + " ");
            asciiValue++;
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(attackboard[i][j] + " ");
                if (j == BOARD_SIZE - 1) {
                    System.out.print("\n");
                }
            }
        }
    }

}