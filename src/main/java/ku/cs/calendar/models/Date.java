package ku.cs.calendar.models;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class Date {

    private int date;
    private int month;
    private int year;

    public Date(int year, int month, int date) {
        this.date = date;
        this.month = month;
        this.year = year;
    }

    public int getDate() {
        return date;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
