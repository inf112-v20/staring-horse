package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player {

    private int xPos;
    private int yPos;
    private Direction direction;
    private int healthPoints;

    private Texture playerTextures;
    private TextureRegion aliveTexture;
    private TextureRegion deadTexture;
    private TextureRegion wonTexture;
    private TextureRegion[][] textureRegion;

    public Player() {
        this.xPos = 0;
        this.yPos = 0;
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

    public void moveForward(){
        if(this.direction == Direction.NORTH){
            this.moveNorth();
        } else if(this.direction == Direction.EAST){
            this.moveEast();
        } else if(this.direction == Direction.SOUTH){
            this.moveSouth();
        } else if(this.direction == Direction.WEST){
            this.moveWest();
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
        if(this.direction == Direction.NORTH){
            this.direction = Direction.EAST;
        } else if(this.direction == Direction.EAST){
            this.direction = Direction.SOUTH;
        } else if(this.direction == Direction.SOUTH){
            this.direction = Direction.WEST;
        } else if(this.direction == Direction.WEST){
            this.direction = Direction.NORTH;
        }
    }

    public void rotateCounterClockwise(){
        if(this.direction == Direction.NORTH){
            this.direction = Direction.WEST;
        } else if(this.direction == Direction.EAST){
            this.direction = Direction.NORTH;
        } else if(this.direction == Direction.SOUTH){
            this.direction = Direction.EAST;
        } else if(this.direction == Direction.WEST){
            this.direction = Direction.SOUTH;
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
