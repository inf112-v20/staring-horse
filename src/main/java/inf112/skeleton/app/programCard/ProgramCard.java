package inf112.skeleton.app.programCard;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import inf112.skeleton.app.enums.ProgramCardAction;

import java.util.Random;

public class ProgramCard {
    private ProgramCardAction action;
    private int priority;
    private Texture texture;

    public ProgramCard(){
        this.action = ProgramCardAction.getRandomProgramCardAction();
        generatePriority();
    }

    /**
     * Construct ProgramCard with spesified ProgramCardAction
     * @param action - spesified ProgramCardAction
     */
    public ProgramCard(ProgramCardAction action){
        this.action = action;
        generatePriority();
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

    /**
     * Generate priority corresponding to the ProgramCard's action
     */
    private void generatePriority(){
        int p;

        switch (this.action) {
            case MOVE_THREE:
                p = 800;
                break;
            case MOVE_TWO:
                p = 700;
                break;
            case MOVE_ONE:
                p = 600;
                break;
            case BACK_UP:
                p = 400;
                break;
            case TURN_LEFT:
            case TURN_RIGHT:
                p = 200;
                break;
            case U_TURN:
                p = 100;
                break;
            case AGAIN:
                p = 0;
                break;
            default:
                p = -1;
                break;
        }

        p += (new Random().nextInt(100))-50;

        this.priority = p;
    }

    public int getPriority(){
        return this.priority;
    }
}
