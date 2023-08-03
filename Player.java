public class Player {
    PlayerBoard playerboard;
    AttackBoard attackboard;
    String name;

    Player(String name) {
        this.playerboard = new PlayerBoard();
        this.attackboard = new AttackBoard();
        this.name = name;
    }




}
