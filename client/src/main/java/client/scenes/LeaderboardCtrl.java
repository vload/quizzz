package client.scenes;

import commons.LeaderboardEntry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;


public class LeaderboardCtrl extends AbstractCtrl{

    @FXML
    private ListView<String> leaderboardList;


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
     * @param listEntry a list from the server with all the entries in order
     */
    public void init(List<LeaderboardEntry> listEntry){

        List<String> entryList = new ArrayList<String>();

        for(int i=0; i<listEntry.size(); i++){
            String entry = listEntry.get(i).getName() + " - " + listEntry.get(i).getScore() + " points";
            entryList.add(entry);
        }

        ObservableList<String> entryObservableList = FXCollections.observableList(entryList);
        leaderboardList.setItems(entryObservableList);

    }



}