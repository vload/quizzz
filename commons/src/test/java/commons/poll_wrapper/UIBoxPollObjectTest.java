package commons.poll_wrapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UIBoxPollObjectTest {

    MultiPlayerPollObject p1;
    MultiPlayerPollObject p2;
    MultiPlayerPollObject p3;
    UIBoxPollObject sub;
    @BeforeEach
    void init() {
        p1 = new UIBoxPollObject(List.of("Hello","Message","Test"));
        p2 = new UIBoxPollObject(List.of("Hello","Message","Test"));
        p3 = new UIBoxPollObject(List.of("Hello","Mege","Test"));
     }

    @Test
    void constructorTest() {
        assertNotNull(p1);
    }

    @Test
    void getBody() {
        assertEquals(List.of("Hello","Message","Test"),p1.getBody());
        assertEquals(List.of("Hello","Mege","Test"),p3.getBody());
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
    void testHasString() {
        var actual = p1.toString();
        assertTrue(actual.contains(UIBoxPollObject.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("uiMessages"));
    }
}