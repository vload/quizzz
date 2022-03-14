package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import com.google.inject.Stage;
import commons.Question;
import commons.Submission;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Arrays;


public class SPMultipleChoiceQuestionCtrl extends AbstractQuestionCtrl {

    MyMainCtrl mainCtrl;

    Stage primaryStage;

    @FXML
    private Button activityText1;

    @FXML
    private Button activityText2;

    @FXML
    private Button activityText3;

    @FXML
    private Button jokerText1;

    @FXML
    private Button jokerText2;

    private Question associatedQuestion;

    private ArrayList<Button> buttonList;

    /**
     * Constructor for SPMultipleChoiceQuestionCtrl
     * @param mainCtrl which is used for switching scenes
     * @param server that can communicate with backend
     */
    @Inject
    public SPMultipleChoiceQuestionCtrl(MainCtrl mainCtrl, ServerUtils server) {
        super(server);
        this.mainCtrl = new MyMainCtrl();
    }

    /**
     * Event handler for pressing an answer button
     * @param event
     */
    @FXML
    void answerPress(ActionEvent event) {
        Button source = (Button) event.getSource();
        int sourceLength = source.getId().length();
        String sourceId = String.valueOf(source.getId().charAt(sourceLength-1));

        Submission s = new Submission(source.getId(),timerBar.getProgress());
        server.validateQuestion(s);

        updateColors(buttonList, associatedQuestion.getCorrectAnswer());
        //mainCtrl.showQuestionScene(server.getQuestion());

    }

    /**
     *
     * @param buttonList
     * @param correctAnswer
     */
     public void updateColors(ArrayList<Button> buttonList, String correctAnswer) {
         for (Button b : buttonList) {
             String answer = b.getId();
             b.setDisable(true);
             if (answer.equals(correctAnswer)) {
                 b.getStyleClass().removeAll("questionButton");
                 b.getStyleClass().add("questionButtonCorrect");
             } else {
                 b.getStyleClass().removeAll("questionButton");
                 b.getStyleClass().add("questionButtonIncorrect");
             }
         }
     }

    /**
     * Event handler for pressing a joker button
     * @param event
     */
    @FXML
    void jokerPress(ActionEvent event) {

    }

    /**
     * Gets called upon init
     * @param question
     */
    public void initialize(Question question) {
        associatedQuestion = question;
        questionText.setText(question.getQuestionText());

        var activityIterator = question.getActivitySet().iterator();
        activityText1.setText(activityIterator.next().getTitle());
        activityText2.setText(activityIterator.next().getTitle());
        activityText3.setText(activityIterator.next().getTitle());

        activityIterator = question.getActivitySet().iterator();
        activityText1.setId(activityIterator.next().getId());
        activityText2.setId(activityIterator.next().getId());
        activityText3.setId(activityIterator.next().getId());

        buttonList = new ArrayList<>(Arrays.asList(activityText1, activityText2, activityText3));
        initialize();
    }

    /**
     *
     * @param oldString old string to be updated
     * @param score new score to be added
     * @return updated String containing added points
     */
    public String updateScoreString(String oldString, long score){
        String[] array = oldString.split(": ");
        long newScore = score + Long.parseLong(array[1]);
        return array[0] + ": " + newScore;
    }

}
