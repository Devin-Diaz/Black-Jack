package model;

import model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Deck {
    List<Card> deck; //field of type model.Card
    List<Card> savedState; // for resetting the deck

    public Deck() { //constructor
        this.deck = new ArrayList<>();

        String[] suits = {"CLOVERS", "DIAMONDS", "HEARTS", "SPADES"};
        String[] values = {"2", "3", "4", "5", "6",
                "7", "8", "9", "10", "JACK", "QUEEN", "KING", "ACE"};

        // creating deck of cards in orderly fashion
        for(String suit : suits) {
            for(String value : values) {
                this.deck.add(new Card(suit, value));
            }
        }
        savedState = new ArrayList<>(this.deck);
    } // end of constructor

    // peek at top card of deck
    public Card peekTopCard() {
        return this.deck.get(this.deck.size() - 1);
    }

    public Card dealCard() {
//        int randomCard = (int) (Math.random() * 52);
        return this.deck.remove(remainingCardsInDeck() - 1);
    }

    //check remaining cards in a deck, make sure there is enough for game
    public int remainingCardsInDeck() {
        return this.deck.size();
    }

    // method to shuffle cards
    public void shuffleCards() {
        Collections.shuffle(this.deck);
    }

    // reset deck once a game reaches a conclusion
    public void resetDeck() {
        this.deck = savedState;
    }

}
