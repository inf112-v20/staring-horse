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
     *
     * @param pos Vector2 position
     * @param dir Direction
     * @param action ProgramCardAction to be performed
     * @return Vector2 position after ProgramCardAction
     */
    public static Vector2 getPositionAfterProgramCardAction(Vector2 pos, Direction dir, ProgramCardAction action) {
        switch (action) {
            case MOVE_ONE:
                pos = Direction.getPosInDirection(pos,dir);
                return pos;
            case MOVE_TWO:
                pos = Direction.getPosInDirection(pos,dir);
                pos = Direction.getPosInDirection(pos,dir);
                return pos;
            case MOVE_THREE:
                pos = Direction.getPosInDirection(pos,dir);
                pos = Direction.getPosInDirection(pos,dir);
                pos = Direction.getPosInDirection(pos,dir);
                return pos;
            case BACK_UP:
                pos = Direction.getPosInDirection(pos,Direction.oppositeOf(dir));
                return pos;
            default:
                break;
        }
        return pos;
    }
}
