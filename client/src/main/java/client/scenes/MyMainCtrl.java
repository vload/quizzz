package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MyMainCtrl {

    private Stage primaryStage;

    private MainScreenCtrl mainScreenCtrl;
    private Scene mainScreen;


    /**
     * Adding checkstyle
     * @param primaryStage
     * @param mainScreen
     */
    public void initialize(Stage primaryStage, Pair<MainScreenCtrl,
            Parent> mainScreen) {
        this.primaryStage = primaryStage;
        this.mainScreenCtrl = mainScreen.getKey();
        this.mainScreen = new Scene(mainScreen.getValue());

        //showOverview();
        showMainScreen();
        primaryStage.show();
    }

    /**
     * Adding checkstyle
     */
    public void showMainScreen() {
        primaryStage.setTitle("Quizzz!");
        primaryStage.setScene(mainScreen);
        //overviewCtrl.refresh();
        //mainScreenCtrl.refresh();
    }

}