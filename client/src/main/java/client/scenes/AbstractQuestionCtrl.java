package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

import java.util.Timer;
import java.util.TimerTask;

public abstract class AbstractQuestionCtrl {

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

    protected abstract void processAnswer(String answer);


    /**
     * Method that takes care of the UI timer functionality
     */
    public void timer() {
        mainTimer = new Timer();
        this.mainTimer.schedule(new TimerTask() {
            double progressTime = 9.999;
            int timer = 1000;
            int textTime = 10;

            @Override
            public void run() {
                timerBar.setProgress(progressTime / 10);
                progressTime = progressTime - 0.001;
                if (timer == 1000) {
                    timerText.setText(textTime + " s");
                    changeColor(textTime--);
                    timer = 0;
                }
                timer++;
                if (progressTime < 0) {
                    timerText.setText(0 + " s");
                    mainTimer.cancel();
                    timeOut();
                }
            }
        }, 0, 1);
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

    protected void showCorrectAnswerTimer(long score) {
        answerTimer = new Timer();
        this.answerTimer.schedule(new TimerTask() {
            double progressTime = 2.999;
            int timer = 1000;
            int textTime = 3;

            @Override
            public void run() {
                timerBar.setProgress(progressTime / 10);
                progressTime = progressTime - 0.001;
                if (timer == 1000) {
                    timerText.setText(textTime + " s");
                    changeColor(textTime--);
                    timer = 0;
                }
                timer++;
                if (progressTime < 0) {
                    goToNextScene(score);
                }
            }
        }, 0, 1);
    }

    protected abstract void goToNextScene(long score);


    protected void resetUI() {
        scoreText.setText(null);
        timerText.setText(null);
        timerBar.setProgress(10);
        questionText.setText(null);
    }

    /**
     * Method that submits the question to backend
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
}

