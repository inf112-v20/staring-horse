package inf112.skeleton.app.robot;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.programcard.CardDeck;
import inf112.skeleton.app.programcard.ProgramCard;
import inf112.skeleton.app.screen.GameScreen;

public class Player implements IRobot {

    private GameScreen gameScreen;
    private Vector2 pos;
    private Direction direction;

    private Vector2 respawnPos;
    private Direction respawnDirection;

    private int healthPoints;
    private int fullHealthPoints;
    private int lives;
    private boolean isAlive;

    private ProgramCardAction previousAction;

    private int flag = 0;

    // bool set to true when using player for tests (solves error when
    // referencing TmxMapLoader in GameScreen while in tests)
    private boolean isTestRobot = false;

    private TextureRegion robotTexture;

    private CardDeck cardDeck;
    private ProgramCard[] hand;
    private int numberOfCardsInHand;

    private boolean cameFromConveyor;
    private boolean shouldNotMove;


    public Player() {
        this.respawnDirection = Direction.EAST;
        this.direction = respawnDirection;

        this.cardDeck = new CardDeck();
        this.hand = new ProgramCard[5];
        this.numberOfCardsInHand = 0;

        this.fullHealthPoints = 10;
        this.healthPoints = fullHealthPoints;
        this.lives = 3;
        this.cameFromConveyor = false;

        this.isAlive = true;

        this.gameScreen = GameScreen.getInstance();
    }


    @Override
    public boolean hasWon() {
        return this.flag == gameScreen.getGameLogic().getFlags().size();
    }

    @Override
    public void addFlag(String pickupFlag) {
        int pickupFlagNumber = Character.getNumericValue(pickupFlag.charAt(pickupFlag.length()-1));
        if( this.flag+1 == pickupFlagNumber){
            this.flag = pickupFlagNumber;
            System.out.println("Picked up flag" + this.flag);
        } else {
            if(this.flag+1 < pickupFlagNumber)
                System.out.println("Get flag" + (this.flag+1) + " first");
            else
                System.out.println("Player already has " + pickupFlagNumber + ". Get flag" + (this.flag+1));
        }

        if(this.hasWon()){
            gameScreen.playerWin();
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
        if(shouldNotMove)
            return;
        if (!this.isTestRobot && !(gameScreen.getGameLogic().canGo(pos, dir) && gameScreen.getGameLogic().pushIfPossible(pos,dir))) {
            System.out.println("Player can't go");
        }else{
            Vector2 nextPos = Direction.getPosInDirection(this.pos, dir);
            setPos(nextPos);
        }

        if(!this.isTestRobot && gameScreen.getGameLogic().isHole(this.pos)){
            killRobot();
        }
    }

    @Override
    public void killRobot() {
        this.lives--;
        if(this.lives <= 0){
            System.out.println("PLAYER IS OUT OF LIVES!");
            isAlive = false;
            gameScreen.playerLose();
        } else {
            System.out.println(lives + " lives left");
            this.healthPoints = fullHealthPoints;
            respawn();
        }
        this.shouldNotMove = true;
    }

    @Override
    public void respawn(){
        gameScreen.unrenderRobot(this);
        System.out.println("Respawning player");
        if (gameScreen.getGameLogic().posIsOccupied(respawnPos)) {
            gameScreen.getGameLogic().pushOccupyingRobot(respawnPos);
        }
        this.direction = respawnDirection;
        this.pos = respawnPos;
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
        } else {
            System.out.println("Your hand is full!");
        }
    }

    @Override
    public void executeCardInHand(int phase){
        gameScreen.unrenderRobot(this);
        performProgramCardAction(this.hand[phase]);
        gameScreen.renderRobot(this);

        if(phase == 4) clearHand();

    }

    @Override
    public void heal() {
        if (healthPoints < fullHealthPoints)
            this.healthPoints++;
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
    public void clearHand() {
        if(this.getDamageTaken() > 4){
            this.numberOfCardsInHand = this.getDamageTaken()-4;
        }else{
            this.numberOfCardsInHand = 0;
        }
    }

    @Override
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
    public void setPos(Vector2 pos){
        this.pos = pos;
    }

    @Override
    public Vector2 getPos() { return pos; }

    @Override
    public int getXPos() {
        return (int) pos.x;
    }

    @Override
    public int getYPos() {
        return (int) pos.y;
    }

    @Override
    public void setXPos(int x) {
        this.setPos(new Vector2(x, this.getYPos()));
    }

    @Override
    public void setYPos(int y) {
        this.setPos(new Vector2(this.getXPos(), y));
    }

    @Override
    public void takeDamage() {
        this.healthPoints--;
        if (this.healthPoints <= 0)
            killRobot();
    }

    @Override
    public void setRespawnPoint(Vector2 pos) {
        this.respawnPos = pos;
        setPos(respawnPos);
    }

    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public void setToTestRobot(){this.isTestRobot = true;}

    @Override
    public boolean getCameFromConveyor() {
        return cameFromConveyor;
    }

    @Override
    public void setCameFromConveyor(boolean bool) {
        cameFromConveyor = bool;
    }

    @Override
    public String getName() {
        return "PLAYER";
    }

    public boolean getIsTestPlayer() {
        return this.isTestRobot;
    }

    public int getDamageTaken(){return (this.fullHealthPoints-this.healthPoints);}

    @Override
    public int getHealthPoints() {
        return this.healthPoints;
    }

    @Override
    public int getLives() {
        return this.lives;
    }

    @Override
    public int getFlagsTaken() {
        return this.flag;
    }

    @Override
    public boolean getShouldNotMove() {
        return this.shouldNotMove;
    }

    @Override
    public void setShouldNotMove(boolean bool) {
        this.shouldNotMove = bool;
    }
}
