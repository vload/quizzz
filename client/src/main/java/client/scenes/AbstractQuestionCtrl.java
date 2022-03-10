package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

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
    public void initialize() {
        scoreText.setText("Score: 0");
        timerText.setText("10s");
        timerBar.setProgress(100);
    }

}

