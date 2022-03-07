package server.api;

import commons.Activity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityRepository;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final Random random;
    private final ActivityRepository repo;

    /**
     * Adding checkstyle
     *
     * @param random random object
     * @param repo the question repo
     */
    public ActivityController(Random random, ActivityRepository repo) {
        this.random = random;
        this.repo = repo;
    }

    /**
     * Return all questions
     *
     * @return returns all questions
     */
    @GetMapping(path = { "", "/" })
    public List<Activity> getAll() {
        return repo.findAll();
    }
}
