package server.database;


import commons.LeaderboardEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaderboardRepository extends JpaRepository<LeaderboardEntry, Long> {

    /**
     * returns all entries in the order of decreasing high-scores
     * @return A list of LeaderboardEntry ordered by their high-scores
     */
    List<LeaderboardEntry> findAllByOrderByScoreDesc();
    // the reason why this method name is so stupid is because
    // it's behaviour is dependent on keywords in the method signature.
    // JPA does all the work for us :D
}
