package inf112.skeleton.app;

public class Game {
    // Game-class contains Board-object and controls rounds and phases

    private Board gameBoard;

    public Game(){

        this.gameBoard = new Board(10, 10);

    }

}
