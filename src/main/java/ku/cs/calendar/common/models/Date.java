package ku.cs.calendar.common.models;

import java.io.Serializable;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class Date implements Serializable {

    private int date;
    private int month;
    private int year;

    public Date(int date, int month, int year) {
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
