package inf112.skeleton.app;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

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

    /**
     * 
     * @param action
     */
    public ProgramCard(ProgramCardAction action){
        this.action = action;
        this.priority = new Random().nextInt(this.maxPriority - this.minPriority) + this.minPriority;
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
        }
        this.texture = new Texture(Gdx.files.internal("ProgramCards/ProgramCardMove1.png"));
        return this.texture;
    }

    public ProgramCardAction getAction() {
        return this.action;
    }

    /**
     * Creates a new ImageButton of a input card
     * @return a ImageButton.
     */
    public ImageButton makeCardImageButton(final Player player, final GameScreen gameScreen) {
        final ProgramCard programCard = this;
        Texture programCardTexturePressed = new Texture("ProgramCards/ProgramCardMove1Pressed.png");
        // create a drawable for each state of the button
        final Drawable drawable = new TextureRegionDrawable(new TextureRegion(this.getTexture()));
        Drawable pressedDrawable = new TextureRegionDrawable(new TextureRegion(programCardTexturePressed));
        ImageButton imageButton = new ImageButton(drawable);

        imageButton.setSize((float) 200 / 4, (float) 340 / 4);
        imageButton.getStyle() .imageUp = drawable;
        imageButton.getStyle() .imageDown = pressedDrawable;
        imageButton.setPosition(30,55);

        imageButton.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Not Pressed.");
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Pressed: " + programCard.getAction());

                gameScreen.unrenderPlayer();
                player.performProgramCardAction(programCard);
                gameScreen.renderPlayer();

                return true;
            }
        });
        return imageButton;
    }
}
