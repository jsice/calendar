package ku.cs.calendar.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ku.cs.calendar.client.controllers.MainController;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.init();
        primaryStage.setTitle("CalendarService");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

    }

    public static void main(String[] args ) {
        launch(args);
    }
}
