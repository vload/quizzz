package server.services;

import commons.PlayerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.QuestionGenerator;
import server.database.MockActivityRepository;
import server.server_classes.AbstractGame;
import server.server_classes.IdGenerator;
import server.server_classes.MultiPlayerGame;
import server.server_classes.SinglePlayerGame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class MultiPlayerGameServiceTest {

    Map<Long, AbstractGame> gameMap;
    MultiPlayerGameService s1;
    SinglePlayerGameService s2; // Testing ID generation between 2 services
    @BeforeEach
    void init() {
        IdGenerator generator = new IdGenerator();
        gameMap = new HashMap<>();
        MockActivityRepository mockRepo = new MockActivityRepository();
        s1 = new MultiPlayerGameService(generator,gameMap,
                new QuestionGenerator(mockRepo,new Random(42)));
        s2 = new SinglePlayerGameService(generator,gameMap,new QuestionGenerator(mockRepo,new Random(42)));
    }

    @Test
    void isValidGame() {
        assertFalse(s1.isValidGame(0L));
        s1.insertGame(new SinglePlayerGame(0L,"Hello",new ArrayList<>()));
        assertFalse(s1.isValidGame(0L));
        s1.insertGame(new MultiPlayerGame(3L,List.of(
                new PlayerData("Hello"),
                new PlayerData("jay")),new ArrayList<>()));
        assertTrue(s1.isValidGame(3L));
    }

    @Test
    void createMultiplayerGame() {
        PlayerData d1 = new PlayerData("Taco");
        PlayerData d2 = new PlayerData("Michael");
        PlayerData d3 = new PlayerData("Barack");
        List<PlayerData> playerDataList = List.of(d1,d2,d3);
        var r1 = s1.createMultiplayerGame(playerDataList);
        assertEquals(0L,r1);
        assertEquals(1,gameMap.size());
        var r2 = s2.createSinglePlayerGame("Test");
        assertEquals(2,gameMap.size());
        assertEquals(1L,r2);
        var r3 = s1.createMultiplayerGame(List.of(d1,d2));
        assertEquals(3,gameMap.size());
    }

    @Test
    void addMessageToInformationBox() {
        assertNull(s1.addMessageToInformationBox(0L,"Hello"));
        s1.createMultiplayerGame(
                List.of(new PlayerData("Taco"),
                        new PlayerData("Pad")));
        assertEquals("Test",s1.addMessageToInformationBox(0L,"Test"));
        assertNull(s1.addMessageToInformationBox(1L,"Test"));
        assertEquals(List.of("Test"),s1.getInformationBox(0L));
    }

    @Test
    void getInformationBox() {
        assertNull(s1.getInformationBox(0L));
        s1.createMultiplayerGame(
                List.of(new PlayerData("Taco"),
                        new PlayerData("Pad")));
        assertEquals(new ArrayList<>(), s1.getInformationBox(0L));
        assertEquals("Test", s1.addMessageToInformationBox(0L, "Test"));
        assertEquals(List.of("Test"), s1.getInformationBox(0L));
    }

    @Test
    void getPlayerScores() {
        PlayerData d1 = new PlayerData("Taco");
        PlayerData d2 = new PlayerData("Michael");
        PlayerData d3 = new PlayerData("Barack");
        s1.createMultiplayerGame(List.of(d1,d2,d3));
        d1.addScore(30L);
        d2.addScore(60L);
        d3.addScore(90L);
        Map<String,Long> sample = new HashMap<>();
        sample.put("Taco",30L);
        sample.put("Michael",60L);
        sample.put("Barack",90L);
        assertEquals(sample,s1.getPlayerScores(0L));
    }
}