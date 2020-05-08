package inf112.skeleton.app.enums;

import com.badlogic.gdx.math.Vector2;
import java.util.Random;

public enum ProgramCardAction {
    MOVE_ONE,
    MOVE_TWO,
    MOVE_THREE,
    TURN_LEFT,
    TURN_RIGHT,
    U_TURN,
    BACK_UP,
    AGAIN;

    private static final ProgramCardAction[] ACTIONS = values();
    private static final Random RANDOM = new Random();

    /**
     * @return all ProgramCardAction
     */
    public static ProgramCardAction[] getAllProgramCardActions() { return values(); }

    /**
     * @return random ProgramCardAction
     */
    public static ProgramCardAction getRandomProgramCardAction(){
        return ACTIONS[RANDOM.nextInt(ACTIONS.length)];
    }

    /**
     * @return random ProgramCardAction that moves robot forward
     */
    public static ProgramCardAction getRandomMoveForwardProgramCardAction(){
        ProgramCardAction[] moveProgramCardActions = {MOVE_ONE, MOVE_TWO, MOVE_THREE};
        return moveProgramCardActions[RANDOM.nextInt(moveProgramCardActions.length)];
    }


    /**
     * Get position after ProgramCard action is performed
     * @param pos Vector2 position
     * @param dir Direction
     * @param action ProgramCardAction to be performed
     * @return Vector2 position after ProgramCardAction
     */
    public static Vector2 getPositionAfterProgramCardAction(Vector2 pos, Direction dir, ProgramCardAction action) {
        switch (action) {
            case MOVE_ONE:
                pos = Direction.getPosInDirection(pos,dir);
                break;
            case MOVE_TWO:
                pos = Direction.getPosInDirection(pos,dir);
                pos = Direction.getPosInDirection(pos,dir);
                break;
            case MOVE_THREE:
                pos = Direction.getPosInDirection(pos,dir);
                pos = Direction.getPosInDirection(pos,dir);
                pos = Direction.getPosInDirection(pos,dir);
                break;
            case BACK_UP:
                pos = Direction.getPosInDirection(pos,Direction.oppositeOf(dir));
                break;
            default:
                break;
        }
        return pos;
    }

    /**
     * Get direction after ProgramCard action is performed
     * @param dir Direction the robot is facing
     * @param action ProgramCardAction
     * @return Direction after ProgramCard action is performed
     */
    public static Direction getDirectionAfterProgramCardAction(Direction dir, ProgramCardAction action) {
        switch (action) {
            case TURN_RIGHT:
                dir = Direction.rotateClockwise(dir);
                break;
            case TURN_LEFT:
                dir = Direction.rotateCounterClockwise(dir);
                break;
            case U_TURN:
                dir = Direction.oppositeOf(dir);
                break;
            default:
                break;
        }
        return dir;
    }
}
