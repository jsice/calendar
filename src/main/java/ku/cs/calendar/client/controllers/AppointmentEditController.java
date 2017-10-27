package ku.cs.calendar.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.calendar.common.models.Appointment;
import ku.cs.calendar.common.models.AppointmentNeverRepeatState;
import ku.cs.calendar.common.utils.AppointmentRepeatStateUtils;
import ku.cs.calendar.common.utils.CalendarUtils;
import ku.cs.calendar.common.models.Date;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class AppointmentEditController {

    private MainController mainCtrl;

    private Appointment appointment;
    @FXML
    private Label dateLabel;
    @FXML
    private TextField hr, min, title, date, month, year;
    @FXML
    private TextArea description;
    @FXML
    private ComboBox<String> repeatedComboBox;

    @FXML
    protected void save(ActionEvent e){
        if (this.validateInput()) {
            appointment.setTitle(title.getText());
            appointment.setDescription(description.getText());
            appointment.setHr(Integer.parseInt(hr.getText()));
            appointment.setMin(Integer.parseInt(min.getText()));
            appointment.setDate(new Date(Integer.parseInt(date.getText()), Integer.parseInt(month.getText()), Integer.parseInt(year.getText())));
            appointment.setRepeated(AppointmentRepeatStateUtils.getInstanceOfState(repeatedComboBox.getSelectionModel().getSelectedItem()));
            this.mainCtrl.getCalendarService().updateAppointment(appointment);

            back();
        }
    }

    @FXML
    protected void cancel(ActionEvent e){
        back();
    }

    @FXML
    protected void deleteThisAppointment(ActionEvent e){
        this.mainCtrl.getCalendarService().removeAppointment(appointment);
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApEditPane());
        this.mainCtrl.showAppointments();
    }

    @FXML
    private void initialize() {
        this.repeatedComboBox.getItems().removeAll(this.repeatedComboBox.getItems());
        this.repeatedComboBox.getItems().addAll("Never", "Daily", "Weekly", "Monthly");
        this.repeatedComboBox.getSelectionModel().select("Never");
    }

    protected void setup() {
        hr.setText(String.format("%02d", appointment.getHr()));
        min.setText(String.format("%02d", appointment.getMin()));
        date.setText(appointment.getDate().getDate()+"");
        month.setText(appointment.getDate().getMonth()+"");
        year.setText(appointment.getDate().getYear()+"");
        title.setText(appointment.getTitle());
        description.setText(appointment.getDescription());
        String repeatState = AppointmentRepeatStateUtils.getNameOfState(appointment.getRepeated());
        this.repeatedComboBox.getSelectionModel().select(repeatState);
        dateLabel.setText("Start Date:");
        if (appointment.getRepeated().getClass() == AppointmentNeverRepeatState.class) dateLabel.setText("Date:");
        date.setStyle("-fx-control-inner-background: White");
        month.setStyle("-fx-control-inner-background: White");
        year.setStyle("-fx-control-inner-background: White");
        hr.setStyle("-fx-control-inner-background: White");
        min.setStyle("-fx-control-inner-background: White");
    }

    private void back() {
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApEditPane());
        this.mainCtrl.seeAppointmentDetail(this.appointment);
    }

    private boolean validateInput() {
        boolean valid = true;
        int date = 0;
        int month = 0;
        int year = 0;
        int h = -1;
        int m = -1;
        try {
            date = Integer.parseInt(this.date.getText());
            this.date.setStyle("-fx-control-inner-background: White");
        } catch (NumberFormatException e) {
            this.date.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }
        try {
            month = Integer.parseInt(this.month.getText());
            this.month.setStyle("-fx-control-inner-background: White");
        } catch (NumberFormatException e) {
            this.month.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }
        try {
            year = Integer.parseInt(this.year.getText());
            this.year.setStyle("-fx-control-inner-background: White");
        } catch (NumberFormatException e) {
            this.year.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }

        if (date != 0 && month != 0 && year != 0) {
            if (!CalendarUtils.isValidDate(date, month, year)) {
                this.date.setStyle("-fx-control-inner-background: Red");
                this.month.setStyle("-fx-control-inner-background: Red");
                this.year.setStyle("-fx-control-inner-background: Red");
                valid = false;
            } else {
                this.date.setStyle("-fx-control-inner-background: White");
                this.month.setStyle("-fx-control-inner-background: White");
                this.year.setStyle("-fx-control-inner-background: White");
            }
        }

        try {
            h = Integer.parseInt(this.hr.getText());
            this.hr.setStyle("-fx-control-inner-background: White");
        } catch (NumberFormatException e) {
            this.hr.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }

        try {
            m = Integer.parseInt(this.min.getText());
            this.min.setStyle("-fx-control-inner-background: White");
        } catch (NumberFormatException e) {
            this.min.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }

        if (m != -1 && h != -1) {
            if (!(m >= 0 && m < 60 &&
                    h >= 0 && h < 24)) {
                this.hr.setStyle("-fx-control-inner-background: Red");
                this.min.setStyle("-fx-control-inner-background: Red");
                valid = false;
            } else {
                this.hr.setStyle("-fx-control-inner-background: White");
                this.min.setStyle("-fx-control-inner-background: White");
            }
        }

        return valid;
    }

    public void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
}
