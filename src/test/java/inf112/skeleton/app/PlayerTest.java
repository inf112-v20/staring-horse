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
    public void playerMoveForwardWhileNorthMovesPlayerUpOne(){
        testPlayer.moveForward();

        assertEquals(testPlayer.getYPos(), startY+1);
    }

    @Test
    public void rotateClockWiseRotatesPlayerFromNorthToEast(){
        testPlayer.rotateClockwise();

        assertEquals(testPlayer.getDirection(), Direction.EAST);
    }
}
