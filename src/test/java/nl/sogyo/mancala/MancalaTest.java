package nl.sogyo.mancala;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MancalaTest {
    Kalaha kalaha;
    @Before
    public void setUp() {
        kalaha = new Kalaha();
    }
    @Test
    public void testApp() {
        Assert.assertTrue(true);
    }
    @Test
    public void testNumberOfSeedsInKalaha() {
        Assert.assertEquals(0,kalaha.getSeeds());
    }
    @Test
    public void testNumberOfSeedsInFirstHole() {
        Player player = new Player();
        Kalaha kalaha = new Kalaha(player);
        Hole hole = new Hole(player,0,kalaha);
        Assert.assertEquals(4, hole.getSeeds());
    }
    @Test
    public void testKalahaMakesOpponents() {
        Assert.assertEquals(kalaha.getOwner(),kalaha.getOwner().getOpponent().getOpponent());
        Assert.assertNotEquals(kalaha.getOwner(),kalaha.getOwner().getOpponent());
    }
    @Test
    public void testFirstHoleBelongsToOpponent() {
        Assert.assertEquals(kalaha.getOwner(),kalaha.getNeighbour().getOwner().getOpponent());
    }
    @Test
    public void testSecondHoleBelongsToOpponent() {
        Assert.assertEquals(kalaha.getOwner(),kalaha.getNeighbour().getNeighbour().getOwner().getOpponent());
    }
    @Test
    public void test6thHoleCreatedForOpponent() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<6; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        Assert.assertEquals(kalaha.getOwner(),kalahaTest.getOwner().getOpponent());
    }
    @Test
    public void testCreatingOpponentsKalaha() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<7; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        Assert.assertEquals(kalaha.getOwner().getOpponent(),kalahaTest.getOwner());
        Assert.assertEquals(0,kalahaTest.getSeeds());
    }
    @Test
    public void test1stHolePlayer() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<8; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        Assert.assertEquals(kalaha.getOwner(),kalahaTest.getOwner());
        Assert.assertEquals(4,kalahaTest.getSeeds());
    }
    @Test
    public void test6thHolePlayer() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<13; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        Assert.assertEquals(kalaha.getOwner(),kalahaTest.getOwner());
    }
    @Test
    public void testCircle() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<13; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        Assert.assertEquals(kalaha,kalahaTest.getNeighbour());
    }
    @Test
    public void testNeighbourGetsASeedAfterMove() {
        int seedsBefore = kalaha.getNeighbour().getNeighbour().getSeeds();
        Hole holeToMove = (Hole) kalaha.getNeighbour();
        holeToMove.move();
        Assert.assertEquals(0,kalaha.getNeighbour().getSeeds());
        Assert.assertEquals(seedsBefore + 1,kalaha.getNeighbour().getNeighbour().getSeeds());
    }
    @Test
    public void testSkipKalahaOpponent() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<5; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        Hole holeToMove = (Hole) kalahaTest;
        holeToMove.seeds = 12;
        holeToMove.move();
        Assert.assertEquals(0,kalaha.getSeeds());
        Assert.assertEquals(0,holeToMove.getSeeds());
        Assert.assertEquals(5,kalaha.getNeighbour().getSeeds());
    }
    @Test
    public void testOtherPlayersTurn() {
        Hole holeToMove = (Hole) kalaha.getNeighbour();
        holeToMove.move();
        Assert.assertTrue(kalaha.getOwner().isActive());
    }
    @Test
    public void testTurnCycle() {
        Hole holeToMoveFirst = (Hole) kalaha.getNeighbour();
        Kalaha kalahaInter = kalaha;
        for (int i=0; i<8; i++) {
            kalahaInter = kalahaInter.getNeighbour();
        }
        Hole holeToMoveSecond = (Hole) kalahaInter;
        holeToMoveFirst.move();
        holeToMoveSecond.move();
        Assert.assertEquals(0,holeToMoveSecond.getSeeds());
    }
    @Test
    public void testKalahaExtraTurn() {
        Hole holeToMoveFirst = (Hole) kalaha.getNeighbour().getNeighbour().getNeighbour();
        Hole holeToMoveSecond = (Hole) kalaha.getNeighbour();
        Kalaha kalahaTarget = kalaha;
        for (int i=0; i<7; i++) {
            kalahaTarget = kalahaTarget.getNeighbour();
        }
        holeToMoveFirst.move();
        holeToMoveSecond.move();
        Assert.assertEquals(1,holeToMoveFirst.getSeeds());
        Assert.assertEquals(6,holeToMoveFirst.getNeighbour().getSeeds());
        Assert.assertEquals(1,kalahaTarget.getSeeds());
    }
    @Test
    public void testFindsOpposite() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<13; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        Assert.assertEquals(kalahaTest, kalaha.getNeighbour().opposite());
    }
    @Test
    public void testFindsOpposingKalaha() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<7; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        Assert.assertEquals(kalahaTest, kalaha.opposite());
    }
    @Test
    public void testSteals() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<6; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        kalaha.getNeighbour().seeds = 0;
        kalahaTest.seeds = 8;
        Hole hole = (Hole) kalahaTest;
        hole.move();
        Assert.assertEquals(7,kalahaTest.getNeighbour().getSeeds());
        Assert.assertEquals(0,kalaha.getNeighbour().getSeeds());
    }
    @Test
    public void testDontStealFromEmptyHole() {
        Hole hole1 = (Hole) kalaha.getNeighbour();
        hole1.seeds = 1;
        kalaha.getNeighbour().getNeighbour().seeds = 0;
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<12; i++) {
            kalahaTest = kalahaTest.getNeighbour();
        }
        kalahaTest.seeds = 0;
        hole1.move();
        Assert.assertEquals(0,kalaha.opposite().getSeeds());
    }
    @Test
    public void testMoveAvailableOnEmptyBoard() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<6; i++) {
            kalahaTest = kalahaTest.getNeighbour();
            kalahaTest.seeds = 0;
        }
        Assert.assertFalse(kalaha.moveAvailable());
    }
    @Test
    public void testMoveAvailableOnNotEmptyBoard() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<6; i++) {
            kalahaTest = kalahaTest.getNeighbour();
            if (i != 4) {
                kalahaTest.seeds = 0;
            }
        }
        Assert.assertTrue(kalaha.moveAvailable());
    }
    @Test
    public void testOpponentsBoardIsEmpty() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<13; i++) {
            kalahaTest = kalahaTest.getNeighbour();
            if (i>6) {
                kalahaTest.seeds = 0;
            }
        }
        Assert.assertTrue(kalaha.moveAvailable());
    }
    @Test
    public void testPlayer2StartsOnEmptyBoard() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<13; i++) {
            kalahaTest = kalahaTest.getNeighbour();
            if (i>6) {
                kalahaTest.seeds = 0;
            }
        }
        Hole hole = (Hole) kalaha.getNeighbour();
        hole.move();
        Assert.assertFalse(kalaha.moveAvailable());
    }
    @Test
    public void testGameDecidesWinner() {
        Kalaha kalahaTest = kalaha;
        for (int i=0; i<6; i++) {
            kalahaTest = kalahaTest.getNeighbour();
            kalahaTest.seeds = 0;
        }
        kalahaTest.getNeighbour().seeds = 20;
        Assert.assertEquals(kalaha.getOwner(),kalaha.winner());
    }
    @Test
    //just for fun
    public void testRealGame() {
        Hole hole1 = (Hole) kalaha.getNeighbour();
        Hole hole2 = (Hole) hole1.getNeighbour();
        Hole hole3 = (Hole) hole2.getNeighbour();
        Hole hole4 = (Hole) hole3.getNeighbour();
        Hole hole5 = (Hole) hole4.getNeighbour();
        Hole hole6 = (Hole) hole5.getNeighbour();
        Hole hole7 = (Hole) hole6.getNeighbour().getNeighbour();
        Hole hole8 = (Hole) hole7.getNeighbour();
        Hole hole9 = (Hole) hole8.getNeighbour();
        Hole hole10 = (Hole) hole9.getNeighbour();
        Hole hole11 = (Hole) hole10.getNeighbour();
        Hole hole12 = (Hole) hole11.getNeighbour();
        hole3.move();
        hole6.move();
        hole8.move();
        hole9.move();
        hole1.move();
        hole10.move();
        hole4.move();
        hole8.move();
        hole5.move();
        hole10.move();
        hole2.move();
        hole11.move();
        hole5.move();
        hole3.move();
        hole5.move();
        hole12.move();
        hole5.move();
        hole7.move();
        hole2.move();
        hole9.move();
        hole8.move();
        hole4.move();
        hole9.move();
        hole5.move();
        hole10.move();
        hole1.move();
        hole7.move();
        hole3.move();
        hole12.move();
        Assert.assertEquals(kalaha.owner.getOpponent(), kalaha.winner());
        Assert.assertEquals(36,kalaha.owner.getOpponent().getEndScore());
        Assert.assertEquals(12,kalaha.owner.getEndScore());
    }
}
