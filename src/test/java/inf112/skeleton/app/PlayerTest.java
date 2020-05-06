package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.robot.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    private Player testPlayer;
    private int startX;
    private int startY;

    @Before
    public void before(){
        testPlayer = new Player();
        testPlayer.setToTestRobot();
        testPlayer.setRespawnPoint(new Vector2(13,13));
        startX = (int) testPlayer.getPos().x;
        startY = (int) testPlayer.getPos().y;
        Direction startDirection = Direction.NORTH;

        testPlayer.setPos(new Vector2(startX,startY));
        testPlayer.setDirection(startDirection);
    }

    @Test
    public void setXPosShouldChangeTestPlayersPosition(){
        int newXPos = 10;
        testPlayer.setXPos(newXPos);

        assertEquals(testPlayer.getXPos(), newXPos);
    }

    @Test
    public void playerSetYPosShouldChangeTestPlayersPosition(){
        int newYPos = 20;
        testPlayer.setYPos(newYPos);

        assertEquals(testPlayer.getYPos(), newYPos);
    }

    @Test
    public void playerMoveForwardWhileFacingNorthShouldMovePlayerOneUp(){
        testPlayer.setDirection(Direction.NORTH);
        testPlayer.move(1, testPlayer.getDirection());

        assertEquals(testPlayer.getYPos(), startY+1);
    }

    @Test
    public void playerMoveForwardWhileFacingEastShouldMovePlayerOneRight(){
        testPlayer.setDirection(Direction.EAST);
        testPlayer.move(1, testPlayer.getDirection());

        assertEquals(testPlayer.getXPos(), startX+1);
    }

    @Test
    public void playerMoveForwardWhileFacingSouthShouldMovePlayerOneDown(){
        testPlayer.setDirection(Direction.SOUTH);
        testPlayer.move(1, testPlayer.getDirection());

        assertEquals(testPlayer.getYPos(), startY-1);
    }

    @Test
    public void playerMoveForwardWhileFacingWestShouldMovePlayerOneLeft(){
        testPlayer.setDirection(Direction.WEST);
        testPlayer.move(1, testPlayer.getDirection());

        assertEquals(testPlayer.getXPos(), startX-1);
    }


    @Test
    public void rotateClockWiseShouldRotatePlayerFromNorthToEast(){
        testPlayer.setDirection(Direction.NORTH);
        testPlayer.rotateClockwise();

        assertEquals(testPlayer.getDirection(), Direction.EAST);
    }

    @Test
    public void rotateCounterClockWiseShouldRotatePlayerFromNorthToEast() {
        testPlayer.setDirection(Direction.NORTH);
        testPlayer.rotateCounterClockwise();

        assertEquals(testPlayer.getDirection(), Direction.WEST);
    }
}
