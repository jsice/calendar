package ku.cs.calendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import ku.cs.calendar.models.Appointment;
import ku.cs.calendar.models.Calendar;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class SelectDateController {

    private MainController mainCtrl;
    private Label[] dateLabels;

    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6, dateLabel7, dateLabel8, dateLabel9, dateLabel10, dateLabel11, dateLabel12, dateLabel13, dateLabel14, dateLabel15, dateLabel16, dateLabel17, dateLabel18, dateLabel19, dateLabel20, dateLabel21;
    @FXML
    private Label dateLabel22, dateLabel23, dateLabel24, dateLabel25, dateLabel26, dateLabel27, dateLabel28, dateLabel29, dateLabel30, dateLabel31, dateLabel32, dateLabel33, dateLabel34, dateLabel35, dateLabel36, dateLabel37, dateLabel38, dateLabel39, dateLabel40, dateLabel41, dateLabel42;
    @FXML
    private FlowPane datePanel1, datePanel2, datePanel3, datePanel4, datePanel5, datePanel6, datePanel7, datePanel8, datePanel9, datePanel10, datePanel11, datePanel12, datePanel13, datePanel14, datePanel15, datePanel16, datePanel17, datePanel18, datePanel19, datePanel20, datePanel21;
    @FXML
    private FlowPane datePanel22, datePanel23, datePanel24, datePanel25, datePanel26, datePanel27, datePanel28, datePanel29, datePanel30, datePanel31, datePanel32, datePanel33, datePanel34, datePanel35, datePanel36, datePanel37, datePanel38, datePanel39, datePanel40, datePanel41, datePanel42;


    protected void setMainCtrl(MainController mainCtrl) {
        dateLabels = new Label[] {dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6, dateLabel7, dateLabel8, dateLabel9, dateLabel10, dateLabel11, dateLabel12, dateLabel13, dateLabel14, dateLabel15, dateLabel16, dateLabel17, dateLabel18, dateLabel19, dateLabel20, dateLabel21,
                dateLabel22, dateLabel23, dateLabel24, dateLabel25, dateLabel26, dateLabel27, dateLabel28, dateLabel29, dateLabel30, dateLabel31, dateLabel32, dateLabel33, dateLabel34, dateLabel35, dateLabel36, dateLabel37, dateLabel38, dateLabel39, dateLabel40, dateLabel41, dateLabel42};
        this.mainCtrl = mainCtrl;
    }

    protected void setDates() {
        int m = mainCtrl.getShownMonth();
        int y = mainCtrl.getShownYear();
        java.util.Calendar d = new GregorianCalendar(y-543, m-1, 1);
        int day = d.get(java.util.Calendar.DAY_OF_WEEK) - 1;
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
    protected void selectDate(MouseEvent e) {
        String text = ((Label)e.getSource()).getText();
        if (text.equals("")) return;
        int d = Integer.parseInt(text);
        mainCtrl.setSelectedDate(d);
        mainCtrl.setSelectedMonth(mainCtrl.getShownMonth());
        mainCtrl.setSelectedYear(mainCtrl.getShownYear());
        mainCtrl.getSelectedDateLabel().setText(Calendar.getMonthName(mainCtrl.getSelectedMonth()) + " " + mainCtrl.getSelectedDate() + ", " + mainCtrl.getSelectedYear());

        mainCtrl.getDetailPanel().getChildren().remove(mainCtrl.getApDetailPane());
        mainCtrl.getDetailPanel().getChildren().remove(mainCtrl.getAddPanel());
        mainCtrl.getDetailPanel().getChildren().remove(mainCtrl.getNewBtn());
        mainCtrl.getDetailPanel().getChildren().add(mainCtrl.getNewBtn());

        Calendar c = mainCtrl.getCalendar();
        if (!c.hasAppointmentsOnDate(mainCtrl.getSelectedDate(), mainCtrl.getSelectedMonth(), mainCtrl.getSelectedYear())) {
            ArrayList<Appointment> appointments = mainCtrl.getDbManager().getAppointmentByDate(mainCtrl.getSelectedDate(), mainCtrl.getSelectedMonth(), mainCtrl.getSelectedYear());
            for (Appointment ap: appointments) {
                c.addAppointment(ap);
            }
        }

        mainCtrl.showAppointments();

    }

}
