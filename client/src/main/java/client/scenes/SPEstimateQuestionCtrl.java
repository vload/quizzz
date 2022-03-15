package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import jakarta.ws.rs.BadRequestException;
import javafx.application.Platform;
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

    private final MyMainCtrl myMainCtrl;

    /**
     * Constructor for SPEstimateQuestionCtrl
     *
     * @param server     that can communicate with backend
     * @param myMainCtrl
     */
    @Inject
    public SPEstimateQuestionCtrl(ServerUtils server, MyMainCtrl myMainCtrl) {
        super(server);
        this.myMainCtrl = myMainCtrl;
    }

    /**
     * Event handler for typing in the textField
     *
     * @param event
     */
    @FXML
    void checkForEnter(KeyEvent event) {
        if (event.getCode().toString().equals("ENTER")) {
            try {
                answerText.setDisable(true);
                processAnswer(answerText.getText());

            } catch (BadRequestException e) {
                answerText.setDisable(false);
                myMainCtrl.showMainScreen();
            }
        }
    }

    protected void processAnswer(String answer) {
        long score = myMainCtrl.sendSubmission(answer, cancelTimer());
        this.scoreText.setText("Score: " + score);
        answerText.setText(associatedQuestion.getCorrectAnswer());
        showCorrectAnswerTimer(score);
    }

    /**
     * Method that submits the question to backend
     */
    @Override
    public void timeOut() {
        answerText.setDisable(true);
        answerText.setText(associatedQuestion.getCorrectAnswer());
        long score = myMainCtrl.sendSubmission("late", -1L);
        this.scoreText.setText("Score: " + score);
        showCorrectAnswerTimer(score);
    }

    protected void resetUI() {
        super.resetUI();
        answerText.clear();
    }


    protected void goToNextScene(long score) {
        timerText.setText(0 + " s");
        answerText.setDisable(false);
        resetUI();
        Platform.runLater(() -> myMainCtrl.setNextQuestion(score));
        answerTimer.cancel();
    }

    /**
     * Event handler for pressing a joker button
     *
     * @param event
     */
    @FXML
    void jokerPressed(ActionEvent event) {

    }

    /**
     * Gets called upon init
     *
     * @param question
     * @param score
     */
    public void init(Question question, Long score) {
        init(score);
        associatedQuestion = question;
        questionText.setText(question.getQuestionText());
        activityText.setText(question.getActivitySet().iterator().next().getTitle());
    }

}
