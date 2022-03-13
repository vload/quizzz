package client.scenes;

import client.utils.ServerUtils;
import commons.Question;
import commons.QuestionType;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;

public class MyMainCtrl {

    private Stage primaryStage;

    private ServerUtils server;

    private MainScreenCtrl mainScreenCtrl;
    private Scene mainScreen;

    private NameScreenCtrl nameScreenCtrl;
    private Scene nameScreen;

    private SPEstimateQuestionCtrl spEstimateQuestionCtrl;
    private Scene spEstimateQuestionScreen;

    private SPMultipleChoiceQuestionCtrl spMultipleChoiceQuestionCtrl;
    private Scene spMCQuestionScreen;

    /**
     * Constructor for MyMainCtrl
     */
    public MyMainCtrl(){}

    /**
     * This method initializes the stage
     * @param primaryStage
     * @param server
     * @param mainScreen
     * @param nameScreen
     * @param spEstimateQuestionScreen
     * @param spMCQuestionScreen
     */
    public void initialize(Stage primaryStage,
                           ServerUtils server,
                           Pair<MainScreenCtrl, Parent> mainScreen,
                           Pair<NameScreenCtrl, Parent> nameScreen,
                           Pair<SPEstimateQuestionCtrl, Parent> spEstimateQuestionScreen,
                           Pair<SPMultipleChoiceQuestionCtrl, Parent> spMCQuestionScreen) {

        this.primaryStage = primaryStage;
        this.server = server;

        this.mainScreenCtrl = mainScreen.getKey();
        this.mainScreen = new Scene(mainScreen.getValue());
        setCSS(this.mainScreen, "ScreenCommonCSS.css");

        this.nameScreenCtrl = nameScreen.getKey();
        this.nameScreen = new Scene(nameScreen.getValue());
        setCSS(this.nameScreen, "ScreenCommonCSS.css");

        this.spEstimateQuestionCtrl = spEstimateQuestionScreen.getKey();
        this.spEstimateQuestionScreen = new Scene(spEstimateQuestionScreen.getValue());
        setCSS(this.spEstimateQuestionScreen, "QuestionCSS.css");

        this.spMultipleChoiceQuestionCtrl = spMCQuestionScreen.getKey();
        this.spMCQuestionScreen = new Scene(spMCQuestionScreen.getValue());
        setCSS(this.spMCQuestionScreen, "QuestionCSS.css");

        showMainScreen();
        primaryStage.show();
    }

    /**
     * This method shows the main screen
     */
    public void showMainScreen() {
        setScene(mainScreen, "Quizzz!");
    }

    /**
     * This method shows the name screen
     */
    public void showNameScreen(){
        setScene(nameScreen, "Enter your name");
    }

    /**
     * This method starts the game by getting a question and displaying it
     */
    public void startGame() {
        String id = server.createGame("Temp");
        //server.getQuestions();
        Question q = server.getQuestion();

        showQuestionScene(q);
    }

    /**
     * Sets the correct scene along with its CSS to the primaryStage
     * @param q the question to be displayed
     */
    public void showQuestionScene(Question q) {
        if (q.getType() == QuestionType.ESTIMATE) {
            setScene(spEstimateQuestionScreen, "EstimateScene");
            spEstimateQuestionCtrl.initialize(q);
        } else {
            setScene(spMCQuestionScreen, "MCScene");
            spMultipleChoiceQuestionCtrl.initialize(q);
        }
    }

    /**
     * This method displays the scene with the title
     * @param scene
     * @param title
     */
    private void setScene(Scene scene, String title) {
        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
    }

    /**
     * This method sets the CSS of a scene
     * @param scene
     * @param fileName with the .css
     */
    public void setCSS(Scene scene, String fileName) {
        scene.getStylesheets().add(Objects.requireNonNull(getClass()
                .getResource(fileName)).toExternalForm());
    }

}