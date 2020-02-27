package inf112.skeleton.app;

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
        startX = 5;
        startY = 2;
        startDirection = Direction.NORTH;

        testPlayer.setYPos(startY);
        testPlayer.setXPos(startX);
        testPlayer.setDirection(startDirection);
    }

    @Test
    public void playerSetXSetsCorrectX(){
        int newXPos = 10;
        testPlayer.setXPos(newXPos);

        assertEquals(testPlayer.getXPos(), newXPos);
    }

    @Test
    public void playerSetYSetsCorrectY(){
        int newYPos = 20;
        testPlayer.setYPos(newYPos);

        assertEquals(testPlayer.getYPos(), newYPos);
    }

    @Test
    public void playerMoveForwardWhileFacingNorthMovesPlayerOneUp(){
        testPlayer.setDirection(Direction.NORTH);
        testPlayer.moveForward(1);

        assertEquals(testPlayer.getYPos(), startY+1);
    }

    @Test
    public void playerMoveForwardWhileFacingEastMovesPlayerOneRight(){
        testPlayer.setDirection(Direction.EAST);
        testPlayer.moveForward(1);

        assertEquals(testPlayer.getXPos(), startX+1);
    }

    @Test
    public void playerMoveForwardWhileFacingSouthMovesPlayerOneDown(){
        testPlayer.setDirection(Direction.SOUTH);
        testPlayer.moveForward(1);

        assertEquals(testPlayer.getYPos(), startY-1);
    }

    @Test
    public void playerMoveForwardWhileFacingWestMovesPlayerOneLeft(){
        testPlayer.setDirection(Direction.WEST);
        testPlayer.moveForward(1);

        assertEquals(testPlayer.getXPos(), startX-1);
    }


    @Test
    public void rotateClockWiseRotatesPlayerFromNorthToEast(){
        testPlayer.setDirection(Direction.NORTH);
        testPlayer.rotateClockwise();

        assertEquals(testPlayer.getDirection(), Direction.EAST);
    }

    @Test
    public void rotateCounterClockWiseRotatesPlayerFromNorthToEast(){
        testPlayer.setDirection(Direction.NORTH);
        testPlayer.rotateCounterClockwise();

        assertEquals(testPlayer.getDirection(), Direction.WEST);
    }
}
