package ku.cs.calendar.models;

import java.io.Serializable;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public interface AppointmentRepeatState extends Serializable{
    boolean isOnTheDate(Date apDate, int date, int month, int year);
}
