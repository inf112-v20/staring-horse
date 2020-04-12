package inf112.skeleton.app.robot;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.enums.ProgramCardAction;
import inf112.skeleton.app.gameLogic.GameLogic;
import inf112.skeleton.app.programCard.ProgramCard;
import inf112.skeleton.app.screen.GameScreen;

import java.util.ArrayList;

public class AI implements IRobot {

    private int xPos;
    private int yPos;
    private Direction direction;

    private int respawnXPos;
    private int respawnYPos;
    private Direction respawnDirection;

    private int healthPoints;
    private int lives;

    private ProgramCardAction previousAction;

    private ArrayList<String> flags;

    // bool set to true when using Robot for tests (solves error when
    // referencing TmxMapLoader in GameScreen while in tests)
    private boolean isTestRobot = false;

    private TextureRegion robotTexture;

    private ArrayList<Integer> respawnPoints;

    public AI(){
        this.respawnPoints = GameLogic.getInstance().getXandYposofRespawnPoint();
        this.respawnXPos = respawnPoints.get(2);
        this.respawnYPos = respawnPoints.get(3);
        this.respawnDirection = Direction.NORTH;

        this.xPos = respawnXPos;
        this.yPos = respawnYPos;
        this.direction = respawnDirection;

        this.healthPoints = 10;
        this.lives = 3;

        this.flags = new ArrayList<>();

        this.loadAssets();
    }

    public void executeRandomProgramCardAction(){
        GameScreen.getInstance().unrenderRobot(this);
        this.performProgramCardAction(new ProgramCard());
        GameScreen.getInstance().renderRobot(this);
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
    public void moveBackward(int backWardDistance){
        for (int i = 0; i < backWardDistance; i++){
            this.moveBackwardOne();
        }
    }

    @Override
    public void moveBackwardOne(){
        switch (this.direction) {
            case EAST:
                if (!this.isTestRobot && !GameLogic.getInstance().canGo(this, true)) {
                    System.out.println("Robot can't go WEST");
                    break;
                }
                this.moveWest();
                break;
            case NORTH:
                if (!this.isTestRobot && !GameLogic.getInstance().canGo(this, true)) {
                    System.out.println("Robot can't go SOUTH");
                    break;
                }
                this.moveSouth();
                break;
            case WEST:
                if (!this.isTestRobot && !GameLogic.getInstance().canGo(this, true)) {
                    System.out.println("Robot can't go EAST");
                    break;
                }
                this.moveEast();
                break;
            case SOUTH:
                if (!this.isTestRobot && !GameLogic.getInstance().canGo(this, true)) {
                    System.out.println("Robot can't go NORTH");
                    break;
                }
                this.moveNorth();
                break;
            default:
                break;
        }

        if(!this.isTestRobot && GameLogic.getInstance().isHole(this.xPos, this.yPos)){
            killRobot();
        }
    }

    @Override
    public void moveForward(int forwardDistance){
        for (int i = 0; i < forwardDistance; i++){
            this.moveForwardOne();
        }
    }

    @Override
    public void moveForwardOne(){
        switch (this.direction) {
            case WEST:
                if (!this.isTestRobot && !GameLogic.getInstance().canGo(this, false)) {
                    System.out.println("Robot can't go WEST");
                    break;
                }
                this.moveWest();
                break;
            case SOUTH:
                if (!this.isTestRobot && !GameLogic.getInstance().canGo(this, false)) {
                    System.out.println("Robot can't go SOUTH");
                    break;
                }
                this.moveSouth();
                break;
            case EAST:
                if (!this.isTestRobot && !GameLogic.getInstance().canGo(this, false)) {
                    System.out.println("Robot can't go EAST");
                    break;
                }
                this.moveEast();
                break;
            case NORTH:
                if (!this.isTestRobot && !GameLogic.getInstance().canGo(this, false)) {
                    System.out.println("Robot can't go NORTH");
                    break;
                }
                this.moveNorth();
                break;
        }

        if(!this.isTestRobot && GameLogic.getInstance().isHole(this.xPos, this.yPos)){
            killRobot();
        }
    }

    @Override
    public void killRobot() {
        this.lives--;
        if(this.lives <= 0){
            System.out.println("ROBOT IS OUT OF LIVES!");
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

        // TODO move this into GameLoop and do after all the robots has moved.
        if (!this.isTestRobot) {
            GameLogic.getInstance().pickUpFlag(this);
            GameLogic.getInstance().changeDirOnGear(this);
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
    public void moveNorth(){
        this.setYPos(this.getYPos()+1);
    }

    @Override
    public void moveEast(){
        this.setXPos(this.getXPos()+1);
    }

    @Override
    public void moveSouth(){
        this.setYPos(this.getYPos()-1);
    }

    @Override
    public void moveWest(){
        this.setXPos(this.getXPos()-1);
    }

    @Override
    public void loadAssets() {
        this.robotTexture = new TextureRegion(new Texture("Robo.png"));
    }

    @Override
    public TextureRegion getTexture() {
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

    public void setToTestRobot(){this.isTestRobot = true;}
}
