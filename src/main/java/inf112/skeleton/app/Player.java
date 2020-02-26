package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

    private int xPos;
    private int yPos;
    private Direction direction;
    private int healthPoints;
    private ProgramCardAction previousAction;

    private Texture playerTextures;
    private TextureRegion aliveTexture;
    private TextureRegion deadTexture;
    private TextureRegion wonTexture;
    private TextureRegion[][] textureRegion;

    public Player() {
        this.xPos = 10;
        this.yPos = 15;
        this.direction = Direction.NORTH;
    }

    public void setXPos(int x) { this.xPos = x; }

    public void setYPos(int y) { this.yPos = y; }

    public int getYPos() { return yPos; }

    public int getXPos() { return xPos; }

    public void setDirection(Direction dir){
        this.direction = dir;
    }

    public Direction getDirection() {
        return this.direction;
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
        }
    }

    public void performProgramCardAction(ProgramCard progCard){
        switch (progCard.getAction()){
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
            case U_TURN:
                this.rotateClockwise();
                this.rotateClockwise();
                break;
            }
    }

    public TextureRegion getAliveTexture() {
        return this.aliveTexture;
    }

    public TextureRegion getDeadTexture() {
        return this.deadTexture;
    }

    public TextureRegion getWonTexture() {
        return this.wonTexture;
    }

    public void loadAssets() {
        this.playerTextures = new Texture("player.png");
        this.textureRegion = new TextureRegion(playerTextures).split(300,300);
        this.aliveTexture = new TextureRegion();
        this.deadTexture = new TextureRegion();
        this.wonTexture = new TextureRegion();

        aliveTexture = textureRegion[0][0];
        deadTexture = textureRegion[0][1];
        wonTexture = textureRegion[0][2];
    }
}
