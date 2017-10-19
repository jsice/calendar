package ku.cs.calendar.models;

import java.util.GregorianCalendar;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class AppointmentWeeklyRepeatState implements AppointmentRepeatState {
    public boolean isOnTheDate(Date apDate, int date, int month, int year) {
        if (year > apDate.getYear() ||
                year == apDate.getYear() && month > apDate.getMonth() ||
                year == apDate.getYear() && month == apDate.getMonth() && date >= apDate.getDate()) {
            java.util.Calendar c1 = new GregorianCalendar(year - 543, month - 1, date);
            int day1 = c1.get(java.util.Calendar.DAY_OF_WEEK);
            java.util.Calendar c2 = new GregorianCalendar(apDate.getYear() - 543, apDate.getMonth() - 1, apDate.getDate());
            int day2 = c2.get(java.util.Calendar.DAY_OF_WEEK);
            if (day1 == day2) return true;
        }

        return false;
    }
}
