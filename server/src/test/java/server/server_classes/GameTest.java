package server.server_classes;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    AbstractGame s1;
    AbstractGame s2;
    @BeforeEach
    void init() {
        s1 = new SinglePlayerGame(3,"Bob");
        s2 = new MultiPlayerGame(4, List.of("Bob,Alice"));
    }

    @Test
    void constructorTest() {
        assertNotNull(s1);
        assertNotNull(s2);
        assertEquals(3,s1.gameID);
        assertEquals(4,s2.gameID);
    }

    @Test
    void isSinglePlayer() {
        assertTrue(s1.isSinglePlayer());
        assertFalse(s2.isSinglePlayer());
    }
}