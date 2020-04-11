package inf112.skeleton.app;

import inf112.skeleton.app.programCard.CardDeck;
import inf112.skeleton.app.programCard.ProgramCard;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CardDeckTest {

    CardDeck cardDeck;

    @Before
    public void before() {
        cardDeck = new CardDeck();
    }

    @Test
    public void drawNineProgramCardsShouldReturn9ProgramCards() {
        assertEquals(cardDeck.drawNineProgramCards().size(), 9);
    }

    @Test
    public void shouldAddCardToDeck() {
        ProgramCard programCard = new ProgramCard();
        cardDeck.addCardToDeck(programCard);
        assertEquals(cardDeck.getCard(0), programCard);
    }

}

