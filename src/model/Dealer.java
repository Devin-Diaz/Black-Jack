package model;

public class Dealer extends Player {

    private final Hand dealersHand;
    public Dealer() {
        super("DEALER");
        this.dealersHand = new Hand();
    }

    // HIT method inherited from model.Player class.

    // STANDING SETTER inherited from model.Player class

    // STANDER CHECKER inherited from model.Player class

    // CURRENT DEALER SCORE inherited from model.Player class

    // BUSTED SETTER and CHECKER inherited from model.Player class
}
