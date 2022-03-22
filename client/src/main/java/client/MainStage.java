package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import client.utils.ServerUtils;
import com.google.inject.Injector;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainStage extends Application {

    private static final Injector INJECTOR = createInjector(new MainModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     *
     * @param args
     * @throws URISyntaxException
     * @throws IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch(args);
    }

    /**
     *
     * @param primaryStage
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        var mainScreen = FXML.load(MainScreenCtrl.class, "client", "scenes", "MainScreen.fxml");
        var nameScreen = FXML.load(NameScreenCtrl.class, "client", "scenes", "NameScreen.fxml");
        var lobbyScreen = FXML.load(LobbyScreenCtrl.class, "client", "scenes", "LobbyScreen.fxml");
        var spEstimateQuestionScreen = FXML.load(SPEstimateQuestionCtrl.class,
                "client", "scenes", "SPEstimateQuestion.fxml");
        var spMCQuestionScreen = FXML.load(SPMultipleChoiceQuestionCtrl.class,
                "client", "scenes", "SPMultipleChoice.fxml");
        var mainCtrl = INJECTOR.getInstance(MyMainCtrl.class);
        var server = INJECTOR.getInstance(ServerUtils.class);
        mainCtrl.init(primaryStage, server, mainScreen, nameScreen, lobbyScreen, spEstimateQuestionScreen, spMCQuestionScreen);
    }




}