package inf112.skeleton.app.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.screen.GameScreen;

import java.util.Random;

public class ProgramCard {
    private int maxPriority = 500;
    private int minPriority = 50;
    private ProgramCardAction action;
    private int priority;
    private Texture texture;
    private ImageButton cardButton;
    private boolean isInHand;

    public ProgramCard(){
        this.action = ProgramCardAction.getRandomProgramCardAction();
        this.priority = new Random().nextInt(this.maxPriority - this.minPriority) + this.minPriority;
        this.isInHand = false;
    }

    /**
     * 
     * @param action
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
        // TODO make position be depended on the screen size or viewport instead of static.
        imageButton.setPosition(30,55);

        imageButton.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                // could be useful in the future.
            }

            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Pressed: " + programCard.getAction());

                if (!programCard.isInHand()) {
                    player.addToChosenCards(programCard);
                    gameScreen.showPlayersHand();

                    if(player.isHandFull()) {
                        player.executeCardsInHand();
                    }
                }

                return true;
            }
        });
        cardButton = imageButton;
        return imageButton;
    }

    public Actor getCardButton() {
        return cardButton;
    }

    public void setIsInHand(boolean bool) {
        this.isInHand = bool;
    }

    public boolean isInHand() {
        return this.isInHand;
    }
}
