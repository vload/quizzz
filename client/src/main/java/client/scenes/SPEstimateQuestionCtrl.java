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
import java.util.Timer;

public class SPEstimateQuestionCtrl extends AbstractQuestionCtrl {

    private Timer alertTimer;

    @FXML
    private Button activityText;

    @FXML
    private TextField answerText;

    @FXML
    private Button jokerText;

    @FXML
    private Button submitButton;

    @FXML
    private Text alertText;

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
     * Gets called upon init
     *
     * @param question
     * @param score
     */
    public void init(Question question, Long score) {
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
        answerText.textProperty().addListener((observable, oldValue, newValue) -> {
            answerText.setText(newValue);
            if (event.getCode().toString().equals("ENTER")) {
                try {
                    alertText.setVisible(false);
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
            }else{
                validateText(answerText.getText());
            }
        });


    }

    /**
     * Validates text once a user enters a new character
     * @param answer
     */
    protected void validateText(String answer){
        alertText.setVisible(false);
        try{
            if(answer == ""){
                alertText.setVisible(false);
                return;
            }
            Long input = Long.valueOf(answer);
        }catch (NumberFormatException e){
            alertText.setVisible(true);
            answerText.setDisable(false);
            submitButton.setDisable(false);
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

            } catch (BadRequestException e) {
                answerText.setDisable(false);
                submitButton.setDisable(false);
                myMainCtrl.showMainScreen();
            }catch(NumberFormatException n){
                answerText.setDisable(false);
                submitButton.setDisable(false);
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
        answerTimer.cancel();
    }


    /**
     * Method that submits the question to backend
     */
    @Override
    public void timeOut() {
        answerText.setDisable(true);
        submitButton.setDisable(true);
        answerText.setText(associatedQuestion.getCorrectAnswer());
        long score = myMainCtrl.sendSubmission("late", -1L);
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

    /**
     * Event handler for pressing a joker button
     *
     * @param event
     */
    @FXML
    void jokerPressed(ActionEvent event) {

    }


    /**
     * A method that hides the alert message text
     */
    protected void hideAlert(){
        alertText.setVisible(false);
    }


//    /**
//     * Method that displays the warning message for 2 seconds after inputting incorrect answer type
//     */
//    protected void showAlertMessage() {
//        alertText.setVisible(false);
//        alertTimer = new Timer();
//        alertTimer.schedule(new TimerTask() {
//            double progressTime = 1.99;
//
//            @Override
//            public void run() {
//                alertText.setVisible(true);
//                progressTime = progressTime - 0.01;
//                if (progressTime < 0) {
//                    Platform.runLater(() -> hideAlert());
//                    alertTimer.cancel();
//                }
//            }
//        }, 0, 10);
//    }



}
