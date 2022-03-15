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

    protected Timer t;

    /**
     * Constructor for QuestionController
     * @param server that can communicate with backend
     */
    @Inject
    public AbstractQuestionCtrl(ServerUtils server) {
        this.server = server;
    }

    /**
     * Gets called upon init
     */
    public void init() {
        scoreText.setText("Score: 0");
        timerText.setText("10s");
        timerBar.setProgress(100);
        timer();
    }

    /**
     * @param score the score to be initialized with the question
     */
    public void initializeNext(Long score){
        scoreText.setText("Score: " + score);
        timerText.setText("10s");
        timerBar.setProgress(100);
        timerNext();
    }


    /**
     * Method that takes care of the UI timer functionality
     */
    public void timer() {
        this.t = new Timer();
        this.t.schedule(new TimerTask() {
            double progressTime = 9.999;
            int timer = 1000;
            int textTime = 10;
            @Override
            public void run() {
                timerBar.setProgress(progressTime/10);
                progressTime = progressTime - 0.001;
                if (timer == 1000) {
                    timerText.setText(textTime + " s");
                    changeColor(textTime);
                    textTime--;
                    timer = 0;
                }
                timer++;
                if (progressTime < 0) {
                    timerText.setText(0 + " s");
                    t.cancel();
                    submitQuestion(-1, 0);
                }
            }
        }, 0, 1);
    }

    /**
     *
     */
    public void timerNext() {
        t = new Timer();
        this.t.schedule(new TimerTask() {
            double progressTime = 9.999;
            int timer = 1000;
            int textTime = 10;
            @Override
            public void run() {
                timerBar.setProgress(progressTime/10);
                progressTime = progressTime - 0.001;
                if (timer == 1000) {
                    timerText.setText(textTime + " s");
                    changeColor(textTime);
                    textTime--;
                    timer = 0;
                }
                timer++;
                if (progressTime < 0) {
                    timerText.setText(0 + " s");
                    t.cancel();
                    submitQuestion(-1, 0);
                }
            }
        }, 0, 1);
    }

    /**
     *
     * @return the current timer value
     */
    public Double cancelTimer() {

        Double result = timerBar.getProgress();
        timerBar.setProgress(100);
        t.cancel();
        return result;
    }

    /**
     * Method that submits the question to backend
     * @param answer
     * @param time
     */
    public void submitQuestion(int answer, int time) {
        questionText.setText("Too late");
    }

    /**
     * Changes the color of the times according to textTime
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

