package inf112.skeleton.app;

import java.util.ArrayList;

public class CardDeck {

    private ArrayList<ProgramCard> deck;

    public CardDeck(){
        this.deck = new ArrayList<>();
    }

    public void drawNineProgramCards(){
        this.deck.clear();
        for(int i = 0; i < 9; i++){
            this.deck.add(new ProgramCard());
        }
    }

    public ProgramCard getCard(int index){
        return this.deck.get(index);
    }

}
