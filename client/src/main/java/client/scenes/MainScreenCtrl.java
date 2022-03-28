/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client.scenes;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

import javax.inject.Inject;
import javafx.scene.control.TextField;

import java.net.ConnectException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenCtrl extends AbstractCtrl implements Initializable {

    private final MyMainCtrl myMainCtrl;

    @FXML
    private TextField ipAddressField;

    @FXML
    private Text ipWarningMessage;

    /**
     * Constructor for the name screen controller
     * @param myMainCtrl
     */
    @Inject
    public MainScreenCtrl(MyMainCtrl myMainCtrl){
        this.myMainCtrl = myMainCtrl;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ipWarningMessage.setVisible(false);
    }

    private void setIP() throws ConnectException {
        if (ipAddressField.getText().length() == 0) {
            myMainCtrl.setIP("http://localhost:8080/");
        } else {
            myMainCtrl.setIP(ipAddressField.getText());
        }
    }

    /**
     * Shows main screen
     */
    public void goToMPName() {
        try {
            setIP();
            myMainCtrl.showMPNameScreen();
            ipWarningMessage.setVisible(false);
        } catch (ConnectException e) {
            ipWarningMessage.setVisible(true);
        }
    }

    /**
     * Shows main screen
     */
    public void goToSPName() {
        try {
            setIP();
            myMainCtrl.showSPNameScreen();
            ipWarningMessage.setVisible(false);
        } catch (ConnectException e) {
            ipWarningMessage.setVisible(true);
        }
    }

    /**
     * Event handler for Admin button click
     */
    public void onAdminClick() {
        try {
            setIP();
            //action here
            ipWarningMessage.setVisible(false);
        } catch (ConnectException e) {
            ipWarningMessage.setVisible(true);
        }

    }

}