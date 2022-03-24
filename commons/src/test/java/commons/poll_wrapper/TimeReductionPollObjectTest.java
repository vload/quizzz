package commons.poll_wrapper;

import commons.PlayerData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeReductionPollObjectTest {

    MultiPlayerPollObject p1;
    MultiPlayerPollObject p2;
    MultiPlayerPollObject p3;
    @BeforeEach
    void init() {
        p1 = new TimeReductionPollObject(new PlayerData("NLE"));
        p2 = new TimeReductionPollObject(new PlayerData("NLE"));
        p3 = new TimeReductionPollObject(new PlayerData("NL"));
    }

    @Test
    void getBody() {
        assertEquals(new PlayerData("NLE"),p1.getBody());
        assertEquals(new PlayerData("NLE"),p2.getBody());
    }

    @Test
    void testEquals() {
        assertEquals(p1,p2);
        assertNotEquals(p1,p3);
    }

    @Test
    void testHashCode() {
        assertEquals(p1.hashCode(),p2.hashCode());
        assertNotEquals(p1.hashCode(),p3.hashCode());
    }

    @Test
    void testToString() {
        var actual = p1.toString();
        assertTrue(actual.contains(TimeReductionPollObject.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("whoInitiated"));

    }
}