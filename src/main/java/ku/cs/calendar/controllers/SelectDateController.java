package ku.cs.calendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import ku.cs.calendar.models.Calendar;

import java.util.Date;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class SelectDateController {

    private MainController mainCtrl;
    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6, dateLabel7, dateLabel8, dateLabel9, dateLabel10, dateLabel11, dateLabel12, dateLabel13, dateLabel14, dateLabel15, dateLabel16, dateLabel17, dateLabel18, dateLabel19, dateLabel20, dateLabel21;
    @FXML
    private Label dateLabel22, dateLabel23, dateLabel24, dateLabel25, dateLabel26, dateLabel27, dateLabel28, dateLabel29, dateLabel30, dateLabel31, dateLabel32, dateLabel33, dateLabel34, dateLabel35, dateLabel36, dateLabel37, dateLabel38, dateLabel39, dateLabel40, dateLabel41, dateLabel42;
    private Label[] dateLabels;

    public void setMainCtrl(MainController mainCtrl) {
        dateLabels = new Label[] {dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6, dateLabel7, dateLabel8, dateLabel9, dateLabel10, dateLabel11, dateLabel12, dateLabel13, dateLabel14, dateLabel15, dateLabel16, dateLabel17, dateLabel18, dateLabel19, dateLabel20, dateLabel21,
                dateLabel22, dateLabel23, dateLabel24, dateLabel25, dateLabel26, dateLabel27, dateLabel28, dateLabel29, dateLabel30, dateLabel31, dateLabel32, dateLabel33, dateLabel34, dateLabel35, dateLabel36, dateLabel37, dateLabel38, dateLabel39, dateLabel40, dateLabel41, dateLabel42};
        this.mainCtrl = mainCtrl;
    }

    public void setDates() {
        int m = mainCtrl.getSelectedMonth();
        int y = mainCtrl.getSelectedYear();
        Date d = new Date(y-1900-543, m-1, 1);
        int day = d.getDay();
        int dNum = 1;
        for (int i = 0; i < dateLabels.length; i++) {
            if (i >= day && dNum <= Calendar.getMonthDay(m, y)) {
                dateLabels[i].setText(dNum+"");
                dNum++;
            }
            else {
                dateLabels[i].setText("");
            }

        }
    }

    @FXML
    public void selectDate(MouseEvent e) {
        String text = ((Label)e.getSource()).getText();
        if (text.equals("")) return;
        int d = Integer.parseInt(text);
        mainCtrl.setSelectedDate(d);
        mainCtrl.getSelectedDateLabel().setText(Calendar.getMonthName(mainCtrl.getSelectedMonth()) + " " + mainCtrl.getSelectedDate() + ", " + mainCtrl.getSelectedYear());

        mainCtrl.showAppointments();
    }

}
