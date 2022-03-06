package server.server_classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerGameTest {

    AbstractGame s1;
    AbstractGame s2;
    AbstractGame s3;
    MultiPlayerGame sub;
    @BeforeEach
    void init() {
        s1 = new MultiPlayerGame(5,List.of("Marcus","Kanye","Alice"));
        s2 = new MultiPlayerGame(5,List.of("Marcus","Kanye","Alice"));
        s3 = new MultiPlayerGame(4,List.of("Marcus","Kanye","Alce"));
        sub = (MultiPlayerGame) s1;
    }

    @Test
    void constructorTest() {
        assertNotNull(s1);
        assertEquals(5,s1.gameID);
        assertEquals(5,s2.gameID);
        assertEquals(4,s3.gameID);
    }

    @Test
    void getPlayerNames() {
        assertEquals(List.of("Marcus","Kanye","Alice"),sub.getPlayerNames());
    }

    @Test
    void getNameScorePairs() {
        Map<String,Long> map = new HashMap<>();
        map.put("Marcus",0L);
        map.put("Kanye",0L);
        map.put("Alice",0L);
        assertEquals(map,sub.getNameScorePairs());
    }

    @Test
    void getPlayerScore() {
        sub.givePoints("Kanye",60);
        assertEquals(60L,sub.getPlayerScore("Kanye"));
    }

    @Test
    void givePoints() {
        sub.givePoints("Alice",779);
        assertEquals(779,sub.getPlayerScore("Alice"));
        sub.givePoints("Kanye",0);
        assertEquals(0,sub.getPlayerScore("Kanye"));
        assertThrows(InvalidParameterException.class, () -> {
            sub.givePoints(null,32);
        });
        assertThrows(InvalidParameterException.class,() -> {
            sub.givePoints("",532);
        });
    }

    @Test
    void addPlayer() {
        assertTrue(sub.addPlayer("Ryan",32));
        assertTrue(sub.getNameScorePairs().containsKey("Ryan"));
        assertEquals(32,sub.getPlayerScore("Ryan"));
        assertEquals(List.of("Marcus","Kanye","Alice","Ryan"),sub.getPlayerNames());
        assertFalse(sub.addPlayer("Kanye",42));
        assertFalse(sub.addPlayer("",58));
        assertFalse(sub.addPlayer(null,569));
    }

    @Test
    void deletePlayer() {
        sub.addPlayer("Ryan",50);
        assertTrue(sub.getNameScorePairs().containsKey("Ryan"));
        assertTrue(sub.deletePlayer("Ryan"));
        assertFalse(sub.getNameScorePairs().containsKey("Ryan"));
        assertEquals(List.of("Marcus","Kanye","Alice"),sub.getPlayerNames());
        assertFalse(sub.deletePlayer("KJDK"));
        assertFalse(sub.deletePlayer(null));
        assertFalse(sub.deletePlayer(""));
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
        assertTrue(actual.contains(MultiPlayerGame.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("playerNames"));
    }
}