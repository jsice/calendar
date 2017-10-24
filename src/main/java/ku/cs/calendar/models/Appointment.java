package ku.cs.calendar.models;

import java.io.Serializable;

/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class Appointment implements Comparable<Appointment>, Serializable {

    public static int REPEATED_NEVER = 0;
    public static int REPEATED_DAILY = 1;
    public static int REPEATED_WEEKLY = 2;
    public static int REPEATED_MONTHLY = 3;


    private int id = -1;
    private Date date;
    private int hr;
    private int min;
    private String title;
    private String description;
    private AppointmentRepeatState state;

    public Appointment(Date date, int hr, int min, AppointmentRepeatState state) {
        this.date = date;
        this.title = "Untitled Appointment";
        this.description = "No description.";
        this.hr = hr;
        this.min = min;
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getHr() {
        return hr;
    }

    public int getMin() {
        return min;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int compareTo(Appointment ap) {
        if (hr < ap.hr) return -1;
        if (hr > ap.hr) return 1;
        if (min < ap.min) return -1;
        if (min > ap.min) return 1;
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (this.id == -1)
            this.id = id;
    }

    public boolean isOnTheDate(int date, int month, int year) {
        return this.state.isOnTheDate(this.date, date, month, year);
    }

    public void setRepeated(AppointmentRepeatState state) {
        this.state = state;
    }

    public AppointmentRepeatState getRepeated() {
        return this.state;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
