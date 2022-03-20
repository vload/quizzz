package server.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.api.QuestionGenerator;
import server.database.MockActivityRepository;
import server.server_classes.AbstractGame;
import server.server_classes.MultiPlayerGame;
import server.server_classes.SinglePlayerGame;

import static org.junit.jupiter.api.Assertions.*;

import java.util.*;

class MultiPlayerGameServiceTest {

    Map<Long, AbstractGame> gameMap;
    MultiPlayerGameService s1;
    @BeforeEach
    void init() {
        gameMap = new HashMap<>();
        MockActivityRepository mockRepo = new MockActivityRepository();
        s1 = new MultiPlayerGameService(gameMap,
                new QuestionGenerator(mockRepo,new Random(42)));
    }

    @Test
    void isValidGame() {
        assertFalse(s1.isValidGame(0L));
        s1.insertGame(new SinglePlayerGame(0L,"Hello",new ArrayList<>()));
        assertFalse(s1.isValidGame(0L));
        s1.insertGame(new MultiPlayerGame(3L,List.of("Hello","jay"),new ArrayList<>()));
        assertTrue(s1.isValidGame(3L));
    }
}