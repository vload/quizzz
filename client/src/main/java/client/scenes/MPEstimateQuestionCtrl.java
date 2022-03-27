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
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MPEstimateQuestionCtrl extends AbstractMPQuestionCtrl{

    @FXML
    private Button jokerButton0;

    @FXML
    private Button jokerButton1;

    @FXML
    private Text questionText;

    @FXML
    private Button activityText;

    @FXML
    private TextField answerText;

    @FXML
    private Button submitButton;

    @FXML
    private Text alertText;

    private Question associatedQuestion;


    /**
     * Constructor for QuestionController
     *
     * @param server   that can communicate with backend
     * @param mainCtrl
     */
    @Inject
    public MPEstimateQuestionCtrl(ServerUtils server, MyMainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Gets called upon init
     *
     * @param question
     * @param score
     */
    public void init(Question question, Long score) {
        jokerList = new ArrayList<>(Arrays.asList(jokerButton0, jokerButton1));
        init(score);
        alertText.setVisible(false);
        associatedQuestion = question;
        questionText.setText(question.getQuestionText());
        activityText.setText(question.getActivitySet().iterator().next().getTitle());
    }

    /**
     * Event handler for typing in the textField
     *
     * @param event
     */
    @FXML
    void checkForEnter(KeyEvent event) {
        alertText.setVisible(false);
        if (event.getCode().toString().equals("ENTER")) {
            validateEnter(answerText.getText());
        } else if (event.getCode().toString().equals("BACK_SPACE")){
            validateBackspace(answerText.getText());
        } else {
            validateEvent(event.getText());
        }
    }

    /**
     * Validates input after enter is pressed
     * @param answer
     */
    protected void validateEnter(String answer){
        try {
            answerText.setDisable(true);
            submitButton.setDisable(true);
            alertText.setVisible(false);
            Long.valueOf(answer);
            processAnswer(answer);
        } catch (BadRequestException e) {
            answerText.setDisable(false);
            submitButton.setDisable(false);
            myMainCtrl.showMainScreen();
        }catch (NumberFormatException e){
            answerText.setDisable(false);
            submitButton.setDisable(false);
            alertText.setVisible(true);
        }
    }

    /**
     * Validates input after a backspace
     * @param answer
     */
    protected void validateBackspace(String answer){
        try {
            if (answer.length() > 0) {
                Integer.parseInt(answer);
            }
        } catch (NumberFormatException e) {
            alertText.setVisible(true);
        }
    }

    /**
     * Validates input
     * @param eventText
     */
    protected void validateEvent(String eventText){
        try {
            Integer.parseInt(eventText);
            String answer = answerText.getText();
            if (answer.length() > 0) {
                Integer.parseInt(answer);
            }
        } catch (NumberFormatException e) {
            alertText.setVisible(true);
        }
    }

    /**
     * Event handler for clicking submit button
     *
     * @param event
     */
    @FXML
    void clickSubmit(ActionEvent event) {
        try {
            Long input = Long.valueOf(answerText.getText());
            answerText.setDisable(true);
            submitButton.setDisable(true);
            processAnswer(answerText.getText());
        }catch(NumberFormatException n){
            answerText.setDisable(false);
            submitButton.setDisable(false);
            alertText.setVisible(true);
        }catch (BadRequestException e) {
            answerText.setDisable(false);
            submitButton.setDisable(false);
            myMainCtrl.showMainScreen();
        }
    }

    /**
     * Method that sends the answer that the player presses to the server and acts accordingly
     * @param answer
     */
    @Override
    protected void processAnswer(String answer) {
        long score = myMainCtrl.sendSubmission(answer, cancelTimer());
        this.scoreText.setText("Score: " + score);
        answerText.setText(associatedQuestion.getCorrectAnswer());
        showCorrectAnswerTimer(score);
    }

    /**
     * Method that transitions from the current question to the next one
     * @param score
     */
    @Override
    protected void goToNextScene(long score) {
        timerText.setText(0 + " s");
        answerText.setDisable(false);
        submitButton.setDisable(false);
        resetUI();
        Platform.runLater(() -> myMainCtrl.setNextQuestion(score));
        answerTimerTask.cancel();
    }

    /**
     * Method that submits the question to backend
     */
    @Override
    public void timeOut() {
        answerText.setDisable(true);
        submitButton.setDisable(true);
        answerText.setText(associatedQuestion.getCorrectAnswer());
        long score = myMainCtrl.sendSubmission("late", -1);
        this.scoreText.setText("Score: " + score);
        showCorrectAnswerTimer(score);
    }

    /**
     * Method that resets all the UI elements to their base state
     */
    @Override
    protected void resetUI() {
        super.resetUI();
        answerText.clear();
    }

    @Override
    protected void setUpJokers() {
        int i = 0;
        jokerMap = new HashMap<>();
        for (JokerData joker : myMainCtrl.getJokerList()) {

            if (joker != null && joker.isSp() && joker.isEstimate()) {

                jokerMap.put("jokerButton" + i, joker);
                jokerList.get(i).setText(joker.getText());

                if (joker.isUsed()) {
                    jokerList.get(i).setDisable(true);
                }
                i++;
            }

        }
    }

}