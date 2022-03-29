package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import commons.Activity;
import jakarta.ws.rs.BadRequestException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;

public class AdminAddCtrl extends AbstractCtrl{

    @FXML
    private TextField idTextField;

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

    private String activityId;
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
     * Gets called every time the scene is displayed
     * @param activity
     */
    public void init(Activity activity) {
        this.activityId = activity.getId();
        idTextField.setText(activity.getId());
        consumptionTextField.setText(String.valueOf(activity.getEnergyConsumption()));
        sourceTextField.setText(activity.getSource());
        titleTextField.setText(activity.getTitle());
        imageTextField.setText("TO BE DONE");
        deleteButton.setDisable(activityId == null);
    }

    /**
     * Event handler for Back button click
     * @param ev
     */
    @FXML
    void onBackClick(ActionEvent ev) {
        myMainCtrl.showAdminScreen();
    }

    /**
     * Event handler for Save button click
     * @param ev
     */
    @FXML
    void onSaveClick(ActionEvent ev) {
        double c = -1;
        try {
            c = Double.parseDouble(consumptionTextField.getText());
        } catch (NumberFormatException e){
            System.out.println(e);
            return;
        }
        String id = idTextField.getText();
        String title = titleTextField.getText();
        String source = sourceTextField.getText();
        String image = imageTextField.getText();
        Activity activity = new Activity(id, image, title, c, source);
        try{
            if (!(activityId == null) && !id.equals(activityId)) {
                server.deleteActivity(activityId);
            }
            server.saveActivity(activity);
        } catch (BadRequestException e) {
            System.out.println("There was a problem with saving");
        }
        onBackClick(null);
    }

    /**
     * Event handler for Delete button click
     * @param ev
     */
    @FXML
    void onDeleteClick(ActionEvent ev) {
        try{
            server.deleteActivity(activityId);
            onBackClick(null);
        } catch (BadRequestException e) {
            System.out.println("There was a problem with deleting");
        }
    }

}
