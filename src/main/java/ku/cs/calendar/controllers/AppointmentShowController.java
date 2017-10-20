package ku.cs.calendar.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import ku.cs.calendar.models.Appointment;

import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class AppointmentShowController {

    private MainController mainCtrl;
    private ArrayList<Label> appointmentLabels = new ArrayList<Label>();

    @FXML
    private FlowPane showApPane;

    @FXML
    private void newAppointment() {
        this.mainCtrl.getDetailPanel().getChildren().remove(this.mainCtrl.getApShowPane());
        this.mainCtrl.addAppointment();
    }

    protected void showAppointments() {
        hideAppointments();
        PriorityQueue<Appointment> appointments = mainCtrl.getCalendarManager().getAppointments(mainCtrl.getSelectedDate(), mainCtrl.getSelectedMonth(), mainCtrl.getSelectedYear());
        while (!appointments.isEmpty()) {
            final Appointment ap = appointments.poll();
            String title = ap.getTitle();
            if (title.length() > 30) title = title.substring(0, 26)+"...";
            Label l = new Label(String.format("%02d:%02d  %s",ap.getHr(),ap.getMin(),title));
            l.setPrefWidth(326);
            l.setPrefHeight(25);
            l.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    mainCtrl.getDetailPanel().getChildren().remove(mainCtrl.getApShowPane());
                    mainCtrl.seeAppointmentDetail(ap);
                }
            });
            this.appointmentLabels.add(l);
            this.showApPane.getChildren().add(l);
        }
    }

    private void hideAppointments() {
        if (this.appointmentLabels.size() > 0)
            for (int i = appointmentLabels.size() - 1; i >= 0; i--) {
                this.showApPane.getChildren().remove(appointmentLabels.get(i));
                this.appointmentLabels.remove(this.appointmentLabels.get(i));
            }
    }

    public void setMainCtrl(MainController mainCtrl) {
        this.mainCtrl = mainCtrl;
    }
}
