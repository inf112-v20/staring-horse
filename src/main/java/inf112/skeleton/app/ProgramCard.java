package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

import java.util.Random;

public class ProgramCard {
    private int maxPriority = 500;
    private int minPriority = 50;
    private ProgramCardAction action;
    private int priority;
    private Texture texture;

    public ProgramCard(){
        this.action = ProgramCardAction.getRandomProgramCardAction();
        this.priority = new Random().nextInt(this.maxPriority - this.minPriority) + this.minPriority;
    }

    public ProgramCard(ProgramCardAction action){
        this.action = action;
        this.priority = new Random().nextInt(this.maxPriority - this.minPriority) + this.minPriority;
    }

    public Texture getTexture(){
        switch (this.action){
            case MOVE_ONE:
                this.texture = new Texture(Gdx.files.internal("ProgramCardMove1.png"));
                break;
        }
        this.texture = new Texture(Gdx.files.internal("ProgramCardMove1.png"));
        return this.texture;
    }

    public ProgramCardAction getAction() {
        return this.action;
    }
}
