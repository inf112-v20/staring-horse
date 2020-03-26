package inf112.skeleton.app.player;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObjects;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.screen.GameScreen;
import org.lwjgl.Sys;

import java.util.ArrayList;

public class Player {

    private int xPos;
    private int yPos;
    private Direction direction;

    private int respawnXPos;
    private int respawnYPos;
    private Direction respawnDirection;

    private int healthPoints;
    private int lives;

    private ProgramCardAction previousAction;
    private CardDeck cardDeck;
    private ProgramCard[] hand;

    private TextureRegion playerTexture;
    private int numCardsInHand;

    private ArrayList<String> flags;

    // bool set to true when using player for tests (solves error when
    // referencing TmxMapLoader in GameScreen while in tests)
    private boolean isTestPlayer = false;

    public Player() {
        this.respawnXPos = 10;
        this.respawnYPos = 16;
        this.respawnDirection = Direction.NORTH;

        this.xPos = respawnXPos;
        this.yPos = respawnYPos;
        this.direction = respawnDirection;

        this.cardDeck = new CardDeck();
        this.hand = new ProgramCard[5];
        this.numCardsInHand = 0;

        this.healthPoints = 10;
        this.lives = 3;

        this.flags = new ArrayList<>();
    }


    /**
     * checks if the player has collected all the flags. / stood on all flags
     * @return
     */
    public boolean hasWon() {
        return (flags.contains("flag1") && flags.contains("flag2") && flags.contains("flag3") && flags.contains("flag4"));
    }

    public void addFlag(String flag) {
        flags.add(flag);
    }


    public void moveBackward(int backWardDistance){
        for (int i = 0; i < backWardDistance; i++){
            this.moveBackwardOne();
        }
    }

    private void moveBackwardOne(){
        switch (this.direction) {
            case EAST:
                this.moveWest();
                break;
            case NORTH:
                this.moveSouth();
                break;
            case WEST:
                this.moveEast();
                break;
            case SOUTH:
                this.moveNorth();
                break;
            default:
                break;
        }

        if (!this.isTestPlayer) {
            GameScreen.getInstance().pickUpFlag(this);
        }

        if(!this.isTestPlayer && GameScreen.getInstance().isHole(this.xPos, this.yPos)){
            killRobot();
        }
    }

    public void moveForward(int forwardDistance){
        for (int i = 0; i < forwardDistance; i++){
            this.moveForwardOne();
        }
    }

    private void moveForwardOne(){
        switch (this.direction) {
            case WEST:
                this.moveWest();
                break;
            case SOUTH:
                this.moveSouth();
                break;
            case EAST:
                this.moveEast();
                break;
            case NORTH:
                this.moveNorth();
                break;
            default:
                break;
        }

        if (!this.isTestPlayer) {
            GameScreen.getInstance().pickUpFlag(this);
        }

        if(!this.isTestPlayer && GameScreen.getInstance().isHole(this.xPos, this.yPos)){
            killRobot();
        }
    }

    private void killRobot() {
        this.lives--;
        if(this.lives <= 0){
            System.out.println("Player is out of lives!!!");
            RoboRally.getInstance().setMenuScreen();
        } else {
            respawn();
        }
    }

    /**
     * respawn robot at respawn-point
     */
    private void respawn(){
        System.out.println("Respawning robot");
        this.direction = respawnDirection;
        this.xPos = respawnXPos;
        this.yPos = respawnYPos;
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

        System.out.println("Execute: " + progCard.getAction());

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

    public void setDirection(Direction dir){
        this.direction = dir;
    }

    public Direction getDirection() {
        return this.direction;
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

    public void loadAssets() {
        this.playerTexture = new TextureRegion(new Texture("Robo.png"));

    }

    public void drawNewDeck(){
        this.cardDeck.drawNineProgramCards();
    }

    /**
     * Add ProgramCard to player hand
     * @param card - ProgramCard to add to player hand
     */
    public void addCardToHand(ProgramCard card) {
        if (numCardsInHand < 5) {
            hand[numCardsInHand] = card;
            numCardsInHand += 1;
            card.setIsInHand(true);
        } else {
            System.out.println("Your hand is full!");
        }
    }

    /**
     * Execute all programcards in hand
     */
    public void executeCardsInHand(){
        GameScreen gameScreen = GameScreen.getInstance();
        for (ProgramCard currentCard : this.hand) {
            gameScreen.unrenderPlayer();
            performProgramCardAction(currentCard);
            gameScreen.renderPlayer();
        }

        clearHand();
    }

    public ProgramCard getProgramCard(int index){
        return this.cardDeck.getCard(index);
    }

    public TextureRegion getTexture() {
        return this.playerTexture;
    }

    private void clearHand() {
        this.hand = new ProgramCard[hand.length];
    }

    public ProgramCard[] getHand() {
        return this.hand;
    }

    public boolean isHandFull() {
        return numCardsInHand >= 5;
    }

    public void setXPos(int x) {
        this.xPos = x;
    }

    public void setYPos(int y) {
        this.yPos = y;
    }

    public int getYPos() { return yPos; }

    public int getXPos() { return xPos; }

    public void setToTestPlayer(){this.isTestPlayer = true;}
}
