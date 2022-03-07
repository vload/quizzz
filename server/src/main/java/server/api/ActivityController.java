package server.api;

import commons.Activity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import server.database.ActivityRepository;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {
    private final ActivityRepository repo;

    /**
     * Adding checkstyle
     *
     * @param repo the question repo
     */
    public ActivityController(ActivityRepository repo) {
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
