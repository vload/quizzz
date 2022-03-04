package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Objects;

public class MyMainCtrl {

    private Stage primaryStage;

    private MainScreenCtrl mainScreenCtrl;
    private Scene mainScreen;

    private NameScreenCtrl nameScreenCtrl;
    private Scene nameScreen;


    /**
     * This method initializes the stage
     * @param primaryStage
     * @param mainScreen
     * @param nameScreen
     */
    public void initialize(Stage primaryStage, Pair<MainScreenCtrl,
                           Parent> mainScreen, Pair<NameScreenCtrl,
                           Parent> nameScreen) {
        this.primaryStage = primaryStage;

        this.mainScreenCtrl = mainScreen.getKey();
        this.mainScreen = new Scene(mainScreen.getValue());
        this.mainScreen.getStylesheets().add(Objects.requireNonNull(getClass()
                       .getResource("MainScreenCSS.css")).toExternalForm());

        this.nameScreenCtrl = nameScreen.getKey();
        this.nameScreen = new Scene(nameScreen.getValue());
        this.nameScreen.getStylesheets().add(Objects.requireNonNull(getClass()
                       .getResource("NameScreenCSS.css")).toExternalForm());

        showMainScreen();
        primaryStage.show();
    }

    /**
     * This method shows the main screen
     */
    public void showMainScreen() {
        primaryStage.setTitle("Quizzz!");
        primaryStage.setScene(mainScreen);
    }

    /**
     * This method shows the name screen
     */
    public void showNameScreen(){
        primaryStage.setTitle("Enter your name");
        primaryStage.setScene(nameScreen);
    }

}