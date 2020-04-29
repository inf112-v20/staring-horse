package inf112.skeleton.app.robot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.RoboRally;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.gameLogic.GameLogic;
import inf112.skeleton.app.programCard.CardDeck;
import inf112.skeleton.app.programCard.ProgramCard;
import inf112.skeleton.app.screen.GameScreen;

import java.util.ArrayList;

public class Player implements IRobot {

    private int xPos;
    private int yPos;
    private Direction direction;

    private int respawnXPos;
    private int respawnYPos;
    private Direction respawnDirection;

    private int healthPoints;
    private int lives;
    private boolean isDead;

    private ProgramCardAction previousAction;

    private ArrayList<String> flags;

    // bool set to true when using player for tests (solves error when
    // referencing TmxMapLoader in GameScreen while in tests)
    private boolean isTestRobot = false;

    private TextureRegion robotTexture;

    private CardDeck cardDeck;
    private ProgramCard[] hand;
    private int numberOfCardsInHand;


    public Player() {
        this.respawnDirection = Direction.NORTH;
        this.direction = respawnDirection;

        this.cardDeck = new CardDeck();
        this.hand = new ProgramCard[5];
        this.numberOfCardsInHand = 0;

        this.healthPoints = 10;
        this.lives = 3;

        this.flags = new ArrayList<>();

    }


    @Override
    public boolean hasWon() {
        return (flags.contains("flag1") && flags.contains("flag2") && flags.contains("flag3") && flags.contains("flag4"));
    }

    @Override
    public void addFlag(String flag) {
        if (flags.contains(flag)) {
            System.out.println("You already have this flag.");
        }

        if (flag.equals("flag1") && flags.size() == 0) {
            flags.add(flag);
            System.out.println("Picked up flag 1");
        } else if (flags.size() == 0) {
            System.out.println("Take Flag 1 first!");
        } else if (flag.equals("flag2") && flags.get(0).equals("flag1") && flags.size() == 1) {
            flags.add(flag);
            System.out.println("Picked up flag 2");
        } else if (flags.size() == 1) {
            System.out.println("Take Flag 2 first!");
        } else if (flag.equals("flag3") && flags.get(1).equals("flag2") && flags.size() == 2) {
            flags.add(flag);
            System.out.println("Picked up flag 3");
        } else if (flags.size() == 2) {
            System.out.println("Take Flag 3 first!");
        } else if (flag.equals("flag4") && flags.get(2).equals("flag3") && flags.size() == 3) {
            flags.add(flag);
            System.out.println("Picked up flag 4");
        }
    }

    @Override
    public void move(int distance, Direction dir){
        for (int i = 0; i < distance; i++){
            this.moveOne(dir);
        }
    }

    @Override
    public void moveOne(Direction dir){
        if (!this.isTestRobot && !GameLogic.getInstance().canGo(new Vector2(getXPos(),getYPos()), dir)) {
            System.out.println("Player can't go");
        }else{
            if(!this.isTestRobot) {
                GameLogic.getInstance().pushIfPossible(getXPos(), getYPos(), dir);
            }
            Vector2 nextPos = Direction.getPosInDirection(new Vector2(getXPos(),getYPos()), dir);
            setXPos((int)nextPos.x);
            setYPos((int)nextPos.y);
        }

        if(!this.isTestRobot && GameLogic.getInstance().isHole(this.xPos, this.yPos)){
            killRobot();
        }

        //if(!this.isTestPlayer)
          //  GameLogic.getInstance().conveyorBelt(this);
    }

    @Override
    public void killRobot() {
        this.lives--;
        if(this.lives <= 0){
            System.out.println("PLAYER IS OUT OF LIVES!");
            isDead = true;
            RoboRally.getInstance().setMenuScreen();
        } else {
            System.out.println(lives + " lives left");
            respawn();
        }
    }

    @Override
    public void respawn(){
        System.out.println("Respawning robot");
        this.direction = respawnDirection;
        this.xPos = respawnXPos;
        this.yPos = respawnYPos;
    }

    @Override
    public void rotateClockwise(){
        this.direction = Direction.rotateClockwise(direction);
    }

    @Override
    public void rotateCounterClockwise(){
        this.direction = Direction.rotateCounterClockwise(direction);
    }

    @Override
    public void performProgramCardAction(ProgramCard programCard) {
        ProgramCardAction action = programCard.getAction();

        System.out.println("Execute: " + programCard.getAction());

        switch (action) {
            case MOVE_ONE:
                this.move(1, this.direction);
                break;
            case MOVE_TWO:
                this.move(2,this.direction);
                break;
            case MOVE_THREE:
                this.move(3,this.direction);
                break;
            case TURN_LEFT:
                this.rotateCounterClockwise();
                break;
            case TURN_RIGHT:
                this.rotateClockwise();
                break;
            case BACK_UP:
                this.move(1, Direction.oppositeOf(this.direction));
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

        /*
        // TODO move this into GameLoop and do after all the robots has moved.
        if (!this.isTestPlayer) {
            GameLogic.getInstance().pickUpFlag(this);
            GameLogic.getInstance().changeDirOnGear(this);
        }
        */

    }

    @Override
    public void setDirection(Direction dir){
        this.direction = dir;
    }

    @Override
    public Direction getDirection() {
        return this.direction;
    }

    @Override
    public void loadAssets() {
        this.robotTexture = new TextureRegion(new Texture("Robot1.png"));
    }

    /**
     * Draw a new deck of programcards that the player can select from
     */
    public void drawNewDeck(){
        this.cardDeck.drawNineProgramCards();
    }

    /**
     * Add ProgramCard to player hand
     * @param card - ProgramCard to add to player hand
     */
    public void addCardToHand(ProgramCard card) {
        if (numberOfCardsInHand < 5) {
            hand[numberOfCardsInHand] = card;
            numberOfCardsInHand += 1;
            card.setIsInHand(true);
        } else {
            System.out.println("Your hand is full!");
        }
    }

    /**
     * Execute all programcards in hand
     */
    public void executeCardInHand(int phase){
        GameScreen gameScreen = GameScreen.getInstance();

        gameScreen.unrenderRobot(this);
        performProgramCardAction(this.hand[phase]);
        gameScreen.renderRobot(this);

        if(phase == 4) clearHand();
    }

    public ProgramCard getProgramCard(int index){
        return this.cardDeck.getCard(index);
    }

    @Override
    public TextureRegion getTexture() {
        if (robotTexture == null) {
            loadAssets();
        }
        return this.robotTexture;
    }

    /**
     * Remove all cards from players hand
     */
    private void clearHand() {
        this.hand = new ProgramCard[hand.length];
        this.numberOfCardsInHand = 0;
    }

    public ProgramCard[] getHand() {
        return this.hand;
    }

    public int getNumberOfCardsInHand(){
        return this.numberOfCardsInHand;
    }

    public boolean isHandFull() {
        return numberOfCardsInHand >= 5;
    }

    @Override
    public void setXPos(int x) {
        this.xPos = x;
    }

    @Override
    public void setYPos(int y) {
        this.yPos = y;
    }

    @Override
    public int getYPos() { return yPos; }

    @Override
    public int getXPos() { return xPos; }

    @Override
    public void takeDamage() {
        this.healthPoints--;
    }

    @Override
    public void setRespawnPoint(int x, int y) {
        respawnXPos = x;
        respawnYPos = y;
        setXPos(respawnXPos);
        setYPos(respawnYPos);
    }

    @Override
    public boolean isDead() {
        return isDead;
    }

    @Override
    public void setToTestRobot(){this.isTestRobot = true;}

    public boolean getIsTestPlayer() {
        return this.isTestRobot;
    }
}
