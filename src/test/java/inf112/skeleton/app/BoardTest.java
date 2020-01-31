package inf112.skeleton.app;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

    int testWidth;
    int testHeight;
    Board testBoard;

    @Before
    public void initTestBoard(){
        this.testWidth = 10;
        this.testHeight = 24;
        this.testBoard = new Board(testWidth, testHeight);
    }

    @Test
    public void testIfBoardIsCorrectWidth(){
        assertEquals(this.testWidth, this.testBoard.getWidth());
    }

    @Test
    public void testIfBoardIsCorrectHeight(){
        assertEquals(this.testHeight, this.testBoard.getHeight());
    }
}
