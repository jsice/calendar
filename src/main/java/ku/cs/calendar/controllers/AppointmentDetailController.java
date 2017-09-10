package ku.cs.calendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ku.cs.calendar.models.Appointment;
import ku.cs.calendar.models.Calendar;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class AppointmentDetailController {

    private MainController mainCtrl;
    private Appointment appointment;

    @FXML
    private Label hr, min, title, date, repeated, dateLabel;
    @FXML
    private TextArea description;

    protected void showDetail() {
        hr.setText(String.format("%02d", appointment.getHr()));
        min.setText(String.format("%02d", appointment.getMin()));
        title.setText(appointment.getTitle());
        date.setText(String.format("%02d/%02d/%04d (%s %02d, %d)",
                appointment.getDate().getDate(), appointment.getDate().getMonth(), appointment.getDate().getYear(),
                Calendar.getMonthName(appointment.getDate().getMonth()), appointment.getDate().getDate(), appointment.getDate().getYear()));
        dateLabel.setText("Start Date:");
        String repeatedMode = "";
        int r = appointment.getRepeated();
        if (r == Appointment.REPEATED_NEVER) {
            repeatedMode = "Never";
            dateLabel.setText("Date:");
        } else if (r == Appointment.REPEATED_DAILY) {
            repeatedMode = "Daily";
        } else if (r == Appointment.REPEATED_WEEKLY) {
            repeatedMode = "Weekly";
        } else if (r == Appointment.REPEATED_MONTHLY) {
            repeatedMode = "Monthly";
        }
        repeated.setText(repeatedMode);
        description.setText(appointment.getDescription());
    }

    @FXML
    private void back() {
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApDetailPane());
        this.mainCtrl.showAppointments();
    }

    @FXML
    private void gotoEditMode() {
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApDetailPane());
        this.mainCtrl.editAppointment(this.appointment);
    }

    protected void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    protected void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
