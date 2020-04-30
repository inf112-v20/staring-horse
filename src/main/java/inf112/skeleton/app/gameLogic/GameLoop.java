package inf112.skeleton.app.gameLogic;

import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.robot.AI;
import inf112.skeleton.app.robot.IRobot;
import inf112.skeleton.app.robot.Player;
import inf112.skeleton.app.screen.GameScreen;

import java.util.ArrayList;


public class GameLoop {

    private Player player;
    private ArrayList<AI> aiList;
    private GameScreen gameScreen;
    private int roundNumber;

    public GameLoop(Player player, ArrayList<AI> aiList, GameScreen gameScreen) {
        this.player = player;
        this.aiList = aiList;
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

    /**
     * Start activation-phase were all robots execute their cards and board-objects are activated
     */
    public void startActivationPhase() {
        for(AI ai:aiList){
            ai.generateSmartMoves();
        }
        for(int phase = 0; phase < 5; phase++){

            // check priority for players and execute cards accordingly

            player.executeCardInHand(phase);
            for(AI ai:aiList){
                //ai.executeRandomProgramCardAction();

                ai.executeCardInHand(phase);
            }

            GameLogic.getInstance().endOfPhaseCheck(player);
            for(AI ai:aiList){
                GameLogic.getInstance().endOfPhaseCheck(ai);
            }

            /*
            this is the order of actions
                * Conveyorbelts
                * Gears
                * Boardlasers
                * Robotlasers
                * Checkpoint/Wincheck
            */

        }

        gameScreen.makeHandInvisible();
        startNewRound();
    }

}
