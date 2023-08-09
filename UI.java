import java.util.Scanner;

/**
 * Class representing the user interface for Battleship
 */
public class UI {

    public final int CARRIER_LENGTH = 5;
    public final int BATTLESHIP_LENGTH = 4;
    public final int CRUISER_LENGTH = 3;
    public final int DESTROYER_LENGTH = 2;
    public final int A_ASCII_VALUE = 65;
    public final int TOTAL_SHIPS = 5;

    Scanner scanner = new Scanner(System.in);

    /**
     * Starts the user interface
     */
    public UI() {
        System.out.println("Welcome to Battleship!");
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        player1.playerBoard.printPlayerBoard();
        selectShips(player1, player2);
    }


    /**
     * Asks players to select and enter the coordinates of each of the 5 ships so, they can be placed
     * @param player1 Represents the player that makes the first move
     * @param player2 Represents the player that makes the second move
     */
    public void selectShips(Player player1, Player player2) {
        Player currentPlayer = player1;
        String playerName = currentPlayer.name;
        int shipLength;
        int shipsPlaced = 0;
        String coordinates;
        while (!playerName.equals("Player 2") || shipsPlaced != TOTAL_SHIPS) {
            System.out.println(playerName + " select a ship to place (Carrier, Battleship, Submarine, Cruiser, or Destroyer): ");
            String currentShipName = scanner.nextLine();
            if (!currentPlayer.playerBoard.ships.isEmpty()) {

                Ship currentShip = currentPlayer.playerBoard.ships.get(0);
                for (Ship ship : currentPlayer.playerBoard.ships) {
                    if (ship.name.equals(currentShipName)) {
                        currentShip = ship;
                        break;
                    }
                }
                if (currentShip.beenPlaced && currentShip.name.equals(currentShipName)) {
                    System.out.println("This ship has already been placed");
                    continue;
                }
            }
            switch (currentShipName) {
                case "Carrier" -> shipLength = CARRIER_LENGTH;
                case "Battleship" -> shipLength = BATTLESHIP_LENGTH;
                case "Submarine", "Cruiser" -> shipLength = CRUISER_LENGTH;
                case "Destroyer" -> shipLength = DESTROYER_LENGTH;
                default -> {
                    System.out.println("Invalid ship");
                    continue;
                }
            }
            while (true) {
                System.out.println("Please enter coordinates of " + currentShipName + " (Length " + shipLength + "):");
                coordinates = scanner.nextLine();
                if (!isValidCoordinates(coordinates, shipLength, currentPlayer)) {
                    continue;
                }
                break;
            }
            String firstCoordinate = coordinates.split(" ")[0].toUpperCase();
            String secondCoordinate = coordinates.split(" ")[1].toUpperCase();

            Ship ship = new Ship(currentShipName, shipLength, firstCoordinate, secondCoordinate, currentPlayer.playerBoard);
            ship.beenPlaced = true;
            currentPlayer.playerBoard.ships.add(ship);


            currentPlayer.playerBoard.placeShip(firstCoordinate, secondCoordinate);
            currentPlayer.playerBoard.printPlayerBoard();
            shipsPlaced++;
            if (shipsPlaced == TOTAL_SHIPS && playerName.equals("Player 1")) {
                currentPlayer = player2;
                shipsPlaced = 0;
                playerName = currentPlayer.name;
            }
        }
        attackShip(player1, player2);

    }

    /**
     * Checks if the coordinates of a ship are valid or if they conflict with existing ships
     * @param coordinates A String containing coordinates for a ship separated by a space
     * @param shipLength Represents the length of a ship
     * @param currentPlayer Represents the player who is currently entering coordinates
     * @return A boolean that says whether the coordinates of a ship are valid
     */
    public boolean isValidCoordinates(String coordinates, int shipLength, Player currentPlayer) {
        if (coordinates.split(" ").length != 2) {
            System.out.println("Invalid coordinates");
            return false;
        }
        String firstCoordinate = coordinates.split(" ")[0].toUpperCase();
        String secondCoordinate = coordinates.split(" ")[1].toUpperCase();
        if (!Character.toString(firstCoordinate.charAt(0)).matches("^[a-zA-Z]*$")) {
            System.out.println("Invalid coordinates");
            return false;
        }

        else if ((firstCoordinate.charAt(0) - A_ASCII_VALUE > 9 || firstCoordinate.charAt(0) - A_ASCII_VALUE < 0) || (secondCoordinate.charAt(0) - A_ASCII_VALUE > 9 || secondCoordinate.charAt(0) - A_ASCII_VALUE < 0)) {
            System.out.println("Invalid coordinates");
            return false;
        }

        try {
            if ((Integer.parseInt(firstCoordinate.replaceAll("[^0-9]", "")) - 1 > 9 || Integer.parseInt(firstCoordinate.replaceAll("[^0-9]", "")) - 1 < 0) || (Integer.parseInt(secondCoordinate.replaceAll("[^0-9]", "")) - 1 > 9 || Integer.parseInt(secondCoordinate.replaceAll("[^0-9]", "")) - 1 < 0)) {
                System.out.println("Invalid coordinates");
                return false;
            }
        }
        catch (NumberFormatException nfe) {
            System.out.println("Invalid coordinates");
            return false;
        }
        int startRow = firstCoordinate.charAt(0) - A_ASCII_VALUE;
        int endRow = secondCoordinate.charAt(0) - A_ASCII_VALUE;
        int startColumn = Integer.parseInt(firstCoordinate.replaceAll("[^0-9]", "")) - 1;
        int endColumn = Integer.parseInt(secondCoordinate.replaceAll("[^0-9]", "")) - 1;
        if (Math.abs(startRow - endRow) + 1 != shipLength && Math.abs(startColumn - endColumn) + 1 != shipLength) {
            System.out.println("Invalid coordinates");
            return false;
        }
        else if (startRow != endRow && startColumn != endColumn) {
            System.out.println("Ship cannot be placed diagonally");
            return false;
        }
        else if (startRow == endRow) {
            for (int i = startColumn; i < endColumn; i++) {
                if (currentPlayer.playerBoard.board[startRow][i].equals("O")) {
                    System.out.println("Coordinates conflict with existing ship");
                    return false;
                }
            }
        }
        else {
            for (int i = startRow; i < endRow; i++) {
                if (currentPlayer.playerBoard.board[i][startColumn].equals("O")) {
                    System.out.println("Coordinates conflict with existing ship");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if a coordinate for an attack is valid
     * @param attackCoordinate A single coordinate representing where an attack should go
     * @return A boolean that says whether the attack coordinate is valid
     */
    public boolean isValidAttackCoordinate(String attackCoordinate) {
        if (attackCoordinate.length() != 3 && attackCoordinate.length() != 2) {
            return false;
        }
        attackCoordinate = attackCoordinate.toUpperCase();
        if (!Character.toString(attackCoordinate.charAt(0)).matches("^[a-zA-Z]*$")) {
            System.out.println("Invalid coordinates");
            return false;
        }
        else if (attackCoordinate.charAt(0) - A_ASCII_VALUE > 9 || attackCoordinate.charAt(0) - A_ASCII_VALUE < 0) {
            return false;
        }
        else return Integer.parseInt(attackCoordinate.replaceAll("[^0-9]", "")) - 1 <= 9 && Integer.parseInt(attackCoordinate.replaceAll("[^0-9]", "")) - 1 >= 0;

    }

    /**
     * Checks if an attack coordinate has already been used
     * @param attackCoordinate A single coordinate representing where an attack should go
     * @param currentPlayer Represents the player who is currently entering an attack
     * @return A boolean that says if an attack coordinate has already been used
     */
    public boolean isNewAttackCoordinate(String attackCoordinate, Player currentPlayer) {
        int row = attackCoordinate.charAt(0) - A_ASCII_VALUE;
        int column = Integer.parseInt(attackCoordinate.replaceAll("[^0-9]", "")) - 1;
        return !currentPlayer.attackBoard.board[row][column].equals("X") && !currentPlayer.attackBoard.board[row][column].equals("M");
    }

    /**
     * Alternates between each player to allow them to place attacks and prints the winner when the game is finished
     * @param player1 Represents the player that makes the first move
     * @param player2 Represents the player that makes the second move
     */
    public void attackShip(Player player1, Player player2) {
        Player currentPlayer = player1;
        String playerName = currentPlayer.name;
        String attackCoordinate;
        while (!currentPlayer.playerBoard.CheckGameOver()) {
            System.out.println("************************");
            System.out.println("ATTACK BOARD");
            currentPlayer.attackBoard.printAttackBoard();
            System.out.println("************************");
            System.out.println("PLAYER BOARD");
            currentPlayer.playerBoard.printPlayerBoard();
            while (true) {
                System.out.println(playerName + " enter coordinate for attack.");
                attackCoordinate = scanner.nextLine();
                if (!isValidAttackCoordinate(attackCoordinate)) {
                    System.out.println("Invalid attack coordinate");
                    continue;
                }
                else if (!isNewAttackCoordinate(attackCoordinate, currentPlayer)) {
                    System.out.println("This coordinate has already been attacked");
                    continue;
                }
                break;
            }

            String attackedSymbol;
            if (currentPlayer == player1) {
                attackedSymbol = player2.playerBoard.checkAttack(attackCoordinate);
                currentPlayer.attackBoard.attack(attackCoordinate, attackedSymbol);
                if (player2.playerBoard.CheckGameOver()) {
                    break;
                }
                System.out.println("Player 1's sunken ships: " + player1.playerBoard.sunkenShips);
                currentPlayer = player2;
            }
            else {
                attackedSymbol = player1.playerBoard.checkAttack(attackCoordinate);
                currentPlayer.attackBoard.attack(attackCoordinate, attackedSymbol);
                if (player1.playerBoard.CheckGameOver()) {
                    break;
                }
                System.out.println("Player 2's sunken ships: " + player2.playerBoard.sunkenShips);
                currentPlayer = player1;
            }
            playerName = currentPlayer.name;
        }
        if (currentPlayer.name.equals("Player 1")) {
            System.out.println("Player 1 has won!");
        }
        if (currentPlayer.name.equals("Player 2")) {
            System.out.println("Player 2 has won!");
        }
    }
}