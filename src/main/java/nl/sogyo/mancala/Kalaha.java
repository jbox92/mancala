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
        if (isKalaha() && !owner.isActive()) {
            neighbour.pass(seedsToPass, activePlayer);
        } else {
            seeds++;
            if (seedsToPass - 1 > 0) {
                neighbour.pass(seedsToPass - 1, activePlayer);
            } else {
                processMove(activePlayer);
            }
        }
    }
    private void processMove(Player activePlayer) {
        if (isKalaha()) {

        } else if (owner == activePlayer && seeds == 1 && opposite().getSeeds() != 0) {
            seeds += opposite().getSeeds();
            opposite().seeds = 0;
            neighbour.moveToKalaha(seeds);
            seeds = 0;
            activePlayer.switchActive();
        } else {
            activePlayer.switchActive();
        }
    }
    public Kalaha opposite() {
        if (isKalaha()) {
            Kalaha kalahaDummy = this;
            for (int i=0; i<7; i++) {
                kalahaDummy = kalahaDummy.getNeighbour();
            }
            return kalahaDummy;
        }
        if (neighbour.isKalaha()) {
            return neighbour.getNeighbour();
        }
        return getNeighbour().opposite().getNeighbour();
    }
    private void moveToKalaha(int seedsToMove) {
        if (isKalaha()) {
            seeds += seedsToMove;
        } else {
            neighbour.moveToKalaha(seedsToMove);
        }
    }
    public boolean moveAvailable() {
        if (owner.getOpponent().isActive()) {
            return neighbour.canMove();
        } else {
            return opposite().getNeighbour().canMove();
        }
    }
    private boolean canMove() {
        if (neighbour.isKalaha()) {
            return !(seeds == 0);
        } else {
            return !(seeds == 0) || neighbour.canMove();
        }
    }
    public Player winner() {
        int diff = seeds + neighbour.score(owner);
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
    private int score(Player player2) {
        if (neighbour.isKalaha() && neighbour.getOwner() == player2) {
            return seeds;
        } else if (owner == player2) {
            return neighbour.score(player2) + seeds;
        } else {
            return neighbour.score(player2) - seeds;
        }
    }
    private boolean isKalaha() {
        return !(this instanceof Hole);
    }
}
