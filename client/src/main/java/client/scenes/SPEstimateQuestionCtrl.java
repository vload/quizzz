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

    private Question associatedQuestion;
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
        if(event.getCode().toString().equals("ENTER")){
            try{
                answerText.setDisable(true);
                String answer = answerText.getText();
                Double timeLeft = timerBar.getProgress();
                long pointsObtained = associatedQuestion.getScore(answer, timeLeft);

                this.scoreText.setText(updateScoreString(scoreText.getText(),pointsObtained));

            }   catch(NumberFormatException e){
                    throw new NumberFormatException();
                }
        }
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
    public void initialize(Question question) {
        initialize();
        associatedQuestion = question;
        questionText.setText(question.getQuestionText());
        activityText.setText(question.getActivitySet().iterator().next().getTitle());
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
