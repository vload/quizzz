package server.server_classes;

import commons.Activity;
import commons.Question;
import commons.QuestionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class MultiPlayerGameTest {

    AbstractGame s1;
    AbstractGame s2;
    AbstractGame s3;
    MultiPlayerGame sub;
    @BeforeEach
    void init() {
        Activity a1 = new Activity("02-shower", "/shower.png",
                "Shower", 10.2,"example.com");
        Activity a2 = new Activity("02-shower", "/shower.png",
                "Shower", 10.1,"example.com");
        Activity a3 = new Activity("05-flamethrower",
                "/flamethrower.png","Flamethrower", 99.3,"example.com");

        Question question = new Question("Sample question",
                Stream.of(a1,a2,a3).collect(Collectors.toSet()), QuestionType.MC, a1.getId());



        s1 = new MultiPlayerGame(5,List.of("Marcus","Kanye","Alice"),List.of(question,question));
        s2 = new MultiPlayerGame(5,List.of("Marcus","Kanye","Alice"), List.of(question,question));
        s3 = new MultiPlayerGame(4,List.of("Marcus","Kanye","Alce"),new ArrayList<>());
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