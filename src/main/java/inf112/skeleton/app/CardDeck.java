package inf112.skeleton.app;

import java.util.ArrayList;

public class CardDeck {

    private ArrayList<ProgramCard> deck;

    public CardDeck(){
        this.deck = new ArrayList<>();
    }

    /**
     * Adds a ProgramCard to the deck
     * @param card
     */
    public void addCardToDeck(ProgramCard card) {
        deck.add(card);
    }

    public void addCardToDeck(ProgramCard card, int index) {
        deck.add(index, card);
    }

    /**
     * Draw 9 new random ProgramCards
     */
    public void drawNineProgramCards(){
        this.deck.clear();
        for(int i = 0; i < 9; i++){
            this.deck.add(new ProgramCard());
        }
    }

    /**
     * Get ProgramCard in index from deck
     * @param index
     * @return ProgramCard
     */
    public ProgramCard getCard(int index){
        return this.deck.get(index);
    }

}
