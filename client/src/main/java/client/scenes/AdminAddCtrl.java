package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AdminAddCtrl extends AbstractCtrl{

    @FXML
    private TextField consumptionTextField;

    @FXML
    private TextField imageTextField;

    @FXML
    private Button saveButton;

    @FXML
    private Button backButton;

    @FXML
    private Button deleteButton;

    @FXML
    private TextField sourceTextField;

    @FXML
    private TextField titleTextField;

    private MyMainCtrl myMainCtrl;
    private ServerUtils server;

    /**
     * Constructor for AdminAddCtrl
     * @param myMainCtrl
     * @param server
     */
    @Inject
    public AdminAddCtrl(MyMainCtrl myMainCtrl, ServerUtils server) {
        this.myMainCtrl = myMainCtrl;
        this.server = server;
    }

    /**
     * Event handler for Back button click
     * @param event
     */
    @FXML
    void onBackClick(ActionEvent event) {

    }

    /**
     * Event handler for Save button click
     * @param event
     */
    @FXML
    void onSaveClick(ActionEvent event) {

    }

    /**
     * Event handler for Delete button click
     * @param event
     */
    @FXML
    void onDeleteClick(ActionEvent event) {

    }

}
