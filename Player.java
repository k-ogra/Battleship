/**
 * Class representing one of the players of the game
 */
public class Player {
    PlayerBoard playerBoard;
    AttackBoard attackBoard;
    String name;

    /**
     * Creates a player and assigns a personal and attack board
     * @param name Represents the name of a player
     */
    Player(String name) {
        this.playerBoard = new PlayerBoard();
        this.attackBoard = new AttackBoard();
        this.name = name;
    }
}
