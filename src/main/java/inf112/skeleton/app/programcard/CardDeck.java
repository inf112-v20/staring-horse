package inf112.skeleton.app.programcard;

import inf112.skeleton.app.enums.ProgramCardAction;

import java.util.ArrayList;

public class CardDeck {

    private ArrayList<ProgramCard> deck;

    public CardDeck(){
        this.deck = new ArrayList<>();
    }

    /**
     * Adds a ProgramCard to the deck
     * @param card ProgramCard
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
    public ArrayList<ProgramCard> drawNineProgramCards(){
        this.deck.clear();
        for(int i = 0; i < 9; i++){
            if (i < 8) {
                this.addCardToDeck(new ProgramCard(ProgramCardAction.getAllProgramCardActions()[i]));
            } else {
                this.addCardToDeck(new ProgramCard());
            }
        }
        return deck;
    }

    /**
     * Get ProgramCard in index from deck
     * @param index int
     * @return ProgramCard
     */
    public ProgramCard getCard(int index){
        return this.deck.get(index);
    }

}
