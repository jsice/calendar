package ku.cs.calendar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ku.cs.calendar.controllers.MainController;
import ku.cs.calendar.models.Appointment;
import ku.cs.calendar.models.Date;

import java.lang.reflect.InvocationTargetException;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();
        mainController.init();
        primaryStage.setTitle("CalendarManager");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();

    }

    public static void main(String[] args ) {
        launch(args);
    }
}
