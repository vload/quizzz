package client.scenes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javax.inject.Inject;


public class LeaderboardCtrl extends AbstractCtrl{

    @FXML
    private ListView<String> leaderBoardList;


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
     * Show the right things on the screen if it is the endgame leaderboard or the midgame leaderboard
     * Fills the leaderboard
     */
    public void init(){

        ObservableList<String> players = FXCollections.observableArrayList(
                "335 points - Julia", "423 points - Ian", "123 points - Sue", "859 points - Matthew",
                "482 points - Hannah", "123 points - Stephan", "423 points - Denise", "674 points - Matt");
        leaderBoardList.setItems(players);
    }



}