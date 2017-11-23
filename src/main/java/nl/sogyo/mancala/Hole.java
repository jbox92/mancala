package nl.sogyo.mancala;

/**
 * Created by jbox on 6/29/2017.
 */
public class Hole extends Kalaha {
    Hole(Player owner, int count, Kalaha start) {
        super(owner);
        seeds = 4;
        count++;
        if (count == 7) {
            neighbour = new Kalaha(owner, count, start);
        } else if (count<14) {
            makeNeighbour(count, start);
        } else if (count == 14) {
            neighbour = start;
        }
    }
    private void makeNeighbour(int count, Kalaha start) {
        neighbour = new Hole(owner, count, start);
    }
    public void move() {
        if (legalToMove()) {
            int seedsToPass = seeds;
            seeds = 0;
            neighbour.pass(seedsToPass, owner);
        }
    }
    private boolean legalToMove() {
        if (seeds != 0 && owner.isActive()) {
            return true;
        } else {
            return false;
        }
    }
    private void processMove(Player activePlayer) {
        if (owner == activePlayer && seeds == 1 && opposite().getSeeds() != 0) {
            steal();
        }
        activePlayer.switchActive();
    }
    private void steal() {
        seeds += opposite().getSeeds();
        opposite().seeds = 0;
        neighbour.moveToKalaha(seeds);
        seeds = 0;
    }
    @Override
    public Kalaha opposite() {
        if (!(neighbour instanceof Hole)) {
            return neighbour.getNeighbour();
        }
        return neighbour.opposite().getNeighbour();
    }
    @Override
    public void moveToKalaha(int seedsToMove) {
        neighbour.moveToKalaha(seedsToMove);
    }
    @Override
    public boolean canMove() {
        return !(seeds == 0) || neighbour.canMove();
    }
    @Override
    public int score(Player player2) {
        if (owner == player2) {
            return neighbour.score(player2) + seeds;
        } else {
            return neighbour.score(player2) - seeds;
        }
    }
    @Override
    public void pass(int seedsToPass, Player activePlayer) {
        seeds++;
        if (seedsToPass - 1 > 0) {
            neighbour.pass(seedsToPass - 1, activePlayer);
        } else {
            processMove(activePlayer);
        }
    }
}
