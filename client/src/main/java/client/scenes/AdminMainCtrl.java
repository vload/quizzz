package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

public class AdminMainCtrl extends AbstractCtrl{

    @FXML
    private ListView<String> activitiesListView;

    @FXML
    private Button addButton;

    private MyMainCtrl myMainCtrl;
    private ServerUtils server;

    /**
     * Constructor for AdminMainCtrl
     * @param myMainCtrl
     * @param server
     */
    @Inject
    public AdminMainCtrl(MyMainCtrl myMainCtrl, ServerUtils server) {
        this.myMainCtrl = myMainCtrl;
        this.server = server;
    }

    /**
     * Event handler for Add button click
     * @param event
     */
    @FXML
    void onAddClick(ActionEvent event) {

    }

    /**
     * Event handler for Back button click
     * @param event
     */
    @FXML
    void onBackClick(MouseEvent event) {

    }

}

