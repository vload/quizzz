package server.api;

import commons.Activity;
import commons.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.database.MockQuestionRepository;

import java.util.HashSet;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class QuestionControllerTest {
    private Random random;
    private MockQuestionRepository repo;
    private Activity a1, a2, a3;
    private Question q1, q2, q3;
    private QuestionController controller;

    /**
     * Setup for the tests 
     */
    @BeforeEach
    public void setup() {
        repo = new MockQuestionRepository();
        random = new Random();

        a1 = new Activity("activity 1", 50, "facebook.com");
        a2 = new Activity("activity 2", 60, "twitter.com");
        a3 = new Activity("activity 3", 70, "google.com");

        HashSet<Activity> set = new HashSet<>();
        set.add(a1);
        set.add(a2);
        set.add(a3);

        q1 = new Question("text-q1");
        q2 = new Question("text-q2", set);
        q3 = new Question("text-q3", set);

        controller = new QuestionController(random, repo);
    }

    /**
     * Test for getAll 
     */
    @Test
    void getAll() {
        repo.save(q1);
        repo.save(q2);
        repo.save(q3);

        var all = controller.getAll();
        assertEquals(3, all.size());
        assertTrue(all.contains(q1));
        assertTrue(all.contains(q2));
        assertTrue(all.contains(q3));
    }
    
    /**
     * Test for getById
     */
    @Test
    void getById() {
        repo.save(q1);
        repo.save(q2);
        repo.save(q3);

        var response1 = controller.getById(q1.id);
        var response2 = controller.getById(q2.id);
        assertEquals(q1, response1.getBody());
        assertEquals(q2, response2.getBody());
    }
}
