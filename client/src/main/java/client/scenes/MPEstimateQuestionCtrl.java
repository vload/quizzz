package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import jakarta.ws.rs.BadRequestException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
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

    @FXML
    private ImageView image;

    @FXML
    private Text correctText;

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
     * Do not use, just from AbstractQuestionCtrl
     *
     * @param score
     */
    @Override
    protected void goToNextScene(long score) {
        return;
    }

    /**
     * Gets called upon init
     *
     * @param question
     * @param score
     * @param list
     * @param infoList
     */
    public void init(Question question, Long score, ObservableList<String> list, ObservableList<String> infoList) {
        jokerList = new ArrayList<>(Arrays.asList(jokerButton0, jokerButton1));
        init(score, list, infoList);
        alertText.setVisible(false);
        answerText.setFocusTraversable(false);
        associatedQuestion = question;
        questionText.setText(question.getQuestionText());
        var imageBytes = question.getActivitySet().iterator().next().image;
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        Image i = new Image(bis);
        image.setImage(i);
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
     * Method that transitions from the current question to the next one
     * @param score
     */
    @Override
    protected void goToNextScene(long score, ObservableList<String> list, ObservableList<String> infoList) {
        timerText.setText(0 + " s");
        answerText.setDisable(false);
        submitButton.setDisable(false);
        resetUI();
        Platform.runLater(() -> myMainCtrl.setNextMPQuestion(score, list, infoList));
        answerTimerTask.cancel();
    }

    @Override
    protected void goToLeaderboard(long score, ObservableList<String> list, ObservableList<String> infoList) {
        timerText.setText(0 + " s");
        answerText.setDisable(false);
        submitButton.setDisable(false);
        resetUI();
        Platform.runLater(() -> myMainCtrl.showMPhalfTimeLeaderboardScreen(score, list, infoList));
        answerTimerTask.cancel();
    }



    /**
     * Method that submits the question to backend
     */
    @Override
    public void timeOut() {
        answerText.setDisable(true);
        submitButton.setDisable(true);
        String correct = "Correct answer: " + associatedQuestion.getCorrectAnswer();
        correctText.setText(correct);
        correctText.setVisible(true);
        super.timeOut();
    }

    /**
     * Method that resets all the UI elements to their base state
     */
    @Override
    protected void resetUI() {
        super.resetUI();
        correctText.setVisible(false);
        answerText.clear();
        image.setImage(null);
    }

    @Override
    protected void setUpJokers() {
        int i = 0;
        jokerMap = new HashMap<>();
        for (JokerData joker : myMainCtrl.getJokerList()) {

            if (joker != null && joker.isSp() && joker.isEstimate()) {

                jokerMap.put("jokerButton" + i, joker);
                jokerList.get(i).setText(joker.getText());
                jokerList.get(i).setFocusTraversable(false);
                if (joker.isUsed()) {
                    jokerList.get(i).setDisable(true);
                }
                i++;
            }

        }
    }


    /**
     * Don't use, used for SP
     * @param score
     * @param list
     */
    @Override
    protected void goToNextScene(long score, ObservableList<String> list) {

    }
}