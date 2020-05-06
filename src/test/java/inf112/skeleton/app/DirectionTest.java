package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static inf112.skeleton.app.enums.Direction.*;

public class DirectionTest {

    @Test
    public void shouldRotateClockwiseWestToNorth() {

        assertEquals(rotateClockwise(WEST), NORTH);
    }

    @Test
    public void shouldRotateClockwiseNorthToEast() {

        assertEquals(rotateClockwise(NORTH), EAST);
    }

    @Test
    public void shouldRotateClockwiseEastToSouth() {

        assertEquals(rotateClockwise(EAST), SOUTH);
    }

    @Test
    public void shouldRotateClockwiseSouthToWest() {

        assertEquals(rotateClockwise(SOUTH), WEST);
    }

    @Test
    public void shouldRotateCounterClockwiseWestToSouth() {

        assertEquals(rotateCounterClockwise(WEST), SOUTH);
    }

    @Test
    public void shouldRotateCounterClockwiseSouthToEast() {

        assertEquals(rotateCounterClockwise(SOUTH), EAST);
    }

    @Test
    public void shouldRotateCounterClockwiseEastToNorth() {

        assertEquals(rotateCounterClockwise(EAST), NORTH);
    }

    @Test
    public void shouldRotateCounterClockwiseNorthToWest() {

        assertEquals(rotateCounterClockwise(NORTH), WEST);
    }

    @Test
    public void shouldGiveOppositeDirectionOfNorth() {

        assertEquals(oppositeOf(NORTH), SOUTH);
    }

    @Test
    public void shouldGiveOppositeDirectionOfSouth() {

        assertEquals(oppositeOf(SOUTH), NORTH);
    }

    @Test
    public void shouldGiveOppositeDirectionOfEast() {

        assertEquals(oppositeOf(EAST), WEST);
    }

    @Test
    public void shouldGiveOppositeDirectionOfWest() {

        assertEquals(oppositeOf(WEST), EAST);
    }

    @Test
    public void shouldGetCorrectPositionForMoveInDirectionNorth() {
        Vector2 position = new Vector2(0,0);

        Vector2 correctNextPosition = new Vector2(0,1);

        assertEquals(getPosInDirection(position, NORTH), correctNextPosition);

    }

    @Test
    public void shouldGetCorrectPositionForMoveInDirectionSouth() {
        Vector2 position = new Vector2(0,0);

        Vector2 correctNextPosition = new Vector2(0,-1);

        assertEquals(getPosInDirection(position, SOUTH), correctNextPosition);

    }

    @Test
    public void shouldGetCorrectPositionForMoveInDirectionEast() {
        Vector2 position = new Vector2(0,0);

        Vector2 correctNextPosition = new Vector2(1,0);

        assertEquals(getPosInDirection(position, EAST), correctNextPosition);

    }

    @Test
    public void shouldGetCorrectPositionForMoveInDirectionWest() {
        Vector2 position = new Vector2(0,0);

        Vector2 correctNextPosition = new Vector2(-1,0);

        assertEquals(getPosInDirection(position, WEST), correctNextPosition);

    }

}
