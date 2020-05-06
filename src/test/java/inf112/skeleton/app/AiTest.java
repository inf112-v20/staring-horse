package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.robot.AI;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AiTest {

    private AI testAI;
    private int startX;
    private int startY;
    private Direction startDirection;

    @Before
    public void before(){
        testAI = new AI();
        testAI.setToTestRobot();
        testAI.setRespawnPoint(new Vector2(13,13));
        startX = (int) testAI.getPos().x;
        startY = (int) testAI.getPos().y;
        startDirection = Direction.NORTH;

        testAI.setPos(new Vector2(startX,startY));
        testAI.setDirection(startDirection);
    }

    @Test
    public void setXPosShouldChangetestAIsPosition(){
        int newXPos = 10;
        testAI.setXPos(newXPos);

        assertEquals(testAI.getXPos(), newXPos);
    }

    @Test
    public void aiSetYPosShouldChangetestAIsPosition(){
        int newYPos = 20;
        testAI.setYPos(newYPos);

        assertEquals(testAI.getYPos(), newYPos);
    }

    @Test
    public void aiMoveForwardWhileFacingNorthShouldMoveAiOneUp(){
        testAI.setDirection(Direction.NORTH);
        testAI.move(1, testAI.getDirection());

        assertEquals(testAI.getYPos(), startY+1);
    }

    @Test
    public void aiMoveForwardWhileFacingEastShouldMoveAiOneRight(){
        testAI.setDirection(Direction.EAST);
        testAI.move(1, testAI.getDirection());

        assertEquals(testAI.getXPos(), startX+1);
    }

    @Test
    public void aiMoveForwardWhileFacingSouthShouldMoveAiOneDown(){
        testAI.setDirection(Direction.SOUTH);
        testAI.move(1, testAI.getDirection());

        assertEquals(testAI.getYPos(), startY-1);
    }

    @Test
    public void aiMoveForwardWhileFacingWestShouldMoveAiOneLeft(){
        testAI.setDirection(Direction.WEST);
        testAI.move(1, testAI.getDirection());

        assertEquals(testAI.getXPos(), startX-1);
    }


    @Test
    public void rotateClockWiseShouldRotateAiFromNorthToEast(){
        testAI.setDirection(Direction.NORTH);
        testAI.rotateClockwise();

        assertEquals(testAI.getDirection(), Direction.EAST);
    }

    @Test
    public void rotateCounterClockWiseShouldRotateAiFromNorthToEast() {
        testAI.setDirection(Direction.NORTH);
        testAI.rotateCounterClockwise();

        assertEquals(testAI.getDirection(), Direction.WEST);
    }
}

