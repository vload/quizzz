package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NameScreenCtrl extends AbstractCtrl{

    private final MyMainCtrl myMainCtrl;

    @FXML
    private TextField SPnameField;

    @FXML
    private TextField MPnameField;

    /**
     * Constructor for the name screen controller
     * @param myMainCtrl
     */
    @Inject
    public NameScreenCtrl(MyMainCtrl myMainCtrl){
        this.myMainCtrl = myMainCtrl;
    }

    /**
     * Shows main screen
     */
    public void back() {
        myMainCtrl.showMainScreen();
    }

    /**
     * Attempts to start a singleplayer game
     */
    public void startSPGame(){
        myMainCtrl.startSPGame(SPnameField.getText());
    }

    /**
     * Attempts to start a multiplayer game
     */
    public void startMPGame() {
        myMainCtrl.startMPGame(MPnameField.getText());
    }


}
