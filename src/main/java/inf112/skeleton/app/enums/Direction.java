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
                return EAST;
            case SOUTH:
                return NORTH;
            case EAST:
                return WEST;
            case NORTH:
                return SOUTH;
        }

        return dir;
    }

    /**
     * Return position in direction of original position
     * @param pos - original position
     * @param dir - direction to go
     * @return Vector2 position
     */
    public static Vector2 getPosInDirection(Vector2 pos, Direction dir){
        // copy of pos so that it does not change the original position's value
        Vector2 posCopy = pos.cpy();
        switch (dir) {
            case WEST:
                return posCopy.sub(1,0);
            case SOUTH:
                return posCopy.sub(0,1);
            case EAST:
                return posCopy.add(1,0);
            case NORTH:
                return posCopy.add(0,1);
        }

        return pos;
    }
}
