package inf112.skeleton.app;

public class Board {

    private int height;
    private int width;

    // 2D-array that represents the board-tiles
    // null-value represents empty floor
    private IElement[][] boardGrid;

    public Board (int width, int height){
        this.height = height;
        this.width = width;
        this.boardGrid = new IElement[height][width];

        this.boardGrid[5][5] = new Flag();
        this.boardGrid[2][4] = new Hole();
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }
    /*
    * return true if value at pos(x,y) is empty floor
    */
    public boolean isFloor(int x, int y){
        // necessary to avoid nullPointerException when trying to use
        // methods from IElement returned from elementAt()
        return boardGrid[y][x] == null;
    }

    public IElement elementAt(int x, int y){
        return boardGrid[y][x];
    }
}
