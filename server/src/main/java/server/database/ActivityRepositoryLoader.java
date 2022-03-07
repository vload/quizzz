package server.database;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import commons.Activity;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Configuration
public class ActivityRepositoryLoader {
    @Bean
    ApplicationRunner loadActivities(ActivityRepository repository) {
        return args -> {
            try {
                List<Activity> activities = (new ObjectMapper()).readValue(
                        new File("server\\src\\main\\resources\\activities.json"),
                        new TypeReference<List<Activity>>() {});
                repository.saveAll(activities);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        };
    }
}
