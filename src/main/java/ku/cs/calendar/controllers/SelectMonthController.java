package ku.cs.calendar.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ku.cs.calendar.models.Calendar;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class SelectMonthController {

    private MainController mainCtrl;

    protected void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    @FXML
    private void selectMonth(ActionEvent e) {
        int m = Calendar.getMonthNum(((Button)e.getSource()).getText());
        mainCtrl.setShownMonth(m);
        mainCtrl.getMainPanel().getChildren().remove(mainCtrl.getSelectMonthPane());
        mainCtrl.getMainPanel().getChildren().add(mainCtrl.getSelectDatePane());
        mainCtrl.setDatesInMonth();
        mainCtrl.getMainBtn().setText(Calendar.getMonthName(mainCtrl.getShownMonth()) + ", " + mainCtrl.getShownYear());

        mainCtrl.clearDetailPane();
        mainCtrl.showAppointments();
    }
}
