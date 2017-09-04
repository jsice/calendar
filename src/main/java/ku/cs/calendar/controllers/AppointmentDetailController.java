package ku.cs.calendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import ku.cs.calendar.models.Appointment;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class AppointmentDetailController {

    private MainController mainCtrl;
    private Appointment appointment;

    @FXML
    private Label hr, min, title;
    @FXML
    private TextArea description;

    protected void showDetail() {
        hr.setText(String.format("%02d", appointment.getHr()));
        min.setText(String.format("%02d", appointment.getMin()));
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
    }

    @FXML
    private void back() {
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApDetailPane());
        this.mainCtrl.getDetailPanel().getChildren().add(this.mainCtrl.getNewBtn());
        this.mainCtrl.showAppointments();
    }

    @FXML
    private void applyEditMode() {
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApDetailPane());
        this.mainCtrl.getDetailPanel().getChildren().add(this.mainCtrl.getApEditPane());
        this.mainCtrl.getApEditCtrl().setAppointment(this.appointment);
        this.mainCtrl.getApEditCtrl().setup();
    }

    protected void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    protected void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
