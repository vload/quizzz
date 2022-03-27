package server.api;

import commons.Activity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.ActivityRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class ActivityController {

    private final ActivityRepository repo;

    /**
     * Constructor for ActivityController
     * @param repo
     */
    public ActivityController(ActivityRepository repo){
        this.repo = repo;
    }

    /**
     * API endpoint for getting all the activities
     * @return null if empty, all the activities otherwise
     */
    @GetMapping(path="/activities") // "/api/admin/activities"
    public List<Activity> getAllActivities () {
        if (repo.count() == 0) {
            return new ArrayList<>();
        }
        return repo.findAll();
    }

    /**
     * API endpoint for adding/updating an activity
     * @param activity to put in the database
     * @return BadRequest if activity is null, the activity otherwise
     */
    @PostMapping(path="/addactivity") // "/api/admin/addactivity"
    public ResponseEntity<Activity> addActivity (@RequestBody Activity activity) {
        if (activity == null) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("[INFO] Adding activity, count: " + repo.count());
        repo.save(activity);
        System.out.println("[INFO] Added activity, count: " + repo.count());
        return ResponseEntity.ok(activity);
    }

    /**
     * API endpoint for deleting an activity
     * @param id of the activity to delete
     * @return BadRequest if activity doesn't exist, String otherwise
     */
    @GetMapping(path="/deleteactivity/{id}") // "/api/admin/deleteactivity/{id}"
    public ResponseEntity<String> deleteActivity (@PathVariable("id") String id) {
        if (repo.findById(id).isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        System.out.println("[INFO] Deleting activity, count: " + repo.count());
        repo.deleteById(id);
        System.out.println("[INFO] Deleted activity, count: " + repo.count());
        return ResponseEntity.ok("Activity " + id + " deleted");
    }
}
