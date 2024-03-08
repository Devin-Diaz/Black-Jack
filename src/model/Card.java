package model;

public class Card {
    private final String suit; // the type of card
    private final String value; // value of card

    public Card(String suit, String value) { // constructor
        this.suit = suit;
        this.value = value;
    }

    // getters and setters
    public String getSuit() {return suit;}
    public String getValue() {return value;}
    public int getNumberValue() {
        if(value.equals("ACE")) return 11;
        else if(value.equals("JACK") || value.equals("QUEEN") || value.equals("KING"))
            return 10;
        else
            return Integer.parseInt(value);
    }

    @Override
    public String toString() {
        return value + " OF " + suit;
    }


}
