package server.api;

import java.util.ArrayList;
import java.util.List;

import commons.Activity;
import commons.Question;
import commons.QuestionType;
import org.springframework.stereotype.Component;
import server.database.ActivityRepository;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class QuestionGenerator {
    private final ActivityRepository activityRepository;

    /**
     * constructor for QuestionGenerator
     * @param activityRepository the repository of activities
     */
    public QuestionGenerator(ActivityRepository activityRepository){
        this.activityRepository = activityRepository;
    }

    /**
     * generates one question
     * @return the generated question
     */
    public Question generateQuestion(){
        Activity a1 = new Activity("02-shower", "/shower.png","Shower", 10.2,"example.com");
        Activity a2 = new Activity("03-heater", "/heater.png","Heater", 30.2,"example.com");
        Activity a3 = new Activity("05-flamethrower", "/flamethrower.png","Flamethrower", 99.3,"example.com");

        return new Question("Placeholder", Stream.of(a1,a2,a3).collect(Collectors.toSet()),
                QuestionType.MC, a1.getId());
    }

    /**
     * generates 20 questions
     * @return the generated questions in a list
     */
    public List<Question> generate20Questions(){
        List<Question> result = new ArrayList<>();

        for(int i = 0; i < 20; i++) {
            result.add(generateQuestion());
        }

        return result;
    }
}
