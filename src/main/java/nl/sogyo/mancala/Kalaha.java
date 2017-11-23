package nl.sogyo.mancala;

/**
 * Created by jbox on 6/29/2017.
 */
public class Kalaha {
    int seeds;
    Player owner;
    Kalaha neighbour;

    Kalaha() {
        seeds = 0;
        owner = new Player();
        int count = 1;
        neighbour = new Hole(owner.getOpponent(), count, this);
    }
    Kalaha(Player owner, int count, Kalaha start) {
        seeds = 0;
        this.owner = owner;
        neighbour = new Hole(owner.getOpponent(), ++count, start);
    }
    Kalaha(Player owner) {
        this.owner = owner;
    }
    public int getSeeds() {
        return seeds;
    }
    public Player getOwner() {
        return owner;
    }
    public Kalaha getNeighbour() {
        return neighbour;
    }
    public void pass(int seedsToPass, Player activePlayer) {
        if (!owner.isActive()) {
            neighbour.pass(seedsToPass, activePlayer);
        } else {
            seeds++;
            if (seedsToPass - 1 > 0) {
                neighbour.pass(seedsToPass - 1, activePlayer);
            }
        }
    }
    public Kalaha opposite() {
        Kalaha kalahaDummy = this;
        for (int i=0; i<7; i++) {
            kalahaDummy = kalahaDummy.getNeighbour();
        }
        return kalahaDummy;
    }
    public void moveToKalaha(int seedsToMove) {
            seeds += seedsToMove;
    }
    public boolean moveAvailable() {
        if (owner.getOpponent().isActive()) {
            return neighbour.canMove();
        } else {
            return opposite().getNeighbour().canMove();
        }
    }
    public boolean canMove() {
            return false;
    }
    public Player winner() {
        int diff = neighbour.score(owner);
        setScorePlayers(diff);
        if (diff > 0) {
            return owner;
        } else {
            return owner.getOpponent();
        }
    }
    private void setScorePlayers(int diff) {
        owner.setEndScore(24 + diff/2);
        owner.getOpponent().setEndScore(24 - diff/2);
    }
    public int score(Player player2) {
        if (owner == player2) {
            return seeds;
        } else {
            return neighbour.score(player2) - seeds;
        }
    }
}
