package server.api;

import java.util.*;

import commons.Activity;
import commons.Question;
import commons.QuestionType;
import org.springframework.stereotype.Component;
import server.database.ActivityRepository;

@Component
public class QuestionGenerator {
    private final ActivityRepository activityRepository;
    private final Random random;

    /**
     * constructor for QuestionGenerator
     * @param activityRepository the repository of activities
     * @param random a random object
     */
    public QuestionGenerator(ActivityRepository activityRepository, Random random){
        this.activityRepository = activityRepository;
        this.random = random;
    }

    /**
     * generates one question
     * @return the generated question
     */
    public Question generateQuestion(){
        if(random.nextInt(2) == 0) {
            return generateEstimateQuestion();
        } else {
            return generateMCQuestion();
        }
    }

    private Set<Activity> generateActivitySet(QuestionType type){
        Set<Activity> result = new HashSet<>();

        while(result.size() < QuestionType.getAmountOfActivities(type)){
            var randomActivity = activityRepository.getRandom(random);
            if (randomActivity == null) {
                return null;
            }
            result.add(randomActivity);
        }

        return result;
    }

    private Question generateMCQuestion(){
        Set<Activity> activitySet = generateActivitySet(QuestionType.MC);
        if (activitySet == null) {
            return null;
        }
        String correctAnswer = "";
        String questionText;
        if(random.nextInt(2) == 0) {
            questionText = "Which one of the following consumes the least energy?";
            double minimumConsumption = Double.MAX_VALUE;
            for(var activity: activitySet){
                if(activity.getEnergyConsumption() < minimumConsumption){
                    correctAnswer = activity.getId();
                    minimumConsumption = activity.getEnergyConsumption();
                }
            }
        } else {
            questionText = "Which one of the following consumes the most energy?";
            double maximumConsumption = Double.MIN_VALUE;
            for(var activity: activitySet){
                if(activity.getEnergyConsumption() > maximumConsumption){
                    correctAnswer = activity.getId();
                    maximumConsumption = activity.getEnergyConsumption();
                }
            }
        }
        return new Question(questionText,activitySet,QuestionType.MC, correctAnswer);
    }

    private Question generateEstimateQuestion(){
        Set<Activity> activitySet = generateActivitySet(QuestionType.ESTIMATE);
        if (activitySet == null) {
            return null;
        }

        var activity = activitySet.iterator().next();
        String questionText = "How many watt-hours of energy does " + activity.getTitle() + " consume?";

        return new Question(questionText, activitySet, QuestionType.ESTIMATE,
                Double.toString(activity.getEnergyConsumption()));

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
