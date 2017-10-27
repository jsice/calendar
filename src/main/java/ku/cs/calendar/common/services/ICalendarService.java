package ku.cs.calendar.common.services;

import ku.cs.calendar.common.models.Appointment;

import java.util.PriorityQueue;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public interface ICalendarService {
    public void addAppointment(Appointment ap);
    public boolean hasAppointmentsOnDate(int date, int month, int year);
    public PriorityQueue<Appointment> getAppointments(int date, int month, int year);
    public void removeAppointment(Appointment ap);
    public void updateAppointment(Appointment ap);
}
