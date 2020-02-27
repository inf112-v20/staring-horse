package inf112.skeleton.app;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProgramCardTest {
    Player testPlayer;
    ProgramCard playerCard;
    int startX;
    int startY;
    Direction dir;

    @Before
    public void before(){
        testPlayer = new Player();
        startX = 5;
        startY = 2;
        dir = Direction.NORTH;

        testPlayer.setXPos(startX);
        testPlayer.setYPos(startY);
        testPlayer.setDirection(dir);
    }

    @Test
    public void walkNorthWithMoveOneProgramCard(){
        playerCard = new ProgramCard(ProgramCardAction.MOVE_ONE);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getYPos(), startY + 1);
    }

    @Test
    public void walkWestWithMoveTwoProgramCard(){
        testPlayer.setDirection(Direction.WEST);
        playerCard = new ProgramCard(ProgramCardAction.MOVE_TWO);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getXPos(), startX - 2);
    }

    @Test
    public void walkEastWithMoveThreeProgramCard(){
        testPlayer.setDirection(Direction.EAST);
        playerCard = new ProgramCard(ProgramCardAction.MOVE_THREE);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getXPos(), startX + 3);
    }

    @Test
    public void turnLeftWhenFacingNorthProgramCard(){
        playerCard = new ProgramCard(ProgramCardAction.TURN_LEFT);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getDirection(), Direction.WEST);
    }

    @Test
    public void turnRightWhenFacingSouthProgramCard(){
        testPlayer.setDirection(Direction.SOUTH);
        playerCard = new ProgramCard(ProgramCardAction.TURN_RIGHT);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getDirection(), Direction.WEST);
    }

    @Test
    public void takeUTurnWhenFacingNorthProgramCard(){
        playerCard = new ProgramCard(ProgramCardAction.U_TURN);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getDirection(), Direction.SOUTH);
    }

    @Test
    public void moveBackOneWhileFacingNorth(){
        playerCard = new ProgramCard(ProgramCardAction.MOVE_BACK_ONE);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getYPos(), startY - 1);
    }
}
