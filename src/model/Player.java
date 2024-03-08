package model;

import model.Card;
import model.Hand;

import java.util.List;
import java.util.Scanner;

public class Player {

    private Hand playersHand;
    private boolean standing;
    private String playerName;
    private boolean bustedGame;


    public Player(String playerName) {
        this.playersHand = new Hand();
        standing = false;
        this.playerName = playerName;
        bustedGame = false;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void hit(Card card) {
        this.playersHand.addCard(card);
    }

    public List<Card> currentHand() {
        return playersHand.viewHand();
    }

    public void standSetter() {
        System.out.println(getPlayerName() + " IS STANDING");
        System.out.println();
        this.standing = true;
    }

    public boolean isStandingChecker() {
        return standing;
    }

    public int currentScore() {
        return playersHand.currentHandScore();
    }


    public void bustedSetter() {
        if(playersHand.isBust()) {
            System.out.println(getPlayerName() + " BUSTED. YOUR SCORE IS " + playersHand.currentHandScore());
            bustedGame = true;
        }
    }

    public boolean bustedChecker() {
        return bustedGame;
    }



    // where players chooses to hit or stand then gets stored
    public int playerDecision() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.print("PRESS 1 TO HIT | PRESS 2 TO STAND: ");
            while(!scanner.hasNextInt()) {
                System.out.println("INVALID ENTRY. Try again.");
                scanner.nextInt();
            }
            choice = scanner.nextInt();

        } while(choice != 1 && choice != 2);

        return choice;
    }


}
