package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {

    Activity a1;
    Activity a2;
    Activity a3;
    @BeforeEach
    void init() {
        a1 = new Activity("Shower",10.2,"example.com");
        a2 = new Activity("Shower",10.2,"example.com");
        a3 = new Activity("Flamethrower",99.3,"example.com");
    }

    @Test
    void constructorTest() {
        assertNotNull(a1);
    }

    @Test
    void getTitle() {
        assertEquals("Shower",a1.getTitle());
    }

    @Test
    void getEnergyConsumption() {
        assertEquals(10.2,a1.getEnergyConsumption());
    }

    @Test
    void getSource() {
        assertEquals("example.com",a1.getSource());
    }

    @Test
    void getQuestions() {
        assertNull(a1.getQuestions());
    }

    @Test
    void testEquals() {
        assertEquals(a1,a2);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(a1,a3);
    }

    @Test
    void testHashCode() {
        assertEquals(a1,a2);
        assertEquals(a1.hashCode(),a2.hashCode());
    }

    @Test
    void testNotEqualsHashCode() {
        assertNotEquals(a1,a3);
        assertNotEquals(a1.hashCode(),a3.hashCode());
    }

    @Test
    void hasToString() {
        String actual = a1.toString();
        assertTrue(actual.contains(Activity.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("title"));

    }
}