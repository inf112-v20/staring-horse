package inf112.skeleton.app;

import com.badlogic.gdx.graphics.Color;

public interface IElement {
    /*
    * Perform individual board-elements action (e.g. hole, conveyor belt, etc.)
    * Some elements should only activate when robot is at same position
    */
    void performAction();


    // Will not be necessary when getting actual 2D sprites
    /*
    * Get render-Color
    */
    Color getTileColor();
}
