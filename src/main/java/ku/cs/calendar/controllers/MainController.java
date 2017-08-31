package ku.cs.calendar.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import ku.cs.calendar.models.Appointment;
import ku.cs.calendar.models.Calendar;
import ku.cs.calendar.models.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */

public class MainController {

    private FlowPane addPanel;
    private SelectMonthController selectMonthCtrl;
    private SelectDateController selectDateCtrl;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDate;
    private Calendar calendar;
    private FlowPane selectDatePane;
    private GridPane selectMonthPane;
    private ArrayList<Label> appointmentLabels;
    @FXML
    private Label selectedDateLabel;
    @FXML
    private FlowPane detailPanel;
    @FXML
    private FlowPane mainPanel;
    @FXML
    private Button mainBtn;
    @FXML
    private Button increaseBtn;
    @FXML
    private Button decreaseBtn;

    public void init() throws IOException {
        this.calendar = new Calendar();
        FXMLLoader selectMonthPaneLoader = new FXMLLoader(getClass().getResource("/select_month.fxml"));
        FXMLLoader selectDatePaneLoader = new FXMLLoader(getClass().getResource("/select_date.fxml"));
        this.selectMonthPane = selectMonthPaneLoader.load();
        this.selectDatePane = selectDatePaneLoader.load();
        this.selectMonthCtrl = selectMonthPaneLoader.getController();
        this.selectMonthCtrl.setMainCtrl(this);
        this.selectDateCtrl = selectDatePaneLoader.getController();
        this.selectDateCtrl.setMainCtrl(this);
        this.mainPanel.getChildren().add(this.selectDatePane);
        this.addPanel = initNewAppointmentPanel();
        this.appointmentLabels = new ArrayList<Label>();
    }

    private FlowPane initNewAppointmentPanel() {
        FlowPane fp = new FlowPane();
        fp.setPrefSize(348, 404);
        Label title = new Label("Title: ");
        title.setPrefWidth(348);
        final TextField titleField = new TextField();
        titleField.setPrefWidth(348);
        Label hr = new Label("Hr: ");
        hr.setPrefWidth(348);
        final TextField hrField = new TextField();
        hrField.setPrefWidth(348);
        Label min = new Label("Min: ");
        min.setPrefWidth(348);
        final TextField minField = new TextField();
        minField.setPrefWidth(348);
        Button ok = new Button("OK");
        ok.setPrefWidth(174);
        ok.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Appointment ap = new Appointment(new Date(selectedYear, selectedMonth , selectedDate), Integer.parseInt(hrField.getText()), Integer.parseInt(minField.getText()));
                if (!titleField.getText().equals("")) ap.setTitle(titleField.getText());
                calendar.addAppointment(ap);

                titleField.setText("");
                hrField.setText("");
                minField.setText("");

                detailPanel.getChildren().remove(addPanel);
                showAppointments();
            }
        });

        Button cancel = new Button("cancel");
        cancel.setPrefWidth(174);
        cancel.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                titleField.setText("");
                hrField.setText("");
                minField.setText("");
                detailPanel.getChildren().remove(addPanel);
            }
        });

        fp.getChildren().add(title);
        fp.getChildren().add(titleField);
        fp.getChildren().add(hr);
        fp.getChildren().add(hrField);
        fp.getChildren().add(min);
        fp.getChildren().add(minField);
        fp.getChildren().add(cancel);
        fp.getChildren().add(ok);
        return fp;
    }

    public void setToday() {
        java.util.Date today = new java.util.Date();
        selectedYear = today.getYear() + 1900 + 543;
        selectedMonth = today.getMonth() + 1;
        selectedDate = today.getDate();
        mainBtn.setText(Calendar.getMonthName(selectedMonth) + ", " + selectedYear);
        selectedDateLabel.setText(Calendar.getMonthName(selectedMonth) + " " + selectedDate + ", " + selectedYear);
        this.selectDateCtrl.setDates();
        showAppointments();
    }

    public void showAppointments() {
        hideAppointments();
        TreeSet<Appointment> appointments = calendar.getAppointments(selectedDate, selectedMonth, selectedYear);

        for (Appointment ap: appointments) {
            Label l = new Label(String.format("%02d:%02d  %s",ap.getHr(),ap.getMin(),ap.getTitle()));
            l.setPrefWidth(348);
            appointmentLabels.add(l);
            detailPanel.getChildren().add(l);
        }

    }

    public void hideAppointments() {
        if (appointmentLabels.size() > 0)
            for (int i = appointmentLabels.size() - 1; i >= 0; i--) {
                detailPanel.getChildren().remove(appointmentLabels.get(i));
                appointmentLabels.remove(appointmentLabels.get(i));

            }
            for (Label l: appointmentLabels) {

            }
    }

    @FXML
    private void handleMainBtn(ActionEvent e) {
        if (selectedMonth != 0) {
            mainBtn.setText(selectedYear + "");
            selectedMonth = 0;
            this.mainPanel.getChildren().remove(this.selectDatePane);
            this.mainPanel.getChildren().add(this.selectMonthPane);
        }
    }

    @FXML
    private void handleIncBtn(ActionEvent e) {
        if (selectedMonth != 0) {
            selectedMonth++;
            if (selectedMonth == 13) {
                selectedMonth = 1;
                selectedYear++;
            }
            mainBtn.setText(Calendar.getMonthName(selectedMonth) + ", " + selectedYear);
            this.selectDateCtrl.setDates();

        } else if (selectedYear != 0) {
            selectedYear++;
            mainBtn.setText(selectedYear + "");
        }
    }

    @FXML
    private void handleDecBtn(ActionEvent e) {
        if (selectedYear == 1 && selectedMonth == 1) {
            //can't decrease, do nothing
        } else if (selectedMonth != 0) {
            selectedMonth--;
            if (selectedMonth == 0) {
                selectedMonth = 12;
                selectedYear--;
            }
            mainBtn.setText(Calendar.getMonthName(selectedMonth) + ", " + selectedYear);
            this.selectDateCtrl.setDates();
        } else if (selectedYear > 2) {
            selectedYear--;
            mainBtn.setText(selectedYear + "");
        }
    }

    @FXML
    public void newAppointment(ActionEvent e) {
        hideAppointments();
        detailPanel.getChildren().add(addPanel);
    }

    public FlowPane getSelectDatePane() {
        return selectDatePane;
    }

    public GridPane getSelectMonthPane() {
        return selectMonthPane;
    }

    public Button getMainBtn() {
        return mainBtn;
    }

    public Button getDecreaseBtn() {
        return decreaseBtn;
    }

    public Button getIncreaseBtn() {
        return increaseBtn;
    }

    public FlowPane getMainPanel() {
        return mainPanel;
    }

    public Label getSelectedDateLabel() {
        return selectedDateLabel;
    }

    public void setSelectedDate(int selectedDate) {
        this.selectedDate = selectedDate;
    }

    public int getSelectedDate() {
        return selectedDate;
    }


    public void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    public int getSelectedMonth() {
        return selectedMonth;
    }

    public int getSelectedYear() {
        return selectedYear;
    }

    public SelectDateController getSelectDateCtrl() {
        return selectDateCtrl;
    }

    public SelectMonthController getSelectMonthCtrl() {
        return selectMonthCtrl;
    }
}
