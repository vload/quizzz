package server.api;

import java.util.List;
import java.util.Random;

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
     * Gets a question with specified id
     *
     * @param id the id of the question
     * @return ResponseEntity with the question
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
     * Gets a random question from the database
     *
     * @return ResponseEntity with the question
     */
    @GetMapping("rnd")
    public ResponseEntity<Question> getRandom() {
        List<Question> questions = repo.findAll();
        var idx = random.nextInt((int) repo.count());
        return ResponseEntity.ok(questions.get(idx));
    }
}
