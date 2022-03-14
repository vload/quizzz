package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class SPEstimateQuestionCtrl extends AbstractQuestionCtrl {

    @FXML
    private Button activityText;

    @FXML
    private TextField answerText;

    @FXML
    private Button jokerText;

    /**
     * Constructor for SPEstimateQuestionCtrl
     * @param server that can communicate with backend
     */
    @Inject
    public SPEstimateQuestionCtrl(ServerUtils server) {
        super(server);
    }

    /**
     * Event handler for typing in the textField
     * @param event
     */
    @FXML
    void checkForEnter(KeyEvent event) {

    }

    /**
     * Event handler for pressing a joker button
     * @param event
     */
    @FXML
    void jokerPressed(ActionEvent event) {

    }

    /**
     * Gets called upon init
     * @param question
     */
    public void init(Question question) {
        init();
        questionText.setText(question.getQuestionText());
        activityText.setText(question.getActivities().iterator().next().getTitle());
    }

}
