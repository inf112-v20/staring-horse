package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import org.junit.Test;

import java.util.WeakHashMap;

import static org.junit.Assert.assertEquals;
import static inf112.skeleton.app.enums.Direction.*;

public class directionTest {

    @Test
    public void shouldRotateClockwiseWestToNorth() {

        Direction direction = WEST;

        assertEquals(rotateClockwise(direction), NORTH);
    }

    @Test
    public void shouldRotateClockwiseNorthToEast() {

        Direction direction = NORTH;

        assertEquals(rotateClockwise(direction), EAST);
    }

    @Test
    public void shouldRotateClockwiseEastToSouth() {

        Direction direction = EAST;

        assertEquals(rotateClockwise(direction), SOUTH);
    }

    @Test
    public void shouldRotateClockwiseSouthToWest() {

        Direction direction = SOUTH;

        assertEquals(rotateClockwise(direction), WEST);
    }

    @Test
    public void shouldRotateCounterClockwiseWestToSouth() {

        Direction direction = WEST;

        assertEquals(rotateCounterClockwise(direction), SOUTH);
    }

    @Test
    public void shouldRotateCounterClockwiseSouthToEast() {

        Direction direction = SOUTH;

        assertEquals(rotateCounterClockwise(direction), EAST);
    }

    @Test
    public void shouldRotateCounterClockwiseEastToNorth() {

        Direction direction = EAST;

        assertEquals(rotateCounterClockwise(direction), NORTH);
    }

    @Test
    public void shouldRotateCounterClockwiseNorthToWest() {

        Direction direction = NORTH;

        assertEquals(rotateCounterClockwise(direction), WEST);
    }

    @Test
    public void shouldGiveOppositeDirectionOfNorth() {

        Direction direction = NORTH;

        assertEquals(oppositeOf(direction), SOUTH);
    }

    @Test
    public void shouldGiveOppositeDirectionOfSouth() {

        Direction direction = SOUTH;

        assertEquals(oppositeOf(direction), NORTH);
    }

    @Test
    public void shouldGiveOppositeDirectionOfEast() {

        Direction direction = EAST;

        assertEquals(oppositeOf(direction), WEST);
    }

    @Test
    public void shouldGiveOppositeDirectionOfWest() {

        Direction direction = WEST;

        assertEquals(oppositeOf(direction), EAST);
    }

    @Test
    public void shouldGetCorrectPositionForMoveInDirectionNorth() {
        Vector2 position = new Vector2(0,0);
        Direction direction = NORTH;

        Vector2 correctNextPosition = new Vector2(0,1);

        assertEquals(getPosInDirection(position, direction), correctNextPosition);

    }

    @Test
    public void shouldGetCorrectPositionForMoveInDirectionSouth() {
        Vector2 position = new Vector2(0,0);
        Direction direction = SOUTH;

        Vector2 correctNextPosition = new Vector2(0,-1);

        assertEquals(getPosInDirection(position, direction), correctNextPosition);

    }

    @Test
    public void shouldGetCorrectPositionForMoveInDirectionEast() {
        Vector2 position = new Vector2(0,0);
        Direction direction = EAST;

        Vector2 correctNextPosition = new Vector2(1,0);

        assertEquals(getPosInDirection(position, direction), correctNextPosition);

    }

    @Test
    public void shouldGetCorrectPositionForMoveInDirectionWest() {
        Vector2 position = new Vector2(0,0);
        Direction direction = WEST;

        Vector2 correctNextPosition = new Vector2(-1,0);

        assertEquals(getPosInDirection(position, direction), correctNextPosition);

    }

}
