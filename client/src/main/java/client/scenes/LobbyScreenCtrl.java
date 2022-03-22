package client.scenes;

import client.utils.ServerUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javax.inject.Inject;

public class LobbyScreenCtrl extends AbstractCtrl{

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> playerList;

    @FXML
    private Button startButton1;

    private final MyMainCtrl myMainCtrl;
    private final ServerUtils server;

    /**
     * Constructor for the lobby screen controller
     * @param myMainCtrl
     * @param server
     */
    @Inject
    public LobbyScreenCtrl(MyMainCtrl myMainCtrl, ServerUtils server) {
        this.myMainCtrl = myMainCtrl;
        this.server = server;
    }

    /**
     *
     */
    public void updateListView(){

    }


    /**
     *
     */
    public void goBackToMainScreen(){
        server.disconnect(myMainCtrl.playerData);
        myMainCtrl.showMainScreen();
    }

}
