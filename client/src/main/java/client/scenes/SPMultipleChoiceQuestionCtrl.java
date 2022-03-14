package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    public void init(Question question) {
        questionText.setText(question.getQuestionText());
        var activityIterator = question.getActivities().iterator();
        activityText1.setText(activityIterator.next().getTitle());
        activityText2.setText(activityIterator.next().getTitle());
        activityText3.setText(activityIterator.next().getTitle());
        init();
    }

}
