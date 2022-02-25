package server.api;

import java.util.List;
import java.util.Random;

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
     * @param random ?
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
        return ResponseEntity.ok(repo.getById(id));
    }
}
