package ku.cs.calendar.databases;

import ku.cs.calendar.models.Appointment;

import java.util.ArrayList;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public interface DataSource {
    ArrayList<Appointment> getAllAppointments();
    Appointment insertAppointment(Appointment ap);
    void updateAppointment(Appointment ap);
    void deleteAppointment(Appointment ap);
}
