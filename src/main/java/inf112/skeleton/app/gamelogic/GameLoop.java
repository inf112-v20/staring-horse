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

    /**
     * Starts a new round
     */
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
     * @param phase the phase number
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

            for (IRobot robot : gameScreen.getRobots()) {
                if (robot.isAlive())
                    endOfPhaseCheck(robot);
            }

            gameScreen.getGameLogic().activateWallLasers(gameScreen.getGameLogic().getAllPositionsFromObjectName("WallLaser"));
            gameScreen.updatePlayerInfo();

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

    /**
     * Executes the events that happens at the end of a phase
     * for the given robot.
     * Should probably just do it for all robots from the get go
     * @param robot IRobot
     */
    public void endOfPhaseCheck(IRobot robot) {
        if (robot instanceof Player) {
            Player player = (Player) robot;
            if (!player.getIsTestPlayer()) {
                endOfPhaseCheckHelper(player);
            }
        } else {
            endOfPhaseCheckHelper(robot);
        }
    }

    /**
     * Calls the methods in the correct order. for each robot.
     * @param robot IRobot
     */
    private void endOfPhaseCheckHelper(IRobot robot) {
        gameScreen.unrenderRobot(robot);
        gameScreen.getGameLogic().repairOnWrench(robot);
        gameScreen.getGameLogic().pickUpFlag(robot);
        gameScreen.getGameLogic().onFlagCheck(robot);
        gameScreen.getGameLogic().changeDirOnGear(robot);
        gameScreen.getGameLogic().conveyorBelts(robot);
        gameScreen.getGameLogic().activateLasersFromPos(robot.getPos(), robot.getDirection(), false);
        gameScreen.renderRobot(robot);
    }

    /**
     * will sort the robots in a list where the robot with the
     * largest priority card in a phase is first.
     * @param phase The current phase
     * @return A list of sorted robots.
     */
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
