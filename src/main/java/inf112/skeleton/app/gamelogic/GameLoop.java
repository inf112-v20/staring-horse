package inf112.skeleton.app.gamelogic;

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
        for(AI ai:aiList){
            ai.generateMoves();
        }
        gameScreen.makePlayerDeckMatchSelectableCards();
    }

    /**
     * Start activation-phase were all robots execute their cards and board-objects are activated
     */
    public void startActivationPhase(int phase) {
        if (phase < 5) {
            gameScreen.unrenderLasers();

            // check priority for players and execute cards accordingly
            for (IRobot robot : sortRobotsByPriorityInPhase(phase)) {
                if (robot.isAlive() && !robot.getShouldNotMove()) {
                    robot.executeCardInHand(phase);
                }
            }

            /*
            this is the order of actions
                * Conveyorbelts
                * Gears
                * Boardlasers
                * Robotlasers
                * Checkpoint/Wincheck
            */
            for (IRobot robot : gameScreen.getRobots()) {
                if (robot.isAlive())
                    gameScreen.getGameLogic().endOfPhaseCheck(robot);
            }
            gameScreen.getGameLogic().activateWallLasers(gameScreen.getGameLogic().getAllPositionsFromObjectName("WallLaser"));

            return;
        }

        for (IRobot robot: gameScreen.getRobots()) {
            if (robot instanceof Player) {
                ((Player) robot).clearHand();
            }
            robot.setShouldNotMove(false);
        }

        gameScreen.makeHandInvisible();
        gameScreen.unrenderLasers();
        startNewRound();
    }

    private ArrayList<IRobot> sortRobotsByPriorityInPhase(int phase) {
        ArrayList<IRobot> sortedPhaseRobots = gameScreen.getRobots();
        ArrayList<Integer> priorityList = new ArrayList<>();

        // add each priority into a list
        for (IRobot robot : gameScreen.getRobots()) {
            int priority = robot.getHand()[phase].getPriority();
            priorityList.add(priority);
        }

        // sorts priorityList and sortedPhaseRobots
        for (int i = 0; i < priorityList.size() - 1; i++) {
            for (int j = i + 1; j < priorityList.size(); j++) {
                if (priorityList.get(i) < priorityList.get(j)) {

                    int temp = priorityList.get(j);
                    IRobot robotTemp = sortedPhaseRobots.get(j);
                    priorityList.set(j, priorityList.get(i));
                    priorityList.set(i, temp);
                    sortedPhaseRobots.set(j, sortedPhaseRobots.get(i));
                    sortedPhaseRobots.set(i, robotTemp);
                }
            }
        }
        return sortedPhaseRobots;
    }

}
