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
        hr.setText(appointment.getHr()+"");
        min.setText(appointment.getMin()+"");
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
    }

    @FXML
    private void applyEditMode() {

    }

    protected void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    protected void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
