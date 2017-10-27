package ku.cs.calendar.server.datasources;

import ku.cs.calendar.common.models.Appointment;

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
