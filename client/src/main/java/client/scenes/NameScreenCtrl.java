package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class NameScreenCtrl extends AbstractCtrl{

    private final MyMainCtrl myMainCtrl;

    @FXML
    private TextField nameField;

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
     * Clears the name input field, which is temporarily used for demonstrational purposes
     */
    public void startGame(){


        myMainCtrl.startGame(nameField.getText());
    }


}
