package ku.cs.calendar.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ku.cs.calendar.common.utils.CalendarUtils;

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
        int m = CalendarUtils.getMonthNum(((Button)e.getSource()).getText());
        mainCtrl.setShownMonth(m);
        mainCtrl.getMainPanel().getChildren().remove(mainCtrl.getSelectMonthPane());
        mainCtrl.getMainPanel().getChildren().add(mainCtrl.getSelectDatePane());
        mainCtrl.setDatesInMonth();
        mainCtrl.getMainBtn().setText(CalendarUtils.getMonthName(mainCtrl.getShownMonth()) + ", " + mainCtrl.getShownYear());

        mainCtrl.clearDetailPane();
        mainCtrl.showAppointments();
    }
}
