package game;

import model.Dealer;
import model.Deck;
import model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    //fields
    private final Deck deck;
    private final Dealer dealer;
    private final List<Player> players;
    private boolean gameState;

    public Game() { // constructor
        this.deck = new Deck();
        this.dealer = new Dealer();
        this.players = new ArrayList<>();
        this.gameState = false;
    }

    // starts black jack game
    public void startGame() {
        introduction();
        addPlayers();
        dealInitialCards();
        while(!gameOver()) {
            playersTurn();
            sleepingThread(1500);
            if(gameState) break;
            dealersTurn();
        }
        results();
    } // end of game starter


    // welcoming user to the game
    public void introduction() {
        System.out.println("STARTING BLACKJACK GAME BY DEVIN DIAZ");
        System.out.println();
        System.out.println();
    } // end of user welcome


    // setting player names
    public String setPlayersName() {
        Scanner scanner = new Scanner(System.in);
        String name = "";

        while(name.isEmpty()) {
            System.out.print("ENTER PLAYER NAME: ");
            name = scanner.nextLine().trim();

            if(name.isEmpty()) {
                System.out.println("INVALID ENTRY: Try again.");
            }
        }
        return name.toUpperCase();
    } // end of player name setter


    // Used to populate our list of players with players. Up to 4 players for now
    public void addPlayers() {
        Scanner scanner = new Scanner(System.in);
        int numPlayers = 0;

        do {
            System.out.print("Enter the number of players (1-4): ");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a number! Try again.");
                scanner.next(); // discard the non-integer input
            }
            numPlayers = scanner.nextInt();
        } while (numPlayers < 1 || numPlayers > 4);

        for (int i = 0; i < numPlayers; i++) {
            String name = setPlayersName();
            Player player = new Player(name);
            players.add(player);
        }
    } // end of player adder


    // checks if all players busted
    private boolean allPlayersBusted() {
        int counter = 0;
        for(Player player : players) {
            if(player.bustedChecker()) counter++;
        }
        return counter == players.size();
    } // end of method





    // checks if all PLAYERS have busted or are STANDING
    public boolean playerStatusChecker() {
        int counter = 0;
        for(Player player : players) {
            if(player.isStandingChecker() || player.bustedChecker()) {
                counter++;
            }
        }
        return counter == players.size();
    } // end of PLAYER checker


    //player turn method
    public void playersTurn() {
        sleepingThread(1500);
        //beginning of players turn
        System.out.println();
        System.out.println("**PLAYERS TURN**");
        for(int i = 0; i < 50; i++) System.out.print("-");
        System.out.println();

        if(playerStatusChecker()) {
            System.out.println("ALL PLAYERS HAVE BUSTED OR STOOD");
            gameState = true;
            return;
        }
        for(Player player : players) {
            System.out.println();
            // fields to not hardcode methods each time needed
            String players_name = player.getPlayerName();

            // announces which player is up
            System.out.println(players_name + "'s TURN");
            System.out.println();

            //checks if player has already busted so we can skip over to the next player
            if(player.bustedChecker()) {
                System.out.println(players_name + " BUSTED WITH A SCORE OF " + player.currentScore());
                sleepingThread(1500);
                continue;
            }

            //checks if player is standing so we can skip over to the next player
            if(player.isStandingChecker()) {
                System.out.println(players_name + " IS STANDING WITH A SCORE OF " + player.currentScore());
                sleepingThread(1500);
                continue;
            }

            // Where the game logic begins for the player
            int playerDecision = player.playerDecision();

            if(playerDecision == 1) {
                System.out.println(players_name + " HAS HIT!");
                System.out.println("CARD DRAWN IS " + deck.peekTopCard());
                player.hit(deck.dealCard());
                System.out.println(players_name +  "'s CURRENT CARDS: " + player.currentHand()
                + " SCORE OF " + player.currentScore());

                if(player.currentScore() > 21) {
                    player.bustedSetter();
                    sleepingThread(1500);
                }
                else {
                    System.out.println(players_name + " IS GOOD FOR THE ROUND");
                    sleepingThread(1500);
                }
            }
            else {
                System.out.println(players_name + " IS GOING TO STAND WITH A SCORE OF " + player.currentScore());
                player.standSetter();
                sleepingThread(1500);
            }
        }
    } // end of players turn

    public void dealInitialCards() {
        System.out.println();
        System.out.println("SHUFFLING CARDS...");
        deck.shuffleCards();
        sleepingThread(1500);
        System.out.println("DEALING CARDS...");
        sleepingThread(1500);
        System.out.println();
        for(Player player : players) {
            player.hit(deck.dealCard());
            player.hit(deck.dealCard());
            System.out.println(player.getPlayerName() + "'S CARDS ARE: " +
                    player.currentHand() + " SCORE OF " + player.currentScore());
            System.out.println();
            sleepingThread(1500);
        }
        dealer.hit(deck.dealCard());
        dealer.hit(deck.dealCard());
        System.out.println("DEALER'S CARDS ARE: " + dealer.currentHand() +
                " SCORE OF " + dealer.currentScore());
    }

    public void dealersTurn() {
        System.out.println();
        System.out.println("**DEALERS TURN**");
        for(int i = 0; i < 50; i++) System.out.print("-");
        System.out.println();

        if(dealer.currentScore() >= 17) {
            dealer.standSetter();
            return;
        }

        if (dealer.isStandingChecker()) {
            System.out.println("DEALER STANDS WITH A SCORE OF " + dealer.currentScore());
        }
        else {
            System.out.println("CARD DRAWN IS " + deck.peekTopCard());
            dealer.hit(deck.dealCard());
            System.out.println("DEALERS CARD'S ARE: " + dealer.currentHand() +
                     " SCORE OF " + dealer.currentScore());
        }

        if(dealer.currentScore() < 17) {
            System.out.println("DEALER HITS");
        }
        else if(dealer.currentScore() <= 21 && dealer.currentScore() >= 17) {
            dealer.standSetter();
        }
        else {
            dealer.bustedSetter();
            gameState = true; // game ends if dealer busts as now we need to check which player got closest to 21
        }
    } // end of dealer logic



    // dictates when to stop the while loop of player and dealer turns to determine the winner after that block
    public boolean gameOver() {
        return gameState;
    } // end of gameOver method



    private void winnerChecker() {

        if (allPlayersBusted() && dealer.bustedChecker()) {
            System.out.println("NO WINNERS IN THIS GAME!");
            return;
        }

        if (allPlayersBusted() && !dealer.bustedChecker()) {
            System.out.println("DEALER WIN'S THIS GAME!");
            return;
        }

        if (players.size() == 1) {

            if(dealer.bustedChecker()) System.out.println(players.get(0).getPlayerName() + " IS THE WINNER THIS GAME!");

            else if (players.get(0).currentScore() > dealer.currentScore() && !dealer.bustedChecker()) {
                System.out.println(players.get(0).getPlayerName() + " IS THE WINNER THIS GAME!");
            } else {
                System.out.println(dealer.getPlayerName() + " IS THE WINNER THIS GAME!");
            }
            return;
        }

        if(players.size() > 1) {
            List<Player> winners = new ArrayList<>();
            for(Player player : players) {
                if(!player.bustedChecker()) {
                    if(winners.isEmpty() || player.currentScore() > winners.get(0).currentScore()) {
                        winners.clear();
                        winners.add(player);
                    } else if (player.currentScore() == winners.get(0).currentScore()) {
                        winners.add(player);
                    }
                }
            }
            if(!winners.isEmpty() && (dealer.bustedChecker() || winners.get(0).currentScore() > dealer.currentScore())) {
                for(Player winner : winners) {
                    System.out.println(winner.getPlayerName() + " IS A WINNER THIS GAME!");
                }
            } else {
                System.out.println(dealer.getPlayerName() + " IS THE WINNER THIS GAME!");
            }
        }
    }

    private void results() {
        System.out.println();
        System.out.println();
        for(int i = 0; i < 50; i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.println("RESULTS OF THIS GAME: ");
        winnerChecker();
    }



    // delay between turns when in game
    public void sleepingThread(int time) {
        try{
            Thread.sleep(time); // in miliseconds
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    } // end of delayer feature

}