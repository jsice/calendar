package ku.cs.calendar.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import ku.cs.calendar.models.Appointment;
import ku.cs.calendar.models.Calendar;
import ku.cs.calendar.models.Date;

import java.io.IOException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */

public class MainController {

    private FlowPane addPanel;
    private SelectMonthController selectMonthCtrl;
    private SelectDateController selectDateCtrl;
    private AppointmentDetailController apDetailCtrl;
    private AppointmentEditController apEditCtrl;
    private DatabaseManager dbManager;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDate;
    private int shownYear;
    private int shownMonth;
    private Calendar calendar;
    private FlowPane selectDatePane;
    private GridPane selectMonthPane, apDetailPane, apEditPane;
    private ArrayList<Label> appointmentLabels;
    @FXML
    private Button newBtn;
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
    @FXML
    private ScrollPane detailArea;

    public void init() throws IOException {
        this.calendar = new Calendar();
        this.dbManager = new DatabaseManager();
        FXMLLoader selectMonthPaneLoader = new FXMLLoader(getClass().getResource("/select_month.fxml"));
        FXMLLoader selectDatePaneLoader = new FXMLLoader(getClass().getResource("/select_date.fxml"));
        FXMLLoader apDetailPaneLoader = new FXMLLoader(getClass().getResource("/ap_detail.fxml"));
        FXMLLoader apEditPaneLoader = new FXMLLoader(getClass().getResource("/ap_edit.fxml"));
        this.selectMonthPane = selectMonthPaneLoader.load();
        this.selectDatePane = selectDatePaneLoader.load();
        this.selectMonthCtrl = selectMonthPaneLoader.getController();
        this.selectMonthCtrl.setMainCtrl(this);
        this.selectDateCtrl = selectDatePaneLoader.getController();
        this.selectDateCtrl.setMainCtrl(this);
        this.mainPanel.getChildren().add(this.selectDatePane);
        this.addPanel = initNewAppointmentPanel();
        this.appointmentLabels = new ArrayList<Label>();

        this.apDetailPane = apDetailPaneLoader.load();
        this.apDetailCtrl = apDetailPaneLoader.getController();
        this.apDetailCtrl.setMainCtrl(this);

        this.apEditPane = apEditPaneLoader.load();
        this.apEditCtrl = apEditPaneLoader.getController();
        this.apEditCtrl.setMainCtrl(this);
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
                Appointment ap = new Appointment(new Date(selectedDate, selectedMonth , selectedYear), Integer.parseInt(hrField.getText()), Integer.parseInt(minField.getText()));
                if (!titleField.getText().equals("")) ap.setTitle(titleField.getText());
                calendar.addAppointment(ap);
                dbManager.insertAppointment(ap);
                titleField.setText("");
                hrField.setText("");
                minField.setText("");

                detailPanel.getChildren().remove(addPanel);
                detailPanel.getChildren().add(newBtn);
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
                detailPanel.getChildren().add(newBtn);
                showAppointments();
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
        java.util.Calendar today = new GregorianCalendar();
        selectedYear = today.get(java.util.Calendar.YEAR) + 543;
        selectedMonth = today.get(java.util.Calendar.MONTH) + 1;
        selectedDate = today.get(java.util.Calendar.DAY_OF_MONTH);
        shownMonth = selectedMonth;
        shownYear = selectedYear;
        mainBtn.setText(Calendar.getMonthName(selectedMonth) + ", " + selectedYear);
        selectedDateLabel.setText(Calendar.getMonthName(selectedMonth) + " " + selectedDate + ", " + selectedYear);
        this.selectDateCtrl.setDates();
        ArrayList<Appointment> aps = dbManager.getAppointmentByDate(selectedDate, selectedMonth, selectedYear);
        for (Appointment ap: aps) {
            calendar.addAppointment(ap);
        }
        showAppointments();
    }

    protected void showAppointments() {
        hideAppointments();
        PriorityQueue<Appointment> appointments = calendar.getAppointments(selectedDate, selectedMonth, selectedYear);

        for (Appointment ap: appointments) {
            final Appointment appointment = ap;
            String title = ap.getTitle();
            if (title.length() > 30) title = title.substring(0, 26)+"...";
            Label l = new Label(String.format("%02d:%02d  %s",ap.getHr(),ap.getMin(),title));
            l.setPrefWidth(324);
            l.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    hideAppointments();
                    detailPanel.getChildren().remove(newBtn);
                    apDetailCtrl.setAppointment(appointment);
                    apDetailCtrl.showDetail();
                    detailPanel.getChildren().add(apDetailPane);
                }
            });
            appointmentLabels.add(l);
            detailPanel.getChildren().add(l);
        }

    }

    private void hideAppointments() {
        if (appointmentLabels.size() > 0)
            for (int i = appointmentLabels.size() - 1; i >= 0; i--) {
                detailPanel.getChildren().remove(appointmentLabels.get(i));
                appointmentLabels.remove(appointmentLabels.get(i));

            }
    }

    @FXML
    private void handleMainBtn(ActionEvent e) {
        if (shownMonth != 0) {
            mainBtn.setText(shownYear + "");
            shownMonth = 0;
            this.mainPanel.getChildren().remove(this.selectDatePane);
            this.mainPanel.getChildren().add(this.selectMonthPane);
        }
    }

    @FXML
    private void handleIncBtn(ActionEvent e) {
        if (shownMonth != 0) {
            shownMonth++;
            if (shownMonth == 13) {
                shownMonth = 1;
                shownYear++;
            }
            mainBtn.setText(Calendar.getMonthName(shownMonth) + ", " + shownYear);
            this.selectDateCtrl.setDates();

        } else if (shownYear != 0) {
            shownYear++;
            mainBtn.setText(shownYear + "");
        }
    }

    @FXML
    private void handleDecBtn(ActionEvent e) {
        if (shownYear != 1 || shownMonth != 1) {
            if (shownMonth != 0) {
                shownMonth--;
                if (shownMonth == 0) {
                    shownMonth = 12;
                    shownYear--;
                }
                mainBtn.setText(Calendar.getMonthName(shownMonth) + ", " + shownYear);
                this.selectDateCtrl.setDates();
            } else if (shownYear > 2) {
                shownYear--;
                mainBtn.setText(shownYear + "");
            }
        }
    }

    @FXML
    private void newAppointment(ActionEvent e) {
        hideAppointments();
        detailPanel.getChildren().remove(newBtn);
        detailPanel.getChildren().add(addPanel);
    }

    protected FlowPane getSelectDatePane() {
        return selectDatePane;
    }

    protected GridPane getSelectMonthPane() {
        return selectMonthPane;
    }

    protected Button getMainBtn() {
        return mainBtn;
    }

    protected Button getDecreaseBtn() {
        return decreaseBtn;
    }

    protected Button getIncreaseBtn() {
        return increaseBtn;
    }

    protected FlowPane getMainPanel() {
        return mainPanel;
    }

    protected Label getSelectedDateLabel() {
        return selectedDateLabel;
    }

    protected void setSelectedDate(int selectedDate) {
        this.selectedDate = selectedDate;
    }

    protected int getSelectedDate() {
        return selectedDate;
    }

    protected FlowPane getDetailPanel() { return detailPanel; }

    protected void setSelectedMonth(int selectedMonth) {
        this.selectedMonth = selectedMonth;
    }

    protected int getSelectedMonth() {
        return selectedMonth;
    }

    protected int getSelectedYear() {
        return selectedYear;
    }

    protected SelectDateController getSelectDateCtrl() {
        return selectDateCtrl;
    }

    protected SelectMonthController getSelectMonthCtrl() {
        return selectMonthCtrl;
    }

    protected GridPane getApDetailPane() {
        return apDetailPane;
    }

    protected AppointmentDetailController getApDetailCtrl() {
        return apDetailCtrl;
    }

    protected FlowPane getAddPanel() {
        return addPanel;
    }

    protected Button getNewBtn() {
        return newBtn;
    }

    protected int getShownMonth() {
        return shownMonth;
    }

    protected int getShownYear() {
        return shownYear;
    }

    protected void setSelectedYear(int selectedYear) {
        this.selectedYear = selectedYear;
    }

    protected void setShownMonth(int shownMonth) {
        this.shownMonth = shownMonth;
    }

    protected AppointmentEditController getApEditCtrl() {
        return apEditCtrl;
    }

    protected GridPane getApEditPane() {
        return apEditPane;
    }

    protected Calendar getCalendar() {
        return calendar;
    }

    public DatabaseManager getDbManager() {
        return dbManager;
    }
}
