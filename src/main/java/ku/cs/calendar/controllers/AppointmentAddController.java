package ku.cs.calendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ku.cs.calendar.models.Appointment;
import ku.cs.calendar.models.Date;

public class AppointmentAddController {

    private MainController mainCtrl;

    @FXML
    private TextField dField, mField, yField, hrField, minField, titleField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ComboBox<String> repeatedComboBox;

    @FXML
    private void ok() {
        int selectedDate = Integer.parseInt(dField.getText());
        int selectedMonth = Integer.parseInt(mField.getText());
        int selectedYear = Integer.parseInt(yField.getText());
        int repeatedMode = repeatedComboBox.getSelectionModel().getSelectedIndex();
        Appointment ap = new Appointment(new Date(selectedDate, selectedMonth , selectedYear), Integer.parseInt(hrField.getText()), Integer.parseInt(minField.getText()), repeatedMode);
        if (!titleField.getText().equals("")) ap.setTitle(titleField.getText());
        if (!descriptionField.getText().equals("")) ap.setDescription(descriptionField.getText());
        ap = this.mainCtrl.getDbManager().insertAppointment(ap);
        this.mainCtrl.getCalendar().addAppointment(ap);
        back();
    }

    @FXML
    private void cancel() {
        back();
    }

    @FXML
    private void initialize() {
        this.repeatedComboBox.getItems().removeAll(this.repeatedComboBox.getItems());
        this.repeatedComboBox.getItems().addAll("Never", "Daily", "Weekly", "Monthly");
        this.repeatedComboBox.getSelectionModel().select("Never");
    }

    protected void setup() {
        dField.setText(mainCtrl.getSelectedDate()+"");
        mField.setText(mainCtrl.getSelectedMonth()+"");
        yField.setText(mainCtrl.getSelectedYear()+"");
        hrField.setText("");
        minField.setText("");
        titleField.setText("");
        descriptionField.setText("");
        this.repeatedComboBox.getSelectionModel().select("Never");
    }

    private void back() {
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApAddPane());
        this.mainCtrl.showAppointments();
    }

    public void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }
}
