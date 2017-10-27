package ku.cs.calendar.client.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import ku.cs.calendar.common.models.Appointment;
import ku.cs.calendar.common.services.ICalendarService;
import ku.cs.calendar.common.utils.CalendarUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */

public class MainController {

    private SelectMonthController selectMonthCtrl;
    private SelectDateController selectDateCtrl;
    private AppointmentDetailController apDetailCtrl;
    private AppointmentEditController apEditCtrl;
    private AppointmentAddController apAddCtrl;
    private AppointmentShowController apShowCtrl;
    private int selectedYear;
    private int selectedMonth;
    private int selectedDate;
    private int shownYear;
    private int shownMonth;
    private ICalendarService calendarService;
    private FlowPane selectDatePane, apShowPane;
    private GridPane selectMonthPane, apDetailPane, apEditPane, apAddPane;

    @FXML
    private Label selectedDateLabel;
    @FXML
    private FlowPane detailPanel;
    @FXML
    private FlowPane mainPanel;
    @FXML
    private Button mainBtn;

    public void init() throws IOException {
        ApplicationContext bf = new ClassPathXmlApplicationContext("client-config.xml");
        this.calendarService = (ICalendarService) bf.getBean("calendarService");
        FXMLLoader selectMonthPaneLoader = new FXMLLoader(getClass().getResource("/fxml/select_month.fxml"));
        FXMLLoader selectDatePaneLoader = new FXMLLoader(getClass().getResource("/fxml/select_date.fxml"));
        FXMLLoader apDetailPaneLoader = new FXMLLoader(getClass().getResource("/fxml/ap_detail.fxml"));
        FXMLLoader apEditPaneLoader = new FXMLLoader(getClass().getResource("/fxml/ap_edit.fxml"));
        FXMLLoader apAddPaneLoader = new FXMLLoader(getClass().getResource("/fxml/ap_add.fxml"));
        FXMLLoader apShowPaneLoader = new FXMLLoader(getClass().getResource("/fxml/ap_show.fxml"));
        this.selectMonthPane = selectMonthPaneLoader.load();
        this.selectDatePane = selectDatePaneLoader.load();
        this.selectMonthCtrl = selectMonthPaneLoader.getController();
        this.selectMonthCtrl.setMainCtrl(this);
        this.selectDateCtrl = selectDatePaneLoader.getController();
        this.selectDateCtrl.setMainCtrl(this);
        this.mainPanel.getChildren().add(this.selectDatePane);

        this.apDetailPane = apDetailPaneLoader.load();
        this.apDetailCtrl = apDetailPaneLoader.getController();
        this.apDetailCtrl.setMainCtrl(this);

        this.apEditPane = apEditPaneLoader.load();
        this.apEditCtrl = apEditPaneLoader.getController();
        this.apEditCtrl.setMainCtrl(this);

        this.apAddPane = apAddPaneLoader.load();
        this.apAddCtrl = apAddPaneLoader.getController();
        this.apAddCtrl.setMainCtrl(this);

        this.apShowPane = apShowPaneLoader.load();
        this.apShowCtrl = apShowPaneLoader.getController();
        this.apShowCtrl.setMainCtrl(this);

        this.setToday();
    }

    private void setToday() {
        java.util.Calendar today = new GregorianCalendar();
        selectedYear = today.get(java.util.Calendar.YEAR) + 543;
        selectedMonth = today.get(java.util.Calendar.MONTH) + 1;
        selectedDate = today.get(java.util.Calendar.DAY_OF_MONTH);
        shownMonth = selectedMonth;
        shownYear = selectedYear;
        mainBtn.setText(CalendarUtils.getMonthName(selectedMonth) + ", " + selectedYear);
        selectedDateLabel.setText(CalendarUtils.getMonthName(selectedMonth) + " " + selectedDate + ", " + selectedYear);
        this.selectDateCtrl.setDates();
        this.showAppointments();
    }

    protected void clearDetailPane() {
        this.detailPanel.getChildren().remove(this.apAddPane);
        this.detailPanel.getChildren().remove(this.apDetailPane);
        this.detailPanel.getChildren().remove(this.apEditPane);
        this.detailPanel.getChildren().remove(this.apShowPane);
    }

    protected void showAppointments() {
        this.detailPanel.getChildren().add(this.apShowPane);
        this.apShowCtrl.showAppointments();
    }

    protected void seeAppointmentDetail(Appointment appointment) {
        this.detailPanel.getChildren().add(this.apDetailPane);
        this.apDetailCtrl.setAppointment(appointment);
        this.apDetailCtrl.showDetail();
    }

    protected void editAppointment(Appointment appointment) {
        this.detailPanel.getChildren().add(this.apEditPane);
        this.apEditCtrl.setAppointment(appointment);
        this.apEditCtrl.setup();
    }

    protected void addAppointment() {
        this.detailPanel.getChildren().add(this.apAddPane);
        this.apAddCtrl.setup();
    }

    void selectDate(int date, int month, int year) {
        this.selectDateCtrl.selectDate(date, month, year);
    }

    protected void setDatesInMonth() {
        this.selectDateCtrl.setDates();
    }

    private void setShownMonthYear() {
        mainBtn.setText(CalendarUtils.getMonthName(shownMonth) + ", " + shownYear);
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
            setShownMonthYear();
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
                setShownMonthYear();
                this.selectDateCtrl.setDates();
            } else if (shownYear > 2) {
                shownYear--;
                mainBtn.setText(shownYear + "");
            }
        }
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

    protected GridPane getApDetailPane() {
        return apDetailPane;
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

    protected GridPane getApEditPane() {
        return apEditPane;
    }

    protected ICalendarService getCalendarService() {
        return calendarService;
    }

    public GridPane getApAddPane() {
        return apAddPane;
    }

    public FlowPane getApShowPane() {
        return apShowPane;
    }
}
