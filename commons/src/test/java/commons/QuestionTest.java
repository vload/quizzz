package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class QuestionTest {

    Question q1;
    Question q2;
    Question q3;
    Activity a1;
    Activity a2;
    Activity a3;
    @BeforeEach
    void init() {
        a1 = new Activity("Shower",30.2,"www.example.com");
        a2 = new Activity("Bike",15,"www.example.com");
        a3 = new Activity("Train",14,"www.example.com");
        q1 = new Question("What is the highest out of the following 3 activities",
                Stream.of(a1,a2,a3).collect(Collectors.toSet()), QuestionType.MC);
        q2 = new Question("What is the highest out of the following 3 activities",
                Stream.of(a1,a2,a3).collect(Collectors.toSet()), QuestionType.MC);
        q3 = new Question("Dummy question", Stream.of(a1,a2).collect(Collectors.toSet()), QuestionType.ESTIMATE);
    }

    @Test
    void constructorTest() {
        assertNotNull(q1);
        assertEquals("What is the highest out of the following 3 activities",q1.getQuestionText());
        assertEquals(Stream.of(a1,a2,a3).collect(Collectors.toSet()), q1.getActivities());
        assertEquals(QuestionType.MC, q1.getType());
    }

    @Test
    void getQuestionTest() {
        assertEquals("What is the highest out of the following 3 activities",q1.getQuestionText());
    }

    @Test
    void getActivitiesTest() {
        assertEquals(Stream.of(a1,a2,a3).collect(Collectors.toSet()), q1.getActivities());
    }

    @Test
    void getTypeTest() {
        assertEquals(QuestionType.MC, q1.getType());
    }

    @Test
    void testEquals() {
        assertEquals(q1,q2);
    }

    @Test
    void testNotEquals() {
        assertNotEquals(q1,q3);
    }

    @Test
    void testHashCode() {
        assertEquals(q1,q2);
        assertEquals(q1.hashCode(),q2.hashCode());
    }

    @Test
    void notEqualsHashCode() {
        assertNotEquals(q1,q3);
        assertNotEquals(q1.hashCode(),q3.hashCode());
    }

    @Test
    void hasToString() {
        String actual = q1.toString();
        assertTrue(actual.contains(Question.class.getSimpleName()));
        assertTrue(actual.contains("\n"));
        assertTrue(actual.contains("questionText"));

    }
}