package inf112.skeleton.app;

import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.ProgramCardAction;
import org.junit.Before;
import org.junit.Test;

import static inf112.skeleton.app.enums.ProgramCardAction.getDirectionAfterProgramCardAction;
import static inf112.skeleton.app.enums.ProgramCardAction.getPositionAfterProgramCardAction;
import static org.junit.Assert.assertEquals;

public class ProgramCardActionTest {

    Direction direction;
    Vector2 position;

    @Before
    public void before() {
        direction = Direction.NORTH;
        position = new Vector2(0,0);
    }

    @Test
    public void shouldGetPositionAfterProgramCardActionMoveOne() {

        ProgramCardAction action = ProgramCardAction.MOVE_ONE;

        assertEquals(getPositionAfterProgramCardAction(position, direction, action), new Vector2(0,1));

    }

    @Test
    public void shouldGetPositionAfterProgramCardActionMoveTwo() {

        ProgramCardAction action = ProgramCardAction.MOVE_TWO;

        assertEquals(getPositionAfterProgramCardAction(position, direction, action), new Vector2(0,2));

    }

    @Test
    public void shouldGetPositionAfterProgramCardActionMoveThree() {

        ProgramCardAction action = ProgramCardAction.MOVE_THREE;

        assertEquals(getPositionAfterProgramCardAction(position, direction, action), new Vector2(0,3));

    }

    @Test
    public void shouldGetPositionAfterProgramCardActionBackUp() {

        ProgramCardAction action = ProgramCardAction.BACK_UP;

        assertEquals(getPositionAfterProgramCardAction(position, direction, action), new Vector2(0,-1));

    }

    @Test
    public void shouldGetPositionAfterProgramCardActionTurnLeft() {

        ProgramCardAction action = ProgramCardAction.TURN_LEFT;

        assertEquals(getPositionAfterProgramCardAction(position, direction, action), new Vector2(0,0));

    }

    @Test
    public void shouldGetPositionAfterProgramCardActionTurnRight() {

        ProgramCardAction action = ProgramCardAction.TURN_RIGHT;

        assertEquals(getPositionAfterProgramCardAction(position, direction, action), new Vector2(0,0));

    }

    @Test
    public void shouldGetPositionAfterProgramCardActionUTurn() {

        ProgramCardAction action = ProgramCardAction.U_TURN;

        assertEquals(getPositionAfterProgramCardAction(position, direction, action), new Vector2(0,0));

    }

    @Test
    public void shouldGetDirectionWestAfterProgramCardActionTurnLeft() {

        ProgramCardAction action = ProgramCardAction.TURN_LEFT;

        assertEquals(getDirectionAfterProgramCardAction(direction, action), Direction.WEST);

    }

    @Test
    public void shouldGetDirectionEastAfterProgramCardActionTurnRight() {

        ProgramCardAction action = ProgramCardAction.TURN_RIGHT;

        assertEquals(getDirectionAfterProgramCardAction(direction, action), Direction.EAST);

    }

    @Test
    public void shouldGetDirectionSouthAfterProgramCardActionUTurn() {

        ProgramCardAction action = ProgramCardAction.U_TURN;

        assertEquals(getDirectionAfterProgramCardAction(direction, action), Direction.SOUTH);

    }
}
