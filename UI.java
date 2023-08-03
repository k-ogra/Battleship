import java.util.Scanner;
import java.util.ArrayList;
public class UI {

    Scanner scanner = new Scanner(System.in);
    public UI() {
        Start();
    }

    public void Start() {
        System.out.println("Welcome to Battleship!");
        Player player1 = new Player("Player1");
        Player player2 = new Player("Player2");
        PlayerBoard board = new PlayerBoard();
        board.PrintPlayerBoard();
        ArrayList<Ship> ships = new ArrayList<>();



        SelectShips(player1, player2, ships);
    }

    public void SelectShips(Player player1, Player player2, ArrayList<Ship> ships) {
        Player currentplayer = player1;
        String playername = currentplayer.name;
        int shiplength = 0;
        int shipsplaced = 0;
        while (playername != "Player2" && shipsplaced != 5) {
            System.out.println(playername + " select a ship to place: ");
            String currentshipname = scanner.nextLine();
            Ship currentship = ships.get(0);
            for (Ship ship : ships) {
                if (ship.name == currentshipname) {
                    currentship = ship;
                    currentship.beenPlaced = true;
                }
            }
            if (currentship.beenPlaced) {
                System.out.println("This ship has already been placed!");
                continue;
            }
            else {
                switch (currentshipname) {
                    case "Carrier":
                        shiplength = 5;
                        break;
                    case "Battleship":
                        shiplength = 4;
                        break;
                    case "Submarine":
                        shiplength = 3;
                        break;
                    case "Crusier":
                        shiplength = 3;
                        break;
                    case "Destroyer":
                        shiplength = 2;
                        break;
                    default:
                        System.out.println("Invalid ship");
                        break;
                }
            }

            System.out.println("Please enter coordinates of " + currentshipname + " (Length " + shiplength + "):");
            String coordinates = scanner.nextLine();
            String firstCoordinate = coordinates.split(" ")[0];
            String secondCoordinate = coordinates.split(" ")[1];
            currentplayer.playerboard.PlaceShip(firstCoordinate, secondCoordinate);
            currentplayer.playerboard.PrintPlayerBoard();
            shipsplaced++;
            if (shipsplaced == 5 && playername == "Player1") {
                currentplayer = player2;
                shipsplaced = 0;
                playername = currentplayer.name;
            }
        }
        AttackShip(player1, player2, ships);

    }

    public void AttackShip(Player player1, Player player2, ArrayList<Ship> ships) {
        Player currentplayer = player1;
        String playername = currentplayer.name;
        while (true) {
            currentplayer.attackboard.PrintAttackBoard();
            System.out.println("*****************");
            currentplayer.playerboard.PrintPlayerBoard();
            System.out.println(playername + "enter coordinate for attack.");
            String attackcoordinate = scanner.nextLine();
            currentplayer.attackboard.Attack(attackcoordinate);
            if (currentplayer == player1) {
                player2.playerboard.Attacked(attackcoordinate);
                currentplayer = player2;
                playername = currentplayer.name;
            }
            else if (currentplayer == player2) {
                player1.playerboard.Attacked(attackcoordinate);
                currentplayer = player1;
                playername = currentplayer.name;
            }




        }

    }
}

