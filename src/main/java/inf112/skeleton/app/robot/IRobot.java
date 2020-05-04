package inf112.skeleton.app.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.programCard.ProgramCard;

public interface IRobot {
    /**
     * @return if robot has won game
     */
    boolean hasWon();

    /**
     * Add collected flag to robot if it does not already have it
     * @param flag - string with flag number
     */
    void addFlag(String flag);

    /**
     * Move robot if not hindered
     * @param distance - distance robot should move
     * @param dir - direction of movement
     */
    void move(int distance, Direction dir);

    /**
     * Move robot one tile if not hindered
     * @param dir - direction of movement
     */
    void moveOne(Direction dir);

    /**
     * Decrease robot health-points
     */
    void takeDamage();

    /**
     * Kill robot and respawn if robot is not out of lives
     */
    void killRobot();

    /**
     * respawn robot at respawn-point
     */
    void respawn();

    /**
     * Rotate robot clockwise
     */
    void rotateClockwise();
    /**
     * Rotate robot counterclockwise
     */
    void rotateCounterClockwise();

    /**
     * Execute programcard-action from programCard on robot
     * @param programCard - ProgramCard
     */
    void performProgramCardAction(ProgramCard programCard);

    void setDirection(Direction dir);

    Direction getDirection();

    /**
     * Load robot texture
     */
    void loadAssets();

    /**
     * Get unique texture for robot and load assets if not already loaded
     * @return texture
     */
    TextureRegion getTexture();

    void setPos(Vector2 pos);

    Vector2 getPos();

    int getXPos();

    int getYPos();

    void setXPos(int x);

    void setYPos(int y);

    void setRespawnPoint(Vector2 pos);

    /**
     * @return true if robot is out of lives and false otherwise
     */
    boolean isAlive();

    void setToTestRobot();

    /**
     * @return true if a robot last moved from a conveyor
     */
    boolean getCameFromConveyor();

    /**
     * sets the cameFromConveyor.
     */
    void setCameFromConveyor(boolean bool);

    String getName();

    int getHealthPoints();

    int getLives();

    int getFlagsTaken();

    boolean getShouldNotMove();

    void setShouldNotMove(boolean bool);
}
