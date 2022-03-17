package server.api;

import java.util.*;

import commons.LeaderboardEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.LeaderboardRepository;

@RestController
@RequestMapping("/api/leaderboard")
public class LeaderboardController {

    private final LeaderboardRepository repo;

    /**
     * LeaderboardController constructor
     *
     * @param repo The repository containing the entries of the leaderboard
     */
    public LeaderboardController(LeaderboardRepository repo) {
        this.repo = repo;
    }

    /**
     * Returns every single LeaderboardEntry currently in repo
     * as a list (sorted in descending order)
     *
     * @return A list which contains LeaderBoardEntries, sorted
     * according to their score.
     */
    @GetMapping(path = {"","/"})
    public ResponseEntity<List<LeaderboardEntry>> getAllInOrder() {
        List<LeaderboardEntry> sortedList = repo.findAllByOrderByScoreDesc();
        return ResponseEntity.ok(sortedList);
    }

    /**
     * API endpoint to save entries to the DB
     * @param entry The entry to be saved.
     * @return A responseentity containing the entry if it was successful,
     * a bad request otherwise.
     */
    @PostMapping(path={"/add","/add/"})
    public ResponseEntity<LeaderboardEntry> add(
            @RequestBody LeaderboardEntry entry) {
        if (entry == null || isNullOrEmpty(entry.getName())) {
            return ResponseEntity.badRequest().build();
        }

        LeaderboardEntry saved = repo.save(entry);
        return ResponseEntity.ok(saved);
    }


    /**
     * Checks if a string is null or empty
     *
     * @param s string to be checked for conditions
     * @return true iff, it's s is not null and not empty, false otherwise
     */
    private static boolean isNullOrEmpty(String s) {
        return s == null || s.isEmpty();
    }
}
