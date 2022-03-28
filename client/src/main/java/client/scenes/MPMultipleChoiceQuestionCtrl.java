package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.JokerType;
import commons.Question;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MPMultipleChoiceQuestionCtrl extends AbstractMPQuestionCtrl {

    @FXML
    private Button activityText1;

    @FXML
    private Button activityText2;

    @FXML
    private Button activityText3;

    @FXML
    private Button jokerButton0;

    @FXML
    private Button jokerButton1;

    private Question associatedQuestion;
    private ArrayList<Button> buttonList;

    /**
     * Constructor for QuestionController
     *
     * @param server   that can communicate with backend
     * @param myMainCtrl
     */
    @Inject
    public MPMultipleChoiceQuestionCtrl(ServerUtils server, MyMainCtrl myMainCtrl) {
        super(server, myMainCtrl);
    }

    /**
     * Gets called upon init
     * @param question
     * @param score
     * @param list
     */
    public void init(Question question, Long score, ObservableList<String> list) {
        buttonList = new ArrayList<>(Arrays.asList(activityText1, activityText2, activityText3));
        jokerList = new ArrayList<>(Arrays.asList(jokerButton0, jokerButton1));
        init(score, list);
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

        activityText1.setFocusTraversable(false);
        activityText2.setFocusTraversable(false);
        activityText3.setFocusTraversable(false);
    }

    /**
     * Event handler for pressing an answer button
     * @param event
     */
    @FXML
    void answerPress(ActionEvent event) {
        Button source = (Button) event.getSource();
        processAnswer(source.getId());
        enableButtons(false);
    }

    /**
     * Method that transitions from the current question to the next one
     * @param score
     */
    @Override
    protected void goToNextScene(long score) {

    }

    /**
     * Method that submits an empty answer with time 0 to the server
     */
    @Override
    public void timeOut() {
        updateColors(buttonList, associatedQuestion.getCorrectAnswer());
        super.timeOut();
    }

    @Override
    protected void goToNextScene(long score, ObservableList<String> list) {
        enableButtons(true);
        enableColors(buttonList);
        resetUI();
        Platform.runLater(() -> myMainCtrl.setNextMPQuestion(score, list));
        answerTimerTask.cancel();
    }

    /**
     * Method that returns the button colors back to normal
     * @param buttonList
     */
    public void enableColors(ArrayList<Button> buttonList){
        for (Button b : buttonList) {
            b.getStyleClass().removeAll("questionButtonCorrect");
            b.getStyleClass().removeAll("questionButtonIncorrect");
        }
    }

    /**
     * Method that enables the answer buttons
     * @param flag
     */
    public void enableButtons(boolean flag){

        for (Button b : buttonList) {
            b.setDisable(!flag);
        }
    }

    /**
     *  Method that shows the incorrect and correct answers
     * @param buttonList
     * @param correctAnswer
     */
    public void updateColors(ArrayList<Button> buttonList, String correctAnswer) {
        for (Button b : buttonList) {
            String answer = b.getId();
            b.setDisable(true);
            if (answer.equals(correctAnswer)) {
                b.getStyleClass().add("questionButtonCorrect");
            } else {
                b.getStyleClass().add("questionButtonIncorrect");
            }
        }
    }

    @Override
    protected void setUpJokers() {
        int i = 0;
        jokerMap = new HashMap<>();
        for (JokerData joker : myMainCtrl.getJokerList()) {
            if (joker != null && joker.isSp() && joker.isMc()) {
                jokerMap.put("jokerButton" + i, joker);
                jokerList.get(i).setDisable(joker.isUsed());
                jokerList.get(i).setText(joker.getText());
                jokerList.get(i).setFocusTraversable(false);

                i++;
            }
        }
    }

    @Override
    protected JokerType jokerPress(ActionEvent event) {
        JokerType type = super.jokerPress(event);

        if (type == JokerType.REMOVE_WRONG_ANSWER) {
            for (Button b : buttonList) {
                String answer = b.getId();
                if (!answer.equals(associatedQuestion.getCorrectAnswer())) {
                    b.getStyleClass().add("questionButtonIncorrect");
                    b.setDisable(true);
                    return type;
                }
            }
        }
        return type;
    }
}
