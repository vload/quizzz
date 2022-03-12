package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Arrays;


public class SPMultipleChoiceQuestionCtrl extends AbstractQuestionCtrl {

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
     * @param server that can communicate with backend
     */
    @Inject
    public SPMultipleChoiceQuestionCtrl(ServerUtils server) {
        super(server);
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

        for (Button b: buttonList) {
            String id = String.valueOf(b.getId().charAt(sourceLength-1));
            b.setDisable(true);
            if(id.equals(associatedQuestion.getCorrectAnswer())){
                b.getStyleClass().removeAll("questionButton");
                b.getStyleClass().add("questionButtonCorrect");
            }else{
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
        var activityIterator = question.getActivities().iterator();
        activityText1.setText(activityIterator.next().getTitle());
        activityText2.setText(activityIterator.next().getTitle());
        activityText3.setText(activityIterator.next().getTitle());
        buttonList = new ArrayList<>(Arrays.asList(activityText1, activityText2, activityText3));
        initialize();
    }

}
