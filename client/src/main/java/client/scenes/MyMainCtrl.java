package client.scenes;

import client.utils.ServerUtils;
import commons.PlayerData;
import commons.JokerType;
import commons.Question;
import commons.QuestionType;
import commons.Submission;
import jakarta.ws.rs.BadRequestException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class MyMainCtrl extends AbstractCtrl {

    public Stage primaryStage;
    public String gameID;
    public PlayerData playerData;
    public boolean connected;

    private ServerUtils server;

    private HashMap<String, SceneCtrlPair> screenMap;
    private ArrayList<JokerData> jokerList;

    //JUST FOR TESTING
    private final List<String> l = new ArrayList<>();


    /**
     * Constructor for MyMainCtrl
     */
    public MyMainCtrl(){}

    /**
     * This method stores all the scenes and opens the main screen
     * @param primaryStage
     * @param server
     * @param mainScreen
     * @param mpNameScreen
     * @param spNameScreen
     * @param lobbyScreen
     * @param spEQScreen
     * @param spMCQScreen
     * @param mpEQScreen
     * @param mpMCQScreen
     * @param spSelectiveScreen
     * @param leaderboardScreen
     */
    public void init(Stage primaryStage,
                     ServerUtils server,
                     Pair<MainScreenCtrl, Parent> mainScreen,
                     Pair<NameScreenCtrl, Parent> mpNameScreen,
                     Pair<NameScreenCtrl, Parent> spNameScreen,
                     Pair<LobbyScreenCtrl, Parent> lobbyScreen,
                     Pair<SPEstimateQuestionCtrl, Parent> spEQScreen,
                     Pair<SPMultipleChoiceQuestionCtrl, Parent> spMCQScreen,
                     Pair<MPEstimateQuestionCtrl, Parent> mpEQScreen,
                     Pair<MPMultipleChoiceQuestionCtrl, Parent> mpMCQScreen,
                     Pair<SPSelectiveQuestionCtrl, Parent> spSelectiveScreen,
                     Pair<LeaderboardCtrl, Parent> leaderboardScreen) {

        this.primaryStage = primaryStage;
        this.server = server;

        screenMap = new HashMap<>();
        screenMap.put("mainScreen", new SceneCtrlPair(mainScreen.getValue(), mainScreen.getKey()));
        screenMap.put("mpNameScreen", new SceneCtrlPair(mpNameScreen.getValue(), mpNameScreen.getKey()));
        screenMap.put("spNameScreen", new SceneCtrlPair(spNameScreen.getValue(), spNameScreen.getKey()));
        screenMap.put("lobbyScreen", new SceneCtrlPair(lobbyScreen.getValue(), lobbyScreen.getKey()));
        screenMap.put("spEQScreen", new SceneCtrlPair(spEQScreen.getValue(), spEQScreen.getKey()));
        screenMap.put("spMCQScreen", new SceneCtrlPair(spMCQScreen.getValue(), spMCQScreen.getKey()));
        screenMap.put("mpEQScreen", new SceneCtrlPair(mpEQScreen.getValue(), mpEQScreen.getKey()));
        screenMap.put("mpMCQScreen", new SceneCtrlPair(mpMCQScreen.getValue(), mpMCQScreen.getKey()));
        screenMap.put("spSelectiveScreen", new SceneCtrlPair(spSelectiveScreen.getValue(), spSelectiveScreen.getKey()));
        screenMap.put("leaderboardScreen", new SceneCtrlPair(leaderboardScreen.getValue(), leaderboardScreen.getKey()));

        primaryStage.setOnCloseRequest(e -> {
            if(connected){
                server.disconnect(playerData);
            }
        });

        showUI();
    }

    private void showUI() {
        primaryStage.setScene(new Scene(screenMap.get("mainScreen").getScene()));
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
        showMainScreen();
        primaryStage.show();
    }

    /**
     * This method shows the main screen
     */
    public void showMainScreen() {
        setScene("mainScreen", "Quizzz!", "ScreenCommonCSS.css");
    }

    /**
     * This method shows the name screen
     */
    public void showSPNameScreen(){
        setScene("spNameScreen", "Enter your name", "ScreenCommonCSS.css");
    }

    /**
     * This method shows the name screen
     */
    public void showMPNameScreen(){
        setScene("mpNameScreen", "Enter your name", "ScreenCommonCSS.css");
    }

    /**
     * This method shows the name screen
     */
    public void showLobbyScreen(){
        setScene("lobbyScreen", "Multiplayer Lobby", "lobbyCSS.css");
    }

    /**
     * This method starts the game by getting a question and displaying it
     * @param name
     */
    public void startSPGame(String name) {
        if (name == null || name.length() == 0) {
            return;
        }
        gameID = server.createGame(name);
        setUpJokers(gameID);
        Question q = server.getQuestion(gameID);
        showQuestionScene(q, 0L);
    }

    /**
     * This method attempts to enter a player into a lobby
     * @param name
     * @return true if player can join with that name, false otherwise
     */
    public boolean startMPGame(String name) {
         playerData = new PlayerData(name);
        if(canStart(playerData)) {
            showLobbyScreen();
            connected = true;
            return true;

        } else{

            return false;
        }
    }

    /**
     *
     * @param data
     * @return true if the game can start with the specified name, false otherwise
     */
    public boolean canStart(PlayerData data){
        try {
            server.connect(data);
            return true;
        }catch (BadRequestException e){
            return false;
        }
    }

    /**
     * Method that sends the submission to the server
     * @param answer
     * @param time
     * @return score after updating
     */
    public long sendSubmission(String answer, double time) {
        Submission s = new Submission(answer, time);
        return server.validateQuestion(s, gameID);
    }

    /**
     * Gets the new question and sets the scene accordingly
     * @param score
     */
    public void setNextQuestion(long score) {
        try {
            Question newQuestion = server.getQuestion(gameID);
            if (newQuestion == null) {
                showLeaderboardScreen();
                return;
            }
            showQuestionScene(newQuestion, score);
        } catch (BadRequestException e) {
            System.out.println(e);
        }
    }

    /**
     * Sets the correct scene along with its CSS to the primaryStage
     * @param score
     * @param q the question to be displayed
     */
    public void showQuestionScene(Question q, Long score) {
        if (q.getType() == QuestionType.ESTIMATE) {
            setScene("spEQScreen", "EstimateScene", "QuestionCSS.css");
            var ctrl = (SPEstimateQuestionCtrl) screenMap.get("spEQScreen").getCtrl();
            ctrl.init(q, score);
        } else {
            if(q.getType() == QuestionType.MC){
                setScene("spMCQScreen", "MCScene", "QuestionCSS.css");
                var ctrl = (SPMultipleChoiceQuestionCtrl) screenMap.get("spMCQScreen").getCtrl();
                ctrl.init(q, score);
            }else{
                if(q.getType() == QuestionType.SELECTIVE){
                    setScene("spSelectiveScreen", "SelectiveScene", "QuestionCSS.css");
                    var ctrl = (SPSelectiveQuestionCtrl) screenMap.get("spSelectiveScreen").getCtrl();
                    ctrl.init(q, score);
                }
            }
        }
    }

    /**
     * Sets the scene
     * @param screen
     * @param title
     * @param cssFile
     */
    private void setScene(String screen, String title, String cssFile) {
        var scene = screenMap.get(screen).getScene();
        primaryStage.setTitle(title);
        primaryStage.getScene().setRoot(scene);
        setCSS(cssFile);
    }

    /**
     * This method sets the CSS of a scene
     * @param fileName with the .css
     */
    public void setCSS(String fileName) {
        primaryStage.getScene().getStylesheets().setAll(Objects.requireNonNull(getClass()
                .getResource("css/" + fileName)).toExternalForm());
    }

    /**
     * Getter for jokerList
     * @return jokerList
     */
    public ArrayList<JokerData> getJokerList() {
        return jokerList;
    }

    /**
     * Method that sends the joker to the server
     * @param joker the joker type to be sent
     * @return boolean allowed/forbidden
     */
    public boolean useJokerSingleplayer(JokerType joker) {
        if (joker == JokerType.REMOVE_WRONG_ANSWER) {
            return true;
        }
        return server.useJokerSingleplayer(Long.parseLong(gameID), joker);
    }

    /**
     * Sets up the game's jokers
     * @param gameID
     */
    public void setUpJokers(String gameID) {
        var jokers = server.getJokers(gameID);
        jokerList = new ArrayList<>();
        for (JokerType j : jokers) {
            String text;
            switch (j) {
                case DOUBLE_POINTS:
                    text = "x2";
                    jokerList.add(new JokerData(text, JokerType.DOUBLE_POINTS, false, true, true, true, true));
                    break;
                case REMOVE_WRONG_ANSWER:
                    text = "Remove";
                    jokerList.add(new JokerData(text, JokerType.REMOVE_WRONG_ANSWER, false, true, false, true, true));
                    break;
                case HALF_TIME:
                    text = "time/2";
                    jokerList.add(new JokerData(text, JokerType.HALF_TIME, false, true, true, false, true));
                    break;
            }
        }
    }

    /**
     *
     * @return primary stage
     */
    public Stage getPrimaryStage(){
        return primaryStage;
    }

    /**
     * shows leaderboard screen
     */
    public void showLeaderboardScreen(){
        setScene("leaderboardScreen", "Quizzz!", "LeaderboardCSS.css");
        var ctrl = (LeaderboardCtrl) screenMap.get("leaderboardScreen").getCtrl();
        ctrl.init();
    }

    /**
     * Sends the pressed emoji to the server
     * @param emoji
     * @param ctrl
     */
    public void sendEmoji(String emoji, AbstractMPQuestionCtrl ctrl) {
        l.add(emoji);
        ctrl.displayReactions(l);
    }

}