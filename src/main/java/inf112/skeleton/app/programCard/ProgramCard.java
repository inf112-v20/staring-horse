package inf112.skeleton.app.programCard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.enums.ProgramCardAction;

import java.util.Random;

public class ProgramCard {
    private int maxPriority = 500;
    private int minPriority = 50;
    private ProgramCardAction action;
    private int priority; // will be used in the near future.
    private Texture texture;
    private boolean isInHand;

    public ProgramCard(){
        this.action = ProgramCardAction.getRandomProgramCardAction();
        this.priority = new Random().nextInt(this.maxPriority - this.minPriority) + this.minPriority;
        this.isInHand = false;
    }

    /**
     * Construct ProgramCard with spesified ProgramCardAction
     * @param action - spesified ProgramCardAction
     */
    public ProgramCard(ProgramCardAction action){
        this.action = action;
        this.priority = new Random().nextInt(this.maxPriority - this.minPriority) + this.minPriority;
        this.isInHand = false;
    }

    /**
     *
     * @return a card texture with the corresponding move.
     */
    public Texture getTexture(){
        switch (this.action){
            case MOVE_ONE:
                this.texture = new Texture(Gdx.files.internal("ProgramCards/ProgramCardMove1.png"));
                break;
            case MOVE_TWO:
                this.texture = new Texture(Gdx.files.internal("ProgramCards/ProgramCardMove2.png"));
                break;
            case MOVE_THREE:
                this.texture = new Texture(Gdx.files.internal("ProgramCards/ProgramCardMove3.png"));
                break;
            case TURN_LEFT:
                this.texture = new Texture(Gdx.files.internal("ProgramCards/ProgramCardTurnLeft.png"));
                break;
            case TURN_RIGHT:
                this.texture = new Texture(Gdx.files.internal("ProgramCards/ProgramCardTurnRight.png"));
                break;
            case U_TURN:
                this.texture = new Texture(Gdx.files.internal("ProgramCards/ProgramCardUTurn.png"));
                break;
            case BACK_UP:
                this.texture = new Texture(Gdx.files.internal("ProgramCards/ProgramCardBackUp.png"));
                break;
            case AGAIN:
                this.texture = new Texture(Gdx.files.internal("ProgramCards/ProgramCardAgain.png"));
                break;
            default:
                this.texture = new Texture("ProgramCards/ProgramCardMove1Pressed.png");
                break;
        }
        return this.texture;
    }

    public ProgramCardAction getAction() {
        return this.action;
    }

    public void setIsInHand(boolean bool) {
        this.isInHand = bool;
    }

    public boolean isInHand() {
        return this.isInHand;
    }
}
