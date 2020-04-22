package inf112.skeleton.app.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.programCard.ProgramCard;

public interface IRobot {
    boolean hasWon();

    void addFlag(String flag);

    void move(int distance, Direction dir);

    void moveOne(Direction dir);

    void killRobot();

    /**
     * respawn robot at respawn-point
     */
    void respawn();

    void rotateClockwise();

    void rotateCounterClockwise();

    void performProgramCardAction(ProgramCard programCard);

    void setDirection(Direction dir);

    Direction getDirection();

    void moveNorth();

    void moveEast();

    void moveSouth();

    void moveWest();

    void loadAssets();

    TextureRegion getTexture();

    void setXPos(int x);

    void setYPos(int y);

    int getYPos();

    int getXPos();

    void setRespawnPoint(int x, int y);

    boolean isDead();
}
