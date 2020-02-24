package inf112.skeleton.app;

import java.util.Random;

public class ProgramCard {
    private int maxPriority = 500;
    private int minPriority = 50;
    private ProgramCardAction action;
    private int priority;

    public ProgramCard(){
        this.action = ProgramCardAction.getRandomProgramCardAction();
        this.priority = new Random().nextInt(this.maxPriority - this.minPriority) + this.minPriority;
    }

    public ProgramCardAction getAction() {
        return this.action;
    }
}
