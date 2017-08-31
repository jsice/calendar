package ku.cs.calendar.models;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class Appointment implements Comparable<Appointment> {

    private Date date;
    private int hr;
    private int min;
    private String title;
    private String description;

    public Appointment(Date date, int hr, int min) {
        this.date = date;
        this.title = "Untitled Appointment";
        this.description = "No description.";
        this.hr = hr;
        this.min = min;
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

    public int compareTo(Appointment ap) {
        if (hr < ap.hr) return -1;
        if (hr > ap.hr) return 1;
        if (min < ap.min) return -1;
        if (min > ap.min) return 1;
        return 0;
    }
}
