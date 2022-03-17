package server.api;

import commons.LeaderboardEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import server.database.MockLeaderboardRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LeaderboardControllerTest {

    LeaderboardEntry e1;
    LeaderboardEntry e2;
    LeaderboardEntry e3;
    MockLeaderboardRepository repo;
    LeaderboardController sut;

    @BeforeEach
    void init() {
        e1 = new LeaderboardEntry(0,"Boris",560L);
        e2 = new LeaderboardEntry(1,"Simon",902L);
        e3 = new LeaderboardEntry(2,"Tyrone",2L);
        repo = new MockLeaderboardRepository();
        repo.saveAll(List.of(e1,e2,e3));
        sut = new LeaderboardController(repo);
    }

    void constructorTest() {
        assertNotNull(sut);
    }


    @Test
    void getAllInOrder() {
        LeaderboardController fakeSUT = new LeaderboardController(
                new MockLeaderboardRepository());
        assertEquals(new ArrayList<LeaderboardEntry>(),fakeSUT.getAllInOrder().getBody());
        assertEquals(List.of(e2,e1,e3),sut.getAllInOrder().getBody());
    }

    @Test
    void add() {
        LeaderboardController sutTwo = new LeaderboardController(new MockLeaderboardRepository());
        sutTwo.add(e1);
        assertEquals(1, Objects.requireNonNull(sutTwo.getAllInOrder().getBody()).size());
        assertEquals(e1,sutTwo.getAllInOrder().getBody().get(0));
        sutTwo.add(e2);
        assertEquals(2,sutTwo.getAllInOrder().getBody().size());
        assertEquals(e2,sutTwo.getAllInOrder().getBody().get(0));
        var r1 = sutTwo.add(null);
        assertEquals(HttpStatus.BAD_REQUEST,r1.getStatusCode());
        assertEquals(2,sutTwo.getAllInOrder().getBody().size());
        var r2 = sutTwo.add(new LeaderboardEntry(0,null,9L));
        assertEquals(HttpStatus.BAD_REQUEST,r2.getStatusCode());
        assertEquals(2,sutTwo.getAllInOrder().getBody().size());
        var r3 = sutTwo.add(new LeaderboardEntry(1,"",3L));
        assertEquals(HttpStatus.BAD_REQUEST,r3.getStatusCode());
        assertEquals(2,sutTwo.getAllInOrder().getBody().size());
    }
}