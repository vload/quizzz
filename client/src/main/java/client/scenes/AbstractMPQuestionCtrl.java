package client.scenes;

import client.utils.ServerUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.List;

public abstract class AbstractMPQuestionCtrl extends AbstractQuestionCtrl{

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
        informationBox.setItems(FXCollections.observableList(list));
    }

    /**
     * Method that displays a list of players in the player list
     * @param list
     */
    public void displayPlayers(List<String> list) {
        playerList.setItems(FXCollections.observableList(list));
    }
}
