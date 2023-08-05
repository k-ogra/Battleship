import java.util.Scanner;
public class UI {

    Scanner scanner = new Scanner(System.in);
    public UI() {
        Start();
    }

    public void Start() {
        System.out.println("Welcome to Battleship!");
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");
        player1.playerboard.PrintPlayerBoard();
        SelectShips(player1, player2);
    }

    public void SelectShips(Player player1, Player player2) {
        Player currentplayer = player1;
        String playername = currentplayer.name;
        int shiplength = 0;
        int shipsplaced = 0;
        String coordinates = "";
        while (playername != "Player 2" || shipsplaced != 5) {
            System.out.println(playername + " select a ship to place (Carrier, Battleship, Submarine, Cruiser, or Destroyer): ");
            String currentshipname = scanner.nextLine();
            if (!currentplayer.playerboard.ships.isEmpty()) {

                Ship currentship = currentplayer.playerboard.ships.get(0);
                for (Ship ship : currentplayer.playerboard.ships) {
                    if (ship.name.equals(currentshipname)) {
                        currentship = ship;
                        break;
                    }
                }
                if (currentship.beenPlaced && currentship.name.equals(currentshipname)) {
                    System.out.println("This ship has already been placed");
                    continue;
                }
            }
            switch (currentshipname) {
                case "Carrier":
                    shiplength = 5;
                    break;
                case "Battleship":
                    shiplength = 4;
                    break;
                case "Submarine", "Cruiser":
                    shiplength = 3;
                    break;
                case "Destroyer":
                    shiplength = 2;
                    break;
                default:
                    System.out.println("Invalid ship");
                    continue;
            }
            while (true) {
                System.out.println("Please enter coordinates of " + currentshipname + " (Length " + shiplength + "):");
                coordinates = scanner.nextLine();
                if (!isValidCoordinates(coordinates, shiplength, currentplayer)) {
                    continue;
                }
                break;
            }
            String firstCoordinate = coordinates.split(" ")[0].toUpperCase();
            String secondCoordinate = coordinates.split(" ")[1].toUpperCase();
            switch (currentshipname) {
                case "Carrier":
                    Ship carrier = new Ship(currentshipname, shiplength, firstCoordinate, secondCoordinate, currentplayer.playerboard);
                    carrier.beenPlaced = true;
                    currentplayer.playerboard.ships.add(carrier);
                    break;
                case "Battleship":
                    Ship battleship = new Ship(currentshipname, shiplength, firstCoordinate, secondCoordinate, currentplayer.playerboard);
                    battleship.beenPlaced = true;
                    currentplayer.playerboard.ships.add(battleship);
                    break;
                case "Submarine":
                    Ship submarine = new Ship(currentshipname, shiplength, firstCoordinate, secondCoordinate, currentplayer.playerboard);
                    submarine.beenPlaced = true;
                    currentplayer.playerboard.ships.add(submarine);
                    break;
                case "Cruiser":
                    Ship cruiser = new Ship(currentshipname, shiplength, firstCoordinate, secondCoordinate, currentplayer.playerboard);
                    cruiser.beenPlaced = true;
                    currentplayer.playerboard.ships.add(cruiser);
                    break;
                case "Destroyer":
                    Ship destroyer = new Ship(currentshipname, shiplength, firstCoordinate, secondCoordinate, currentplayer.playerboard);
                    destroyer.beenPlaced = true;
                    currentplayer.playerboard.ships.add(destroyer);
                    break;
                default:
                    break;
            }


            currentplayer.playerboard.PlaceShip(firstCoordinate, secondCoordinate);
            currentplayer.playerboard.PrintPlayerBoard();
            shipsplaced++;
            if (shipsplaced == 5 && playername == "Player 1") {
                currentplayer = player2;
                shipsplaced = 0;
                playername = currentplayer.name;
            }
        }
        AttackShip(player1, player2);

    }

    public boolean isValidCoordinates(String coordinates, int shiplength, Player currentplayer) {
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

        else if ((firstCoordinate.charAt(0) - 65 > 9 || firstCoordinate.charAt(0) - 65 < 0) || (secondCoordinate.charAt(0) - 65 > 9 || secondCoordinate.charAt(0) - 65 < 0)) {
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
        int startrow = firstCoordinate.charAt(0) - 65;
        int endrow = secondCoordinate.charAt(0) - 65;
        int startcolumn = Integer.parseInt(firstCoordinate.replaceAll("[^0-9]", "")) - 1;
        int endcolumn = Integer.parseInt(secondCoordinate.replaceAll("[^0-9]", "")) - 1;
        if (Math.abs(startrow - endrow) + 1 != shiplength && Math.abs(startcolumn - endcolumn) + 1 != shiplength) {
            System.out.println("Invalid coordinates");
            return false;
        }
        else if (startrow != endrow && startcolumn != endcolumn) {
            System.out.println("Ship cannot be placed diagonally");
            return false;
        }
        else if (startrow == endrow) {
            for (int i = startcolumn; i < endcolumn; i++) {
                if (currentplayer.playerboard.playerboard[startrow][i] == "O") {
                    System.out.println("Coordinates conflict with existing ship");
                    return false;
                }
            }
        }
        else if (startcolumn == endcolumn) {
            for (int i = startrow; i < endrow; i++) {
                if (currentplayer.playerboard.playerboard[i][startcolumn] == "O") {
                    System.out.println("Coordinates conflict with existing ship");
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isValidAttackCoordinate(String attackcoordinate) {
        if (attackcoordinate.length() != 3 && attackcoordinate.length() != 2) {
            return false;
        }
        attackcoordinate = attackcoordinate.toUpperCase();
        if (!Character.toString(attackcoordinate.charAt(0)).matches("^[a-zA-Z]*$")) {
            System.out.println("Invalid coordinates");
            return false;
        }
        else if (attackcoordinate.charAt(0) - 65 > 9 || attackcoordinate.charAt(0) - 65 < 0) {
            return false;
        }
        else if (Integer.parseInt(attackcoordinate.replaceAll("[^0-9]", "")) - 1 > 9 || Integer.parseInt(attackcoordinate.replaceAll("[^0-9]", "")) - 1 < 0) {
            return false;
        }
        return true;

    }

    public boolean isNewAttackCoordinate(String attackcoordinate, Player currentplayer) {
        int row = attackcoordinate.charAt(0) - 65;
        int column = Integer.parseInt(attackcoordinate.replaceAll("[^0-9]", "")) - 1;
        if (currentplayer.attackboard.attackboard[row][column].equals("X") || currentplayer.attackboard.attackboard[row][column].equals("M")) {
            return false;
        }
        return true;
    }

    public void AttackShip(Player player1, Player player2) {
        Player currentplayer = player1;
        String playername = currentplayer.name;
        String attackcoordinate = "";
        while (!currentplayer.playerboard.CheckGameOver()) {
            System.out.println("************************");
            System.out.println("ATTACK BOARD");
            currentplayer.attackboard.PrintAttackBoard();
            System.out.println("************************");
            System.out.println("PLAYER BOARD");
            currentplayer.playerboard.PrintPlayerBoard();
            while (true) {
                System.out.println(playername + " enter coordinate for attack.");
                attackcoordinate = scanner.nextLine();
                if (!isValidAttackCoordinate(attackcoordinate)) {
                    System.out.println("Invalid attack coordinate");
                    continue;
                }
                else if (!isNewAttackCoordinate(attackcoordinate, currentplayer)) {
                    System.out.println("This coordinate has already been attacked");
                    continue;
                }
                break;
            }

            if (currentplayer == player1) {
                String attackedsymbol = player2.playerboard.Attacked(attackcoordinate);
                currentplayer.attackboard.Attack(attackcoordinate, attackedsymbol);
                if (player2.playerboard.CheckGameOver()) {
                    break;
                }
                System.out.println("Player 1's sunken ships: " + player1.playerboard.sunkenships);
                currentplayer = player2;
                playername = currentplayer.name;
            }
            else if (currentplayer == player2) {
                String attackedsymbol = player1.playerboard.Attacked(attackcoordinate);
                currentplayer.attackboard.Attack(attackcoordinate, attackedsymbol);
                if (player1.playerboard.CheckGameOver()) {
                    break;
                }
                System.out.println("Player 2's sunken ships: " + player2.playerboard.sunkenships);
                currentplayer = player1;
                playername = currentplayer.name;
            }
        }
        if (currentplayer.name == "Player 1") {
            System.out.println("Player 1 has won!");
        }
        if (currentplayer.name == "Player 2") {
            System.out.println("Player 2 has won!");
        }
    }
}