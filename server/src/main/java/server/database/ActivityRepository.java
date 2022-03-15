package server.database;

import commons.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Random;

public interface ActivityRepository extends JpaRepository<Activity, String> {
    /**
     * Returns a random activity
     * @param random the random object
     * @return the activity
     *
     */
    default Activity getRandom(Random random){
        var all = findAll();

        return all.get(random.nextInt(all.size()));
    }
}