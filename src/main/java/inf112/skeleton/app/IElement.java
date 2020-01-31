package inf112.skeleton.app;

public interface IElement {
    /*
    * Perform individual board-elements action (e.g. hole, conveyor belt, etc.)
    * Some elements should only activate when robot is at same position
    */
    void performAction();
}
