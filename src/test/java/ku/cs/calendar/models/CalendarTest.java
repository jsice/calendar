package ku.cs.calendar.models;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class CalendarTest {

    Calendar c;

    @Before
    public void setUp() throws Exception {
        c = new Calendar();
    }

    @Test
    public void addAppointment() throws Exception {
        Appointment ap = new Appointment(new Date(2560, 8, 31 ), 1, 1);
        c.addAppointment(ap);
        assert ap == c.getAppointments(31, 8, 2560).first();
    }

}