package client.scenes;

import client.utils.ServerUtils;
import commons.Submission;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractMPQuestionCtrl extends AbstractQuestionCtrl{

    protected Submission submission;

    @FXML
    private Button rButton0;

    @FXML
    private Button rButton1;

    @FXML
    private Button rButton2;

    @FXML
    private Button rButton3;

    @FXML
    private Button rButton4;

    @FXML
    private Button rButton5;

    @FXML
    private Button rButton6;

    @FXML
    private Button rButton7;

    @FXML
    private Button rButton8;

    @FXML
    private Button rButton9;

    @FXML
    private Button rButton10;

    @FXML
    private Button rButton11;

    @FXML
    private ListView<String> informationBox;

    @FXML
    private ListView<String> playerList;

    /**
     * Constructor for QuestionController
     *
     * @param server   that can communicate with backend
     * @param mainCtrl
     */
    public AbstractMPQuestionCtrl(ServerUtils server, MyMainCtrl mainCtrl) {
        super(server, mainCtrl);
    }

    /**
     * Gets called every time a question screen is shown
     * @param score
     * @param list
     * @param infoList
     */
    public void init(Long score, ObservableList<String> list, ObservableList<String> infoList) {
        super.init(score);
        playerList.setItems(list);
        informationBox.setItems(infoList);
    }

    /**
     * Event handler for a reaction button press
     * @param event
     */
    @FXML
    protected void pressReaction(ActionEvent event) {
        Button b = (Button) event.getSource();
        String emoji = b.getText();
        myMainCtrl.sendEmoji(emoji, this);
    }

    /**
     * Method that displays a list of messages in the information box
     * @param list
     */
    public void displayReactions(List<String> list) {
        LinkedList<String> l = new LinkedList<>();
        for (String player : list) {
            l.addFirst(player);
        }
        informationBox.setItems(FXCollections.observableList(l));
    }

    /**
     * Method that takes care of the UI timer functionality
     */
    @Override
    public void timer() {
        mainTimer = new Timer();
        mainTimerTask = new TimerTask() {
            double progressTime = 9.99;
            int timer = 100;
            int textTime = 10;

            @Override
            public void run() {
                Platform.runLater(() -> timerBar.setProgress(progressTime / 10));
                progressTime = progressTime - 0.01;
                if (timer == 100) {
                    Platform.runLater(() -> timerText.setText(textTime + " s"));
                    Platform.runLater(() -> changeColor(--textTime));
                    timer = 0;
                }
                timer++;
                if (progressTime < 0) {
                    Platform.runLater(() -> timerText.setText(0 + " s"));
                    this.cancel();
                    Platform.runLater(() -> timeOut());
                }
            }
        };
        this.mainTimer.schedule(mainTimerTask, 0, 10);
    }

    /**
     * Method that shows the 3s timer while the correct answer is being displayed
     * @param score
     */
    protected void showCorrectAnswerTimer(long score) {
        enableJokers(false);
        answerTimer = new Timer();
        answerTimerTask = new TimerTask() {
            double progressTime = 4.99;
            int timer = 100;
            int textTime = 5;
            boolean scoresShown = false;

            @Override
            public void run() {
                Platform.runLater(() -> timerBar.setProgress(progressTime / 10));
                progressTime = progressTime - 0.01;
                if (timer == 100) {
                    Platform.runLater(() -> timerText.setText(textTime + " s"));
                    Platform.runLater(() -> changeColor(--textTime));
                    timer = 0;
                }
                timer++;
                if (progressTime > 4 && progressTime < 4.5 && !scoresShown) {
                    scoresShown = true;
                    Platform.runLater(() -> updatePlayerList());
                } else if (progressTime < 0) {
                    this.cancel();
                    Platform.runLater(() -> goToNextScene(score, playerList.getItems(), informationBox.getItems()));
                }
            }
        };
        this.answerTimer.schedule(answerTimerTask, 0, 10);
    }

    protected abstract void goToNextScene(long score, ObservableList<String> items, ObservableList<String> items1);

    /**
     * Method that sends the answer that the player presses to the server and acts accordingly
     * @param answer
     */
    protected void processAnswer(String answer) {
        submission = new Submission(answer, timerBar.getProgress());
    }

    /**
     * Gets called when timer runs out
     */
    public void timeOut() {
        long score = 0;
        if (submission != null) {
            score = myMainCtrl.sendMPSubmission(submission);
            submission = null;
        } else {
            score = myMainCtrl.sendMPSubmission(new Submission("late", -1));
        }
        this.scoreText.setText("Score: " + score);
        showCorrectAnswerTimer(score);
    }

    protected void updatePlayerList() {
        var list = myMainCtrl.getPlayerScores();
        playerList.setItems(FXCollections.observableList(list));
    }

    protected abstract void goToNextScene(long score, ObservableList<String> list);
}
