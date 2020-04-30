package inf112.skeleton.app.robot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.gameLogic.GameLogic;
import inf112.skeleton.app.programCard.ProgramCard;
import inf112.skeleton.app.screen.GameScreen;

public class AI implements IRobot {

    private int id;
    private int xPos;
    private int yPos;
    private Direction direction;

    private int respawnXPos;
    private int respawnYPos;
    private Direction respawnDirection;

    private int healthPoints;
    private int lives;

    private ProgramCardAction previousAction;
    // bool set to true when using Robot for tests (solves error when
    // referencing TmxMapLoader in GameScreen while in tests)
    private boolean isTestRobot = false;

    private TextureRegion robotTexture;

    private ProgramCard[] hand;

    // amount of AI-robots made used for giving robots unique ID/name
    private static int aiNumber;
    private boolean isDead;
    private int flag;
    private boolean cameFromConveyor;


    public AI(){
        this.respawnDirection = Direction.NORTH;
        this.direction = respawnDirection;

        this.healthPoints = 10;
        this.lives = 3;
        this.cameFromConveyor = false;

        this.id = ++aiNumber;

        this.hand = new ProgramCard[5];
    }

    /**
     * Execute one random ProgramCard
     */
    public void executeRandomProgramCardAction(){
        GameScreen.getInstance().unrenderRobot(this);
        this.performProgramCardAction(new ProgramCard());
        GameScreen.getInstance().renderRobot(this);
    }

    /**
     * Generates slightly smart moves for AI
     */
    public void generateSmartMoves(){
        for(int i = 0; i < hand.length; i++){
            Vector2 nextFlagPos = GameLogic.getInstance().getFlagPosHashMap().get("flag" + (this.flag+1));

            ProgramCard randomProgramCard;
            Vector2 nextPos;
            do {
                randomProgramCard = new ProgramCard();
                nextPos = ProgramCardAction.getPositionAfterProgramCardAction(
                        new Vector2(this.getXPos(),this.getYPos()),
                        this.getDirection(),
                        randomProgramCard.getAction());
            } while(GameLogic.getInstance().isHole((int)nextPos.x,(int)nextPos.y) ||
                    getDistanceBetweenPositions(new Vector2(this.getXPos(),this.getYPos()), nextFlagPos) <
                    getDistanceBetweenPositions(nextPos, nextFlagPos));

            hand[i] = randomProgramCard;
        }
    }

    /**
     * Get distance between 2 positions
     * @param pos1 Vector2 position
     * @param pos2 Vector2 position
     * @return int distance between positions
     */
    public int getDistanceBetweenPositions(Vector2 pos1, Vector2 pos2){
        return (int) (Math.abs(pos1.x - pos2.x) + Math.abs(pos1.y - pos2.y));
    }

    /**
     * Excecute card in hand corresponding to phase
     * @param phase current card to activate
     */
    public void executeCardInHand(int phase){
        GameScreen.getInstance().unrenderRobot(this);
        this.performProgramCardAction(hand[phase]);
        GameScreen.getInstance().renderRobot(this);
    }

    public static void resetRobotID(){
        aiNumber = 1;
    }

    @Override
    public boolean hasWon() {
        return this.flag == GameLogic.getInstance().getFlags().size();
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
                System.out.println("Robot already has " + pickupFlagNumber + ". Get flag" + (this.flag+1));
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
            System.out.println("Robot" + this.id + " can't go");
        }else{
            if(!this.isTestRobot)
                GameLogic.getInstance().pushIfPossible(getXPos(),getYPos(), dir);
            Vector2 nextPos = Direction.getPosInDirection(new Vector2(getXPos(),getYPos()), dir);
            setXPos((int)nextPos.x);
            setYPos((int)nextPos.y);
        }

        if(!this.isTestRobot && GameLogic.getInstance().isHole(this.xPos, this.yPos)){
            killRobot();
        }

        //if(!this.isTestRobot)
        //  GameLogic.getInstance().conveyorBelt(this);
    }

    @Override
    public void killRobot() {
        this.lives--;
        if(this.lives <= 0){
            if(!isDead)
                System.out.println("Robot" + this.id + " IS OUT OF LIVES!");
            isDead = true;
        } else {
            System.out.println("Robot" + this.id + ": " + lives + " lives left");
            respawn();
        }
    }

    @Override
    public void respawn(){
        System.out.println("Respawning robot...");
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

        //System.out.println("Execute: " + programCard.getAction());

        switch (action) {
            case MOVE_ONE:
                this.move(1, this.direction);
                break;
            case MOVE_TWO:
                this.move(2, this.direction);
                break;
            case MOVE_THREE:
                this.move(3, this.direction);
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
        this.robotTexture = new TextureRegion(new Texture("Robot" + this.id + ".png"));
    }

    @Override
    public TextureRegion getTexture() {
        if (robotTexture == null) {
            loadAssets();
        }
        return this.robotTexture;
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

    @Override
    public boolean getCameFromConveyor() {
        return cameFromConveyor;
    }

    @Override
    public void setCameFromConveyor(boolean bool) {
        cameFromConveyor = bool;
    }
}
