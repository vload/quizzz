package client.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javax.inject.Inject;


public class LeaderboardCtrl extends AbstractCtrl{

    public boolean endGameLeaderBoard;

    @FXML
    private ListView<String> leaderBoardList;

    @FXML
    private Label topText;

    @FXML
    private Label bottomText;

    @FXML
    private Button lobbyButton;

    @FXML
    private Button quitButton;

    private final MyMainCtrl myMainCtrl;


    /**
     * Adding checkstyle
     * @param myMainCtrl 
     */
    @Inject
    public LeaderboardCtrl(MyMainCtrl myMainCtrl) {
        this.myMainCtrl = myMainCtrl;
    }

    /**
     * Shows the main screen when you press on the quit button
     */
    public void showMainScreen(){
        myMainCtrl.showMainScreen();
    }


    /**
     * Shows the lobby screen when you press the lobby button
     */
    public void showLobbyScreen(){
    }


    /**
     * Show the right things on the screen if it is the endgame leaderboard or the midgame leaderboard
     * Fills the leaderboard
     */
    public void init(){
        endGameLeaderBoard = true;

        //if you want to show the endgame leaderboard
        if(endGameLeaderBoard) {
            topText.setText("End Game");
            bottomText.setOpacity(0.0d);
        } else { //if you want to show the midgame leaderboard
            topText.setText("Mid Game");
            bottomText.setText("Game Continues in 5 seconds");
            lobbyButton.setDisable(true);
            quitButton.setDisable(true);
            lobbyButton.setOpacity(0.0d);
            quitButton.setOpacity(0.0d);
        }

        ObservableList<String> players = FXCollections.observableArrayList(
                "335 points - Julia", "423 points - Ian", "123 points - Sue", "859 points - Matthew",
                "482 points - Hannah", "123 points - Stephan", "423 points - Denise", "674 points - Matt");
        leaderBoardList.setItems(players);
    }



}