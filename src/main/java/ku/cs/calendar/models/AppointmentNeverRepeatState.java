package ku.cs.calendar.models;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class AppointmentNeverRepeatState implements AppointmentRepeatState {

    public boolean isOnTheDate(Date apDate, int date, int month, int year) {
        return apDate.getDate() == date && apDate.getMonth() == month && apDate.getYear() == year;
    }
}
