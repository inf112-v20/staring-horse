package inf112.skeleton.app;

import java.util.Stack;

public class CardStack {
    private Stack cardStack;

    public CardStack(){
        this.cardStack = new Stack();
        for(int i = 0; i < 10; i++){
            this.cardStack.add(new ProgramCard());
        }
    }
}
