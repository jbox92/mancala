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
}
