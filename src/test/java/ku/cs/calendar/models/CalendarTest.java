package ku.cs.calendar.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class CalendarTest {

    private Calendar c;

    @BeforeEach
    public void setUp() throws Exception {
        c = new Calendar();
    }

    @Test
    public void addAppointment() throws Exception {
        Appointment ap = new Appointment(new Date(31, 8, 2560 ), 1, 1, Appointment.REPEATED_NEVER);
        c.addAppointment(ap);
        Assertions.assertEquals(1, c.getAppointments(31, 8, 2560).size());
        ap = new Appointment(new Date(31, 8, 2560 ), 1, 1, Appointment.REPEATED_DAILY);
        c.addAppointment(ap);
        Assertions.assertEquals(2, c.getAppointments(31, 8, 2560).size());
        ap = new Appointment(new Date(31, 8, 2560 ), 1, 1, Appointment.REPEATED_MONTHLY);
        c.addAppointment(ap);
        Assertions.assertEquals(3, c.getAppointments(31, 8, 2560).size());
        ap = new Appointment(new Date(4, 8, 2560 ), 1, 1, Appointment.REPEATED_WEEKLY);
        c.addAppointment(ap);
        Assertions.assertEquals(3, c.getAppointments(31, 8, 2560).size());
        Assertions.assertEquals(2, c.getAppointments(4, 8, 2560).size());
        Assertions.assertEquals(3, c.getAppointments(31, 8, 2561).size());
    }

    @Test
    public void hasAppointmentsOnDate() throws Exception {
        Appointment ap = new Appointment(new Date(31, 8, 2560 ), 1, 1, Appointment.REPEATED_NEVER);
        c.addAppointment(ap);
        Assertions.assertEquals(c.hasAppointmentsOnDate(31,8,2560), true);
    }

    @Test
    public void removeAppointment() throws Exception {
        Appointment ap = new Appointment(new Date(31, 8, 2560 ), 1, 1, Appointment.REPEATED_NEVER);
        c.addAppointment(ap);
        c.removeAppointment(ap);
        Assertions.assertEquals(c.hasAppointmentsOnDate(31,8,2560), false);
    }

}