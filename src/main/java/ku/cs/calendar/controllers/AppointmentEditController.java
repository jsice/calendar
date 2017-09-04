package ku.cs.calendar.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ku.cs.calendar.models.Appointment;

public class AppointmentEditController {

    private MainController mainCtrl;

    private Appointment appointment;

    @FXML
    private TextField hr, min, title;
    @FXML
    private TextArea description;

    @FXML
    protected void save(ActionEvent e){
        appointment.setTitle(title.getText());
        appointment.setDescription(description.getText());
        appointment.setHr(Integer.parseInt(hr.getText()));
        appointment.setMin(Integer.parseInt(min.getText()));
        this.mainCtrl.getApDetailCtrl().showDetail();
        back();
    }

    @FXML
    protected void cancel(ActionEvent e){
        back();
    }

    @FXML
    protected void deleteThisAppointment(ActionEvent e){

    }

    protected void setup() {
        hr.setText(String.format("%02d", appointment.getHr()));
        min.setText(String.format("%02d", appointment.getMin()));
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
    }

    private void back() {
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApEditPane());
        this.mainCtrl.getDetailPanel().getChildren().add(this.mainCtrl.getApDetailPane());
    }

    public void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
