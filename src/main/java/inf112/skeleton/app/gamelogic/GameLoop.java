package inf112.skeleton.app.gamelogic;

import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.player.Player;
import inf112.skeleton.app.screen.GameScreen;

public class GameLoop {

    private Player player;
    private GameScreen gameScreen;
    private int roundNumber;

    public GameLoop(Player player, GameScreen gameScreen){
        this.player = player;
        this.gameScreen = gameScreen;
        this.roundNumber = 1;
    }

    public void startGame(){
        this.startNewRound();
    }

    public void startNewRound(){
        System.out.println("Starting round " + roundNumber++);
        gameScreen.makeSelectableCards();

        if (player.hasWon()) {
            System.out.println("THE PLAYER HAS WON!");
            RoboRally.getInstance().setMenuScreen();
        }
    }

}
