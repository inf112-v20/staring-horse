package inf112.skeleton.app;

public class Robot {

    private int x;
    private int y;
    private Direction dir;

    public Robot(){
        // initializes robots position
        this.x = 0;
        this.y = 0;
        this.dir = Direction.NORTH;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public Direction getDirection(){
        return this.dir;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDirection(Direction d){
        this.dir = d;
    }

    public void moveTo(int x, int y){
        this.setX(x);
        this.setY(y);
    }
}
