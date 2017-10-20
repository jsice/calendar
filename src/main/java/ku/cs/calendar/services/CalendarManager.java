package ku.cs.calendar.services;

import ku.cs.calendar.datasources.DataSource;
import ku.cs.calendar.models.Appointment;

import java.util.*;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class CalendarManager {

    private PriorityQueue<Appointment> appointments;
    private DataSource dataSource;

    public CalendarManager(DataSource source) {
        this.dataSource = source;
        this.appointments = new PriorityQueue<Appointment>(this.dataSource.getAllAppointments());
    }

    public void addAppointment(Appointment ap) {
        ap = this.dataSource.insertAppointment(ap);
        this.appointments.add(ap);
    }

    public PriorityQueue<Appointment> getAppointments(int date, int month, int year) {
        PriorityQueue<Appointment> aps = new PriorityQueue<Appointment>();
        for (Appointment ap: this.appointments) {
            if (ap.isOnTheDate(date, month, year))
                aps.add(ap);
        }
        return aps;
    }

    public boolean hasAppointmentsOnDate(int date, int month, int year) {
        return getAppointments(date, month, year).size() != 0;
    }

    public void updateAppointment(Appointment ap) {
        this.dataSource.updateAppointment(ap);
    }

    public void removeAppointment(Appointment ap) {
        this.dataSource.deleteAppointment(ap);
        this.appointments.remove(ap);
    }

}
