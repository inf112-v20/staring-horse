package inf112.skeleton.app.robot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.programCard.ProgramCard;
import inf112.skeleton.app.screen.GameScreen;

public class AI implements IRobot {

    private int id;
    private Vector2 pos;
    private Direction direction;

    private Vector2 respawnPos;
    private Direction respawnDirection;

    private int fullHealthPoints;
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
    private boolean isAlive;
    private int flag;
    private boolean cameFromConveyor;
    private GameScreen gameScreen;
    private boolean shouldNotMove;


    public AI(){
        this.respawnDirection = Direction.EAST;
        this.direction = respawnDirection;

        this.fullHealthPoints = 10;
        this.healthPoints = fullHealthPoints;
        this.lives = 3;
        this.cameFromConveyor = false;

        this.id = ++aiNumber;

        this.hand = new ProgramCard[5];

        this.isAlive = true;

        this.gameScreen = GameScreen.getInstance();
    }

    /**
     * Execute one random ProgramCard
     */
    public void executeRandomProgramCardAction(){
        gameScreen.unrenderRobot(this);
        this.performProgramCardAction(new ProgramCard());
        gameScreen.renderRobot(this);
    }

    /**
     * Generates slightly smart moves for AI
     */
    public void generateSmartMoves(){
        for(int i = 0; i < hand.length; i++){
            Vector2 nextFlagPos = gameScreen.getGameLogic().getFlagPosHashMap().get("flag" + (this.flag+1));

            if (nextFlagPos == null) {
                hand[i] = new ProgramCard();
                continue;
            }

            ProgramCard randomProgramCard;
            Vector2 nextPos;
            do {
                randomProgramCard = new ProgramCard();
                nextPos = ProgramCardAction.getPositionAfterProgramCardAction(this.pos, this.getDirection(),
                        randomProgramCard.getAction());

            } while(gameScreen.getGameLogic().isHole(nextPos) ||
                    getDistanceBetweenPositions(this.pos, nextFlagPos) <
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
        gameScreen.unrenderRobot(this);
        this.performProgramCardAction(hand[phase]);
        gameScreen.renderRobot(this);
    }

    public static void resetRobotID(){
        aiNumber = 1;
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
            System.out.println(this.getName() + " picked up flag" + this.flag);
        }

        if(this.hasWon()){
            gameScreen.robotWin(this);
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
        if (!this.isTestRobot && !gameScreen.getGameLogic().canGo(pos, dir)) {
            System.out.println(this.getName() + " can't go");
        }else{
            if(!this.isTestRobot) {
                gameScreen.getGameLogic().pushIfPossible(this.pos, dir);
            }
            Vector2 nextPos = Direction.getPosInDirection(this.pos, dir);
            setPos(nextPos);
        }

        if(!this.isTestRobot && gameScreen.getGameLogic().isHole(this.pos)){
            killRobot();
        }

        //if(!this.isTestRobot)
        //  GameLogic.getInstance().conveyorBelt(this);
    }

    @Override
    public void killRobot() {
        this.lives--;
        if(this.lives <= 0){
            if(isAlive)
                System.out.println(this.getName() + " IS OUT OF LIVES!");
            isAlive = false;
            gameScreen.onlyOneRobotLeftCheck();
        } else {
            System.out.println(this.getName() + ": " + lives + " lives left");
            this.healthPoints = fullHealthPoints;
            respawn();
        }
        this.shouldNotMove = true;
    }

    @Override
    public void respawn(){
        gameScreen.unrenderRobot(this);
        System.out.println("Respawning robot");
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
        this.robotTexture = new TextureRegion(new Texture("Robot" + (((this.id + 1) % 3) + 2)  + ".png"));
    }

    @Override
    public TextureRegion getTexture() {
        if (robotTexture == null) {
            loadAssets();
        }
        return this.robotTexture;
    }

    @Override
    public void setPos(Vector2 pos) {
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
        return isAlive;
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
        return "ROBOT" + this.id;
    }

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
        return shouldNotMove;
    }

    @Override
    public void setShouldNotMove(boolean bool) {
        this.shouldNotMove = bool;
    }
}
