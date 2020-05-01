package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.robot.Player;
import inf112.skeleton.app.programCard.ProgramCard;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ProgramCardTest {
    Player testPlayer;
    ProgramCard playerCard;
    int startX;
    int startY;
    Direction startDir;

    @Before
    public void before(){
        testPlayer = new Player();
        testPlayer.setToTestRobot();
        startX = 5;
        startY = 2;
        startDir = Direction.NORTH;

        testPlayer.setPos(new Vector2(startX,startY));
        testPlayer.setDirection(startDir);
    }

    @Test
    public void shouldMoveOneNorthWithMoveOneProgramCard(){
        playerCard = new ProgramCard(ProgramCardAction.MOVE_ONE);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getYPos(), startY + 1);
    }

    @Test
    public void shouldMoveTwoWestWithMoveTwoProgramCard(){
        testPlayer.setDirection(Direction.WEST);
        playerCard = new ProgramCard(ProgramCardAction.MOVE_TWO);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getXPos(), startX - 2);
    }

    @Test
    public void shouldMoveThreeEastWithMoveThreeProgramCard(){
        testPlayer.setDirection(Direction.EAST);
        playerCard = new ProgramCard(ProgramCardAction.MOVE_THREE);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getXPos(), startX + 3);
    }

    @Test
    public void shouldTurnWestWhenFacingNorthProgramCard(){
        playerCard = new ProgramCard(ProgramCardAction.TURN_LEFT);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getDirection(), Direction.WEST);
    }

    @Test
    public void shouldTurnWestWhenFacingSouthProgramCard(){
        testPlayer.setDirection(Direction.SOUTH);
        playerCard = new ProgramCard(ProgramCardAction.TURN_RIGHT);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getDirection(), Direction.WEST);
    }

    @Test
    public void shouldMakeUTurnWhenFacingNorthProgramCard(){
        playerCard = new ProgramCard(ProgramCardAction.U_TURN);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getDirection(), Direction.SOUTH);
    }

    @Test
    public void shouldMoveBackOneWhileFacingNorth(){
        playerCard = new ProgramCard(ProgramCardAction.BACK_UP);
        testPlayer.performProgramCardAction(playerCard);

        assertEquals(testPlayer.getYPos(), startY - 1);
    }

    @Test
    //shouldMovePlayerTwoTilesWithMoveOneCardThenOneAgainCard()
    public void moveOneCardThenOneAgainCardShouldMovePlayerTwoTiles(){
        playerCard = new ProgramCard(ProgramCardAction.MOVE_ONE);
        testPlayer.performProgramCardAction(playerCard);
        ProgramCard playerCardAgain = new ProgramCard(ProgramCardAction.AGAIN);
        testPlayer.performProgramCardAction(playerCardAgain);

        assertEquals(testPlayer.getYPos(), startY + 2);
    }
}
