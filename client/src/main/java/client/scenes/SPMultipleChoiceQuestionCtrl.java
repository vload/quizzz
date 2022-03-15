package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Question;
import jakarta.ws.rs.BadRequestException;
import javafx.application.Platform;
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

    private final MyMainCtrl myMainCtrl;

    /**
     * Constructor for SPMultipleChoiceQuestionCtrl
     * @param server that can communicate with backend
     * @param myMainCtrl
     */
    @Inject
    public SPMultipleChoiceQuestionCtrl(ServerUtils server, MyMainCtrl myMainCtrl) {
        super(server);
        this.myMainCtrl = myMainCtrl;
    }

    /**
     * Event handler for pressing an answer button
     * @param event
     */
    @FXML
    void answerPress(ActionEvent event) {
        try {
            Button source = (Button) event.getSource();
            updateColors(buttonList, associatedQuestion.getCorrectAnswer());
            processAnswer(source.getId());

        }catch (BadRequestException e){
            myMainCtrl.showMainScreen();
            enableButtons(buttonList);
            enableColors(buttonList);
        }
    }

    /**
     * Method that submits the question to backend
     */
    @Override
    public void timeOut() {
        updateColors(buttonList, associatedQuestion.getCorrectAnswer());
        long score = myMainCtrl.sendSubmission("late", cancelTimer());
        this.scoreText.setText("Score: " + score);
        showCorrectAnswerTimer(score);
    }

    protected void processAnswer(String answer) {
        long score = myMainCtrl.sendSubmission(answer, cancelTimer());
        this.scoreText.setText("Score: " + score);
        showCorrectAnswerTimer(score);
    }

    protected void goToNextScene(long score) {
        enableButtons(buttonList);
        enableColors(buttonList);
        resetUI();
        Platform.runLater(() -> myMainCtrl.setNextQuestion(score));
        answerTimer.cancel();
    }

    /**
     *
     * @param buttonList
     */
    public void enableColors(ArrayList<Button> buttonList){
        for (Button b : buttonList) {
            b.getStyleClass().removeAll("questionButtonCorrect");
            b.getStyleClass().removeAll("questionButtonIncorrect");
            b.getStyleClass().add("questionButton");
        }
    }

    /**
     *
     * @param buttonList
     */
    public void enableButtons(ArrayList<Button> buttonList){

        for (Button b : buttonList) {
            b.setDisable(false);
        }
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
     * @param score
     */
    public void init(Question question, Long score) {
        init(score);
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
    }

}
