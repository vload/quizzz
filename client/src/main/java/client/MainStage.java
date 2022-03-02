package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
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
        var mainCtrl = INJECTOR.getInstance(MyMainCtrl.class);
        mainCtrl.initialize(primaryStage, mainScreen);
    }
}