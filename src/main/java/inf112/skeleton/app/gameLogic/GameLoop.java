package inf112.skeleton.app.gameLogic;

import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.robot.AI;
import inf112.skeleton.app.robot.Player;
import inf112.skeleton.app.screen.GameScreen;


public class GameLoop {

    private Player player;
    private AI ai;
    private GameScreen gameScreen;
    private int roundNumber;

    public GameLoop(Player player, AI ai, GameScreen gameScreen) {
        this.player = player;
        this.ai = ai;
        this.gameScreen = gameScreen;
        this.roundNumber = 1;
    }

    public void startNewRound() {
        System.out.println("Starting round " + roundNumber++);
        this.player.drawNewDeck();
        gameScreen.makePlayerDeckMatchSelectableCards();


        if (player.hasWon()) {
            System.out.println("THE PLAYER HAS WON!");
            RoboRally.getInstance().setMenuScreen();
        }
    }

    public void startActivationPhase() {

        for(int phase = 0; phase < 5; phase++){

            // check priority for players and execute cards accordingly

            player.executeCardInHand(phase);
            ai.executeRandomProgramCardAction();
        }

        gameScreen.makeHandInvisible();
        startNewRound();
    }

}
