package inf112.skeleton.app.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.screen.GameScreen;

public class Player {

    private int xPos;
    private int yPos;
    private Direction direction;
    private int healthPoints;
    private ProgramCardAction previousAction;
    private CardDeck cardDeck;
    private ProgramCard[] hand;

    private TextureRegion playerTexture;
    private int numCardsInHand;

    // TODO Organize methods so its more readable.

    public Player() {
        this.xPos = 10;
        this.yPos = 15;
        this.direction = Direction.NORTH;
        this.cardDeck = new CardDeck();
        this.hand = new ProgramCard[5];
        this.numCardsInHand = 0;
    }

    public void setXPos(int x) {
        this.xPos = x;
    }

    public void setYPos(int y) {
        this.yPos = y;
    }

    public int getYPos() { return yPos; }

    public int getXPos() { return xPos; }

    public void setDirection(Direction dir){
        this.direction = dir;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public void moveBackward(int backwardDistance){
        switch (this.direction) {
            case WEST:
                for (int i = 0; i < backwardDistance; i++){this.moveEast();}
                break;
            case SOUTH:
                for(int i = 0; i < backwardDistance; i++){this.moveNorth();}
                break;
            case EAST:
                for(int i = 0; i < backwardDistance; i++){this.moveWest();}
                break;
            case NORTH:
                for(int i = 0; i < backwardDistance; i++){this.moveSouth();}
                break;
            default:
                break;
        }
    }

    public void moveForward(int forwardDistance){
        switch (this.direction) {
            case WEST:
                for (int i = 0; i < forwardDistance; i++){this.moveWest();}
                break;
            case SOUTH:
                for(int i = 0; i < forwardDistance; i++){this.moveSouth();}
                break;
            case EAST:
                for(int i = 0; i < forwardDistance; i++){this.moveEast();}
                break;
            case NORTH:
                for(int i = 0; i < forwardDistance; i++){this.moveNorth();}
                break;
            default:
                break;
        }
    }

    public void moveNorth(){
        this.setYPos(this.getYPos()+1);
    }

    public void moveEast(){
        this.setXPos(this.getXPos()+1);
    }

    public void moveSouth(){
        this.setYPos(this.getYPos()-1);
    }

    public void moveWest(){
        this.setXPos(this.getXPos()-1);
    }

    public void rotateClockwise(){
        switch (this.direction) {
            case WEST:
                this.direction = Direction.NORTH;
                break;
            case SOUTH:
                this.direction = Direction.WEST;
                break;
            case EAST:
                this.direction = Direction.SOUTH;
                break;
            case NORTH:
                this.direction = Direction.EAST;
                break;
            default:
                break;
        }
    }

    public void rotateCounterClockwise(){
        switch (this.direction) {
            case WEST:
                this.direction = Direction.SOUTH;
                break;
            case SOUTH:
                this.direction = Direction.EAST;
                break;
            case EAST:
                this.direction = Direction.NORTH;
                break;
            case NORTH:
                this.direction = Direction.WEST;
                break;
            default:
                break;
        }
    }

    public void performProgramCardAction(ProgramCard progCard) {
        ProgramCardAction action = progCard.getAction();

        switch (action) {
            case MOVE_ONE:
                this.moveForward(1);
                break;
            case MOVE_TWO:
                this.moveForward(2);
                break;
            case MOVE_THREE:
                this.moveForward(3);
                break;
            case TURN_LEFT:
                this.rotateCounterClockwise();
                break;
            case TURN_RIGHT:
                this.rotateClockwise();
                break;
            case BACK_UP:
                this.moveBackward(1);
                break;
            case U_TURN:
                this.rotateClockwise();
                this.rotateClockwise();
                break;
            case AGAIN:
                if (this.previousAction != null) {
                    this.performProgramCardAction(new ProgramCard(this.previousAction));
                }
                break;
            default:
                System.out.println("ProgramCardAction not implemented: " + action);
                break;
        }

        if (action != ProgramCardAction.AGAIN) {
            this.previousAction = action;
        }
    }

    public void drawNewDeck(){
        this.cardDeck.drawNineProgramCards();
    }

    public ProgramCard getProgramCard(int index){
        return this.cardDeck.getCard(index);
    }

    public TextureRegion getTexture() {
        return this.playerTexture;
    }

    public void loadAssets() {
        this.playerTexture = new TextureRegion(new Texture("Robo.png"));

    }

    /**
     * Add ProgramCard to player hand
     * @param card
     */
    public void addToChosenCards(ProgramCard card) {
        if (numCardsInHand < 5) {
            hand[numCardsInHand] = card;
            numCardsInHand += 1;
            card.setIsInHand(true);
        } else {
            System.out.println("Your hand is full!");
        }
    }

    public ProgramCard[] getHand() {
        return this.hand;
    }

    public boolean myTurn() {
        return true;
    }

    public boolean isHandFull() {
        for (int i = 0; i < this.hand.length; i++) {
            if (this.hand[i] == null){
                return false;
            }
        }
        return true;
    }

    /**
     * Execute all programcards in hand
     * @param gameScreen
     */
    public void executeCardsInHand(GameScreen gameScreen){
        for (int i = 0; i < this.hand.length; i++) {
            ProgramCard currentCard = this.hand[i];

            gameScreen.unrenderPlayer();
            performProgramCardAction(currentCard);
            gameScreen.renderPlayer();

            System.out.println("Executed " + currentCard.getAction());
        }

        clearHand();
    }

    private void clearHand() {
        this.hand = new ProgramCard[hand.length];
    }
}
