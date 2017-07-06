package nl.sogyo.mancala;

public class Mancala
{
    public static void main( String... args ) {
        Kalaha kalaha = new Kalaha();
        while (kalaha.moveAvailable()) {
            //actor chooses hole, hole moves
        }
        System.out.println(kalaha.winner()); //and scores
    }
}
