package ku.cs.calendar.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
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
    private Label selectedLabel;
    private Color selectedColor = Color.ALICEBLUE;

    @FXML
    private Label dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6, dateLabel7, dateLabel8, dateLabel9, dateLabel10, dateLabel11, dateLabel12, dateLabel13, dateLabel14, dateLabel15, dateLabel16, dateLabel17, dateLabel18, dateLabel19, dateLabel20, dateLabel21;
    @FXML
    private Label dateLabel22, dateLabel23, dateLabel24, dateLabel25, dateLabel26, dateLabel27, dateLabel28, dateLabel29, dateLabel30, dateLabel31, dateLabel32, dateLabel33, dateLabel34, dateLabel35, dateLabel36, dateLabel37, dateLabel38, dateLabel39, dateLabel40, dateLabel41, dateLabel42;

    protected void setMainCtrl(MainController mainCtrl) {
        dateLabels = new Label[] {dateLabel1, dateLabel2, dateLabel3, dateLabel4, dateLabel5, dateLabel6, dateLabel7, dateLabel8, dateLabel9, dateLabel10, dateLabel11, dateLabel12, dateLabel13, dateLabel14, dateLabel15, dateLabel16, dateLabel17, dateLabel18, dateLabel19, dateLabel20, dateLabel21,
                dateLabel22, dateLabel23, dateLabel24, dateLabel25, dateLabel26, dateLabel27, dateLabel28, dateLabel29, dateLabel30, dateLabel31, dateLabel32, dateLabel33, dateLabel34, dateLabel35, dateLabel36, dateLabel37, dateLabel38, dateLabel39, dateLabel40, dateLabel41, dateLabel42};
        this.mainCtrl = mainCtrl;
    }

    private void clearSelected() {
        if (this.selectedLabel != null) {
            this.selectedLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
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
            if (i % 7 == 0) {
                dateLabels[i].setTextFill(Color.RED);
            }
        }

        java.util.Calendar today = new GregorianCalendar();
        if (today.get(java.util.Calendar.YEAR) + 543 == y &&
                today.get(java.util.Calendar.MONTH) + 1 == m) {
            dateLabels[day - 1 + today.get(java.util.Calendar.DAY_OF_MONTH)].setTextFill(Color.BLUE);
        }
        if (this.mainCtrl.getSelectedMonth() == m && this.mainCtrl.getSelectedYear() == y) {
            this.selectedLabel = dateLabels[day - 1 + this.mainCtrl.getSelectedDate()];
            this.selectedLabel.setBackground(new Background(new BackgroundFill(selectedColor, CornerRadii.EMPTY, Insets.EMPTY)));
        } else {
            this.clearSelected();
        }
    }

    @FXML
    protected void selectDate(MouseEvent e) {
        String text = ((Label)e.getSource()).getText();
        if (text.equals("")) return;
        if (selectedLabel != null) {
            this.selectedLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        }
        this.selectedLabel = (Label)e.getSource();
        this.selectedLabel.setBackground(new Background(new BackgroundFill(selectedColor, CornerRadii.EMPTY, Insets.EMPTY)));
        int d = Integer.parseInt(text);
        mainCtrl.setSelectedDate(d);
        mainCtrl.setSelectedMonth(mainCtrl.getShownMonth());
        mainCtrl.setSelectedYear(mainCtrl.getShownYear());
        mainCtrl.getSelectedDateLabel().setText(Calendar.getMonthName(mainCtrl.getSelectedMonth()) + " " + mainCtrl.getSelectedDate() + ", " + mainCtrl.getSelectedYear());

        mainCtrl.clearDetailPane();
        mainCtrl.showAppointments();

    }

}
