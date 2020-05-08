package inf112.skeleton.app.robot;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.programcard.ProgramCard;

public interface IRobot {
    /**
     * @return true if robot has won game
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
     * Respawn robot at respawn-point
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
     * Execute ProgramCardAction from programCard on robot
     * @param programCard - ProgramCard
     */
    void performProgramCardAction(ProgramCard programCard);

    /**
     * Set robot direction
     * @param dir Direction
     */
    void setDirection(Direction dir);

    /**
     * The direction the robot is facing
     * @return Direction
     */
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

    /**
     * Set robot position
     * @param pos Vector2 position
     */
    void setPos(Vector2 pos);

    /**
     * Get robot position
     * @return Vector2
     */
    Vector2 getPos();

    /**
     * Get robot x position
     * @return int
     */
    int getXPos();

    /**
     * Get robot y position
     * @return int
     */
    int getYPos();

    /**
     * Set robot x position
     * @param x int
     */
    void setXPos(int x);

    /**
     * Set robot y position
     * @param y int
     */
    void setYPos(int y);

    /**
     * Set respawn-point
     * @param pos Vector2
     */
    void setRespawnPoint(Vector2 pos);

    /**
     * @return true if robot is out of lives and false otherwise
     */
    boolean isAlive();

    /**
     * Set to test player
     */
    void setToTestRobot();

    /**
     * @return true if a robot last moved from a conveyor
     */
    boolean getCameFromConveyor();

    /**
     * Sets the cameFromConveyor.
     * @param bool
     */
    void setCameFromConveyor(boolean bool);

    /**
     * Get robot name
     * @return String
     */
    String getName();

    /**
     * Return amount of health points
     * @return int number of health points
     */
    int getHealthPoints();

    /**
     * Get amount of lives
     * @return int number of lives
     */
    int getLives();

    /**
     * Get amount of flags taken
     * @return int number of flags
     */
    int getFlagsTaken();

    /**
     * Get if player should not be able to move
     * @return boolean true if not able to move
     */
    boolean getShouldNotMove();

    /**
     * Set to true if player should not be able to move, false otherwise
     * @param bool
     */
    void setShouldNotMove(boolean bool);

    /**
     * Return list of ProgramCards chosen
     * @return ProgramCard list
     */
    ProgramCard[] getHand();

    /**
     * Execute card in hand corresponding to phase
     * @param phase current card to activate
     */
    void executeCardInHand(int phase);

    /**
     * Heals a player by one, unless the robot has max health.
     */
    void heal();
}
