package inf112.skeleton.app.gameLogic;

import inf112.skeleton.app.robot.AI;
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
    }

    /**
     * Start activation-phase were all robots execute their cards and board-objects are activated
     */
    public void startActivationPhase(int phase) {
        for(AI ai:aiList){
            ai.generateSmartMoves();
        }
        if (phase < 5) {


            // check priority for players and execute cards accordingly

            player.executeCardInHand(phase);
            for(AI ai:aiList){
                //ai.executeRandomProgramCardAction();

                if(ai.isAlive())
                    ai.executeCardInHand(phase);
            }

            GameScreen.getInstance().getGameLogic().endOfPhaseCheck(player);
            for(AI ai:aiList){
                GameScreen.getInstance().getGameLogic().endOfPhaseCheck(ai);
            }

            /*
            this is the order of actions
                * Conveyorbelts
                * Gears
                * Boardlasers
                * Robotlasers
                * Checkpoint/Wincheck
            */
            return;
        }

        gameScreen.makeHandInvisible();
        startNewRound();
    }

}
