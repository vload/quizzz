package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractQuestionCtrl extends AbstractCtrl {

    @FXML
    protected Text questionText;

    @FXML
    protected Text scoreText;

    @FXML
    protected ProgressBar timerBar;

    @FXML
    protected Text timerText;

    protected final ServerUtils server;
    protected Timer mainTimer;
    protected Timer answerTimer;

    /**
     * Constructor for QuestionController
     *
     * @param server that can communicate with backend
     */
    @Inject
    public AbstractQuestionCtrl(ServerUtils server) {
        this.server = server;
    }

    /**
     * Gets called upon init
     *
     * @param score the score to be initialized with the question
     */
    public void init(Long score) {
        resetUI();
        scoreText.setText("Score: " + score);
        timerText.setText("10s");
        timerBar.setProgress(10);
        timer();
    }

    /**
     * Method that takes care of the UI timer functionality
     */
    public void timer() {
        mainTimer = new Timer();
        this.mainTimer.schedule(new TimerTask() {
            double progressTime = 9.99;
            int timer = 100;
            int textTime = 11;

            @Override
            public void run() {
                Platform.runLater(() -> timerBar.setProgress(progressTime / 10));
                progressTime = progressTime - 0.01;
                if (timer == 100) {
                    Platform.runLater(() -> timerText.setText(textTime + " s"));
                    changeColor(--textTime);
                    timer = 0;
                }
                timer++;
                if (progressTime < 0) {
                    Platform.runLater(() -> timerText.setText(0 + " s"));
                    mainTimer.cancel();
                    Platform.runLater(() -> timeOut());
                }
            }
        }, 0, 10);
    }

    /**
     * Cancels the current timer and returns the progress of the current question
     *
     * @return the current timer value
     */
    public Double cancelTimer() {
        Double result = timerBar.getProgress();
        timerBar.setProgress(10);
        mainTimer.cancel();
        return result;
    }

    /**
     * Method that shows the 3s timer while the correct answer is being displayed
     * @param score
     */
    protected void showCorrectAnswerTimer(long score) {
        answerTimer = new Timer();
        this.answerTimer.schedule(new TimerTask() {
            double progressTime = 2.99;
            int timer = 100;
            int textTime = 4;

            @Override
            public void run() {
                Platform.runLater(() -> timerBar.setProgress(progressTime / 10));
                progressTime = progressTime - 0.01;
                if (timer == 100) {
                    Platform.runLater(() -> timerText.setText(textTime + " s"));
                    changeColor(--textTime);
                    timer = 0;
                }
                timer++;
                if (progressTime < 0) {
                    Platform.runLater(() -> goToNextScene(score));
                }
            }
        }, 0, 10);
    }

    /**
     * Method that sends the answer that the player presses to the server and acts accordingly
     * @param answer
     */
    protected abstract void processAnswer(String answer);

    /**
     * Method that transitions from the current question to the next one
     * @param score
     */
    protected abstract void goToNextScene(long score);

    /**
     * Method that submits an empty answer with time 0 to the server
     */
    public abstract void timeOut();

    /**
     * Changes the color of the times according to textTime
     *
     * @param textTime
     */
    public void changeColor(int textTime) {
        switch (textTime) {
            case 10:
                timerBar.setStyle("-fx-accent: #4D8C57");
                break;
            case 7:
                timerBar.setStyle("-fx-accent: #A3B56B");
                break;
            case 5:
                timerBar.setStyle("-fx-accent: #FD9A01");
                break;
            case 3:
                timerBar.setStyle("-fx-accent: #FD6104");
                break;
            case 2:
                timerBar.setStyle("-fx-accent: #FF2C05");
                break;
            case 1:
                timerBar.setStyle("-fx-accent: #F00505");
                break;
        }
    }

    /**
     * Method that resets all the UI elements to their base state
     */
    protected void resetUI() {
        scoreText.setText(null);
        timerText.setText(null);
        timerBar.setProgress(10);
        questionText.setText(null);
    }
}

