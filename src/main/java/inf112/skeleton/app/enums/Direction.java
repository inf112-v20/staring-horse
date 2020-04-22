package inf112.skeleton.app.enums;

import com.badlogic.gdx.math.Vector2;

public enum Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    /**
     * Get direction when input-direction is rotated clockwise
     * @param dir - direction
     */
    public static Direction rotateClockwise(Direction dir){
        switch (dir) {
            case WEST:
                return Direction.NORTH;
            case SOUTH:
                return Direction.WEST;
            case EAST:
                return Direction.SOUTH;
            case NORTH:
                return Direction.EAST;
        }

        return dir;
    }

    /**
     * Get direction when input-direction is rotated counterclockwise
     * @param dir - direction
     */
    public static Direction rotateCounterClockwise(Direction dir){
        switch (dir) {
            case WEST:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.EAST;
            case EAST:
                return Direction.NORTH;
            case NORTH:
                return Direction.WEST;
        }

        return dir;
    }

    /**
     * Get opposite direction of dir
     * @param dir - direction
     */
    public static Direction oppositeOf(Direction dir){
        switch (dir) {
            case WEST:
                return Direction.EAST;
            case SOUTH:
                return Direction.NORTH;
            case EAST:
                return Direction.WEST;
            case NORTH:
                return Direction.SOUTH;
        }

        return dir;
    }

    public static Vector2 getPosInDirection(Vector2 pos, Direction dir){
        switch (dir) {
            case WEST:
                return pos.sub(1,0);
            case SOUTH:
                return pos.sub(0,1);
            case EAST:
                return pos.add(1,0);
            case NORTH:
                return pos.add(0,1);
        }

        return null;
    }
}
