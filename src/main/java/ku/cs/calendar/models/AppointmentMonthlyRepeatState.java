package ku.cs.calendar.models;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class AppointmentMonthlyRepeatState implements AppointmentRepeatState {
    public boolean isOnTheDate(Date apDate, int date, int month, int year) {
        if (year > apDate.getYear() ||
                year == apDate.getYear() && month > apDate.getMonth() ||
                year == apDate.getYear() && month == apDate.getMonth() && date >= apDate.getDate())
            if (date == apDate.getDate())
                return true;
        return false;
    }
}
