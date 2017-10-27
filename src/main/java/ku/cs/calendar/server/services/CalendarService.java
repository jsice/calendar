package ku.cs.calendar.server.services;

import ku.cs.calendar.common.services.ICalendarService;
import ku.cs.calendar.server.datasources.DataSource;
import ku.cs.calendar.common.models.Appointment;

import java.util.*;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */

public class CalendarService implements ICalendarService {

    private PriorityQueue<Appointment> appointments;
    private DataSource dataSource;

    public CalendarService(DataSource source) {
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
        for (Appointment appointment: appointments) {
            if (ap.getId() == appointment.getId()) {
                appointment.setTitle(ap.getTitle());
                appointment.setDescription(ap.getDescription());
                appointment.setDate(ap.getDate());
                appointment.setHr(ap.getHr());
                appointment.setMin(ap.getMin());
                appointment.setRepeated(ap.getRepeated());
            }
        }
    }

    public void removeAppointment(Appointment ap) {
        this.dataSource.deleteAppointment(ap);
        this.appointments.remove(ap);
        for (Appointment appointment: appointments) {
            if (ap.getId() == appointment.getId()) {
                this.appointments.remove(appointment);
            }
        }
    }

}
