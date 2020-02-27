package inf112.skeleton.app;

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
     * @return random ProgramCardAction
     */
    public static ProgramCardAction getRandomProgramCardAction(){
        return ACTIONS[RANDOM.nextInt(ACTIONS.length)];
    }
}
