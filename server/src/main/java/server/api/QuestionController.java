package server.api;

import java.util.*;

import commons.Activity;
import commons.QuestionType;
import org.hibernate.Hibernate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import commons.Question;
import org.springframework.web.bind.annotation.RestController;
import server.database.QuestionRepository;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final Random random;
    private final QuestionRepository repo;

    /**
     * Adding checkstyle
     * @param random random object
     * @param repo the question repo
     */
    public QuestionController(Random random, QuestionRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    /**
     * Return all questions
     * @return returns all questions
     */
    @GetMapping(path = { "", "/" })
    public List<Question> getAll() {
        return repo.findAll();
    }

    /**
     * Gets a question with id id
     * @param id the id
     * @return the question
     */
    @GetMapping("/{id}")
    public ResponseEntity<Question> getById(@PathVariable("id") long id) {
        if (id < 0 || !repo.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        var proxy = repo.getById(id);
        return ResponseEntity.ok((Question) Hibernate.unproxy(proxy));
    }

    /**
     * This function returns a randomly generated question by choosing random activities out of its activity set.
     * @return the question
     */
    @GetMapping("/rnd")
    public ResponseEntity<Question> getRandom() {
        int idx = random.nextInt((int) repo.count());
        Question q = repo.findAll().get(idx);
        Set<Activity> selectedActivities = new HashSet<>();

        if(q.getActivities() != null) {
            List<Activity> activities = new ArrayList<>(q.getActivities());

            for (int i = 0; i < QuestionType.getAmountOfActivities(q.getType()); i++) {
                int id = random.nextInt(activities.size());
                selectedActivities.add(activities.get(id));
                activities.remove(id);
            }
        }

        Question responseQuestion = new Question(q.getQuestionText(), selectedActivities, q.getType());

        return ResponseEntity.ok(responseQuestion);
    }

}
