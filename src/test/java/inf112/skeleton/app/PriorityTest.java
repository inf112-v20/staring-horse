package inf112.skeleton.app;

import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.programCard.ProgramCard;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PriorityTest {

    @Test
    public void move3ProgramCardShouldHavePriorityBetween750And849(){
        ProgramCard card = new ProgramCard(ProgramCardAction.MOVE_THREE);
        int cardPriority = card.getPriority();

        int priorityRange = 800;
        int lowestPriority = priorityRange-50;
        int highestPriority = priorityRange+50;

        assertTrue(cardPriority >= lowestPriority && cardPriority < highestPriority);
    }

    @Test
    public void move2ProgramCardShouldHavePriorityBetween650And749(){
        ProgramCard card = new ProgramCard(ProgramCardAction.MOVE_TWO);
        int cardPriority = card.getPriority();

        int priorityRange = 700;
        int lowestPriority = priorityRange-50;
        int highestPriority = priorityRange+50;

        assertTrue(cardPriority >= lowestPriority && cardPriority < highestPriority);
    }

    @Test
    public void move1ProgramCardShouldHavePriorityBetween550And449(){
        ProgramCard card = new ProgramCard(ProgramCardAction.MOVE_ONE);
        int cardPriority = card.getPriority();

        int priorityRange = 600;
        int lowestPriority = priorityRange-50;
        int highestPriority = priorityRange+50;

        assertTrue(cardPriority >= lowestPriority && cardPriority < highestPriority);
    }

    @Test
    public void backUpProgramCardShouldHavePriorityBetween350And449(){
        ProgramCard card = new ProgramCard(ProgramCardAction.BACK_UP);
        int cardPriority = card.getPriority();

        int priorityRange = 400;
        int lowestPriority = priorityRange-50;
        int highestPriority = priorityRange+50;

        assertTrue(cardPriority >= lowestPriority && cardPriority < highestPriority);
    }

    @Test
    public void turnLeftProgramCardShouldHavePriorityBetween150And249(){
        ProgramCard card = new ProgramCard(ProgramCardAction.TURN_LEFT);
        int cardPriority = card.getPriority();

        int priorityRange = 200;
        int lowestPriority = priorityRange-50;
        int highestPriority = priorityRange+50;

        assertTrue(cardPriority >= lowestPriority && cardPriority < highestPriority);
    }

    @Test
    public void turnRightProgramCardShouldHavePriorityBetween150And249(){
        ProgramCard card = new ProgramCard(ProgramCardAction.TURN_RIGHT);
        int cardPriority = card.getPriority();

        int priorityRange = 200;
        int lowestPriority = priorityRange-50;
        int highestPriority = priorityRange+50;

        assertTrue(cardPriority >= lowestPriority && cardPriority < highestPriority);
    }

    @Test
    public void uTurnProgramCardShouldHavePriorityBetween50And149(){
        ProgramCard card = new ProgramCard(ProgramCardAction.U_TURN);
        int cardPriority = card.getPriority();

        int priorityRange = 100;
        int lowestPriority = priorityRange-50;
        int highestPriority = priorityRange+50;

        assertTrue(cardPriority >= lowestPriority && cardPriority < highestPriority);
    }

    @Test
    public void againProgramCardShouldHavePriorityBetweenNegative50And49(){
        ProgramCard card = new ProgramCard(ProgramCardAction.BACK_UP);
        int cardPriority = card.getPriority();

        int priorityRange = 400;
        int lowestPriority = priorityRange-50;
        int highestPriority = priorityRange+50;

        assertTrue(cardPriority >= lowestPriority && cardPriority < highestPriority);
    }

}
