package server.server_classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SinglePlayerGameTest {


    AbstractGame s1;
    AbstractGame s2;
    AbstractGame s3;
    SinglePlayerGame sub;
    @BeforeEach
    void init() {
        s1 = new SinglePlayerGame(5L,"Marcus");
        s2 = new SinglePlayerGame(5L,"Marcus");
        s3 = new SinglePlayerGame(6L,"Marcu");
        sub = (SinglePlayerGame) s1;
    }

    @Test
    void constructorTest() {
        assertNotNull(s1);
        assertEquals(5,s2.gameID);
    }

    @Test
    void getScore() {
        assertEquals(0L,sub.getScore());
    }

    @Test
    void getPlayerName() {
        SinglePlayerGame sub = (SinglePlayerGame) s1;
        assertEquals("Marcus",sub.getPlayerName());
    }

    @Test
    void testEquals() {
        assertEquals(s1,s2);
        assertNotEquals(s1,s3);
    }

    @Test
    void testHashCode() {
        assertEquals(s1.hashCode(),s2.hashCode());
        assertNotEquals(s1.hashCode(),s3.hashCode());
    }

    @Test
    public void hasToString() {
        var actual = s1.toString();
        assertTrue(actual.contains(SinglePlayerGame.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("playerName"));
    }
}