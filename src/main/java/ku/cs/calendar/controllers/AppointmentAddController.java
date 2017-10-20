package ku.cs.calendar.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ku.cs.calendar.models.Appointment;
import ku.cs.calendar.services.CalendarUtils;
import ku.cs.calendar.models.Date;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
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
        if (validateInput()) {
            int selectedDate = Integer.parseInt(dField.getText());
            int selectedMonth = Integer.parseInt(mField.getText());
            int selectedYear = Integer.parseInt(yField.getText());
            int repeatedMode = repeatedComboBox.getSelectionModel().getSelectedIndex();
            Appointment ap = new Appointment(new Date(selectedDate, selectedMonth , selectedYear), Integer.parseInt(hrField.getText()), Integer.parseInt(minField.getText()), repeatedMode);
            if (!titleField.getText().equals("")) ap.setTitle(titleField.getText());
            if (!descriptionField.getText().equals("")) ap.setDescription(descriptionField.getText());
            this.mainCtrl.getCalendarManager().addAppointment(ap);
            this.mainCtrl.selectDate(selectedDate, selectedMonth, selectedYear);
            back();
        }
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
        dField.setStyle("-fx-control-inner-background: White");
        mField.setStyle("-fx-control-inner-background: White");
        yField.setStyle("-fx-control-inner-background: White");
        hrField.setStyle("-fx-control-inner-background: White");
        minField.setStyle("-fx-control-inner-background: White");

    }

    private void back() {
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApAddPane());
        this.mainCtrl.showAppointments();
    }

    private boolean validateInput() {
        boolean valid = true;
        int date = 0;
        int month = 0;
        int year = 0;
        int h = -1;
        int m = -1;
        try {
            date = Integer.parseInt(dField.getText());
        } catch (NumberFormatException e) {
            dField.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }
        try {
            month = Integer.parseInt(mField.getText());
        } catch (NumberFormatException e) {
            mField.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }
        try {
            year = Integer.parseInt(yField.getText());
        } catch (NumberFormatException e) {
            yField.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }

        if (date != 0 && month != 0 && year != 0) {
            if (!CalendarUtils.isValidDate(date, month, year)) {
                dField.setStyle("-fx-control-inner-background: Red");
                mField.setStyle("-fx-control-inner-background: Red");
                yField.setStyle("-fx-control-inner-background: Red");
                valid = false;
            }
        }

        try {
            h = Integer.parseInt(hrField.getText());
        } catch (NumberFormatException e) {
            hrField.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }

        try {
            m = Integer.parseInt(minField.getText());
        } catch (NumberFormatException e) {
            minField.setStyle("-fx-control-inner-background: Red");
            valid = false;
        }

        if (m != -1 && h != -1) {
            if (!(m >= 0 && m < 60 &&
                    h >= 0 && h < 24)) {
                hrField.setStyle("-fx-control-inner-background: Red");
                minField.setStyle("-fx-control-inner-background: Red");
                valid = false;
            }
        }

        return valid;
    }

    protected void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }
}
