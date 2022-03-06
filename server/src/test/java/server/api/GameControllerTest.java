package server.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import server.server_classes.*;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private Map<Long, AbstractGame> games;
    private GameController sut;
    @BeforeEach
    void init() {
        games = new HashMap<>();
        sut = new GameController(games);
    }

    @Test
    void nullNameTest() {
        var r1 = sut.startSinglePlayer("");
        assertEquals(BAD_REQUEST,r1.getStatusCode());
        var r2 = sut.startMultiPlayer(null);
        assertEquals(BAD_REQUEST,r2.getStatusCode());
    }

    @Test
    void nullIDDelete() {
        var r1 = sut.deleteGame("");
        assertEquals(BAD_REQUEST,r1.getStatusCode());
        var r2 = sut.deleteGame(null);
        assertEquals(BAD_REQUEST,r2.getStatusCode());

    }

    @Test
    void generatingIDTestSingleplayer() {
        sut.startSinglePlayer("Alice");
        sut.startSinglePlayer("Bob");
        var response = sut.startSinglePlayer("Pac");
        assertEquals(HttpStatus.OK,response.getStatusCode());
        assertEquals(2L,response.getBody());
    }

    @Test
    void startSinglePlayer() {
        sut.startSinglePlayer("Hudson");
        assertEquals(1,games.size());
        var game = games.get(0L);
        assertTrue(game instanceof SinglePlayerGame);
        assertEquals(((SinglePlayerGame) game).getPlayerName(),"Hudson");
    }

    @Test
    void scoreUpdater() {


    }

    @Test
    void deleteGame() {
        sut.startSinglePlayer("Bob");
        assertEquals(1,games.size());
        sut.startSinglePlayer("Alice");
        assertEquals(2,games.size());
        sut.deleteGame("0");
        assertEquals(1,games.size());
        SinglePlayerGame aliceGame = (SinglePlayerGame) games.get(1L);
        assertEquals("Alice",aliceGame.getPlayerName());
    }

    @Test
    void startMultiPlayer() {

    }

    @Test
    void startMultiPlayerIP() {
    }
}