package model;

import model.Card;

import java.util.ArrayList;
import java.util.List;

public class Hand {
    private static final int WINNING_NUMBER = 21;
    List<Card> playersCards;

    public Hand() {
        playersCards = new ArrayList<>();
    }

    public void addCard(Card card) {
        playersCards.add(card);
    }

    public int currentHandScore() {
        int card_score = 0;
        int ace_counter = 0;

        for(Card card : playersCards) {
            card_score += card.getNumberValue();
            if(card.getValue().equals("ACE")) {
                ace_counter++;
            }
        }
        while(card_score > WINNING_NUMBER && ace_counter > 0) {
            card_score -= 10;
            ace_counter--;
        }
        return card_score;
    }

    public boolean isBust() {
        return currentHandScore() > WINNING_NUMBER;
    }

    public List<Card> viewHand() {
        return this.playersCards;
    }


}
