package inf112.skeleton.app.enums;

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
}
