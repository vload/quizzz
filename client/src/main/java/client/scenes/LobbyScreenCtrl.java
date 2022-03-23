package client.scenes;

import client.utils.ServerUtils;
import commons.PlayerData;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javax.inject.Inject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LobbyScreenCtrl extends AbstractCtrl implements Initializable {

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
     * Called to initialize a controller after its root element has been completely processed.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        server.registerForUpdates(l -> {
            List<PlayerData> dataList = l.getPlayerDataList();
            Platform.runLater(() -> playerList.getItems().clear());
            for (PlayerData p : dataList) {
                Platform.runLater(() -> playerList.getItems().add(p.getPlayerName()));
            }
        });
    }

    /**
     *
     */
    public void goBackToMainScreen(){
        server.disconnect(myMainCtrl.playerData);
        myMainCtrl.connected = false;
        myMainCtrl.showMainScreen();
    }

    /**
     * Stops the long polling
     */
    public void stop(){
        server.stop();
    }


}
