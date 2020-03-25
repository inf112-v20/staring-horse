package inf112.skeleton.app;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.player.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTest {

    Player testPlayer;
    int startX;
    int startY;
    Direction startDirection;

    @Before
    public void before(){
        testPlayer = new Player();
        testPlayer.setToTestPlayer();
        startX = 5;
        startY = 2;
        startDirection = Direction.NORTH;

        testPlayer.setYPos(startY);
        testPlayer.setXPos(startX);
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
        testPlayer.moveForward(1);

        assertEquals(testPlayer.getYPos(), startY+1);
    }

    @Test
    public void playerMoveForwardWhileFacingEastShouldMovePlayerOneRight(){
        testPlayer.setDirection(Direction.EAST);
        testPlayer.moveForward(1);

        assertEquals(testPlayer.getXPos(), startX+1);
    }

    @Test
    public void playerMoveForwardWhileFacingSouthShouldMovePlayerOneDown(){
        testPlayer.setDirection(Direction.SOUTH);
        testPlayer.moveForward(1);

        assertEquals(testPlayer.getYPos(), startY-1);
    }

    @Test
    public void playerMoveForwardWhileFacingWestShouldMovePlayerOneLeft(){
        testPlayer.setDirection(Direction.WEST);
        testPlayer.moveForward(1);

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
