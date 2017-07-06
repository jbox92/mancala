package nl.sogyo.mancala;

/**
 * Created by jbox on 6/29/2017.
 */
public class Player {
    private boolean active;
    private Player opponent;
    private int endScore;

    Player() {
        active = false;
        opponent = new Player(this);
    }
    Player(Player opponent) {
        active = true;
        this.opponent = opponent;
    }
    public Player getOpponent() {
        return opponent;
    }
    public boolean isActive() {
        return active;
    }
    public void switchActive() {
        active = false;
        opponent.active = true;
    }
    public void setEndScore(int endScore) {
        this.endScore = endScore;
    }
    public int getEndScore() {
        return endScore;
    }
}
