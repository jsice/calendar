package ku.cs.calendar.models;

import java.util.*;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class Calendar {

    private static HashMap<Integer, String> monthName = new HashMap<Integer, String>();
    private static HashMap<String, Integer> monthNum = new HashMap<String, Integer>();
    private static HashMap<Integer, Integer> monthDay = new HashMap<Integer, Integer>();
    static {
        monthName.put(1, "January");
        monthName.put(2, "February");
        monthName.put(3, "March");
        monthName.put(4, "April");
        monthName.put(5, "May");
        monthName.put(6, "June");
        monthName.put(7, "July");
        monthName.put(8, "August");
        monthName.put(9, "September");
        monthName.put(10, "October");
        monthName.put(11, "November");
        monthName.put(12, "December");
        monthNum.put("January", 1);
        monthNum.put("February", 2);
        monthNum.put("March", 3);
        monthNum.put("April", 4);
        monthNum.put("May", 5);
        monthNum.put("June", 6);
        monthNum.put("July", 7);
        monthNum.put("August", 8);
        monthNum.put("September", 9);
        monthNum.put("October", 10);
        monthNum.put("November", 11);
        monthNum.put("December", 12);
        monthDay.put(1, 31);
        monthDay.put(2, 28);
        monthDay.put(3, 31);
        monthDay.put(4, 30);
        monthDay.put(5, 31);
        monthDay.put(6, 30);
        monthDay.put(7, 31);
        monthDay.put(8, 31);
        monthDay.put(9, 30);
        monthDay.put(10, 31);
        monthDay.put(11, 30);
        monthDay.put(12, 31);
    }

    private HashMap<Integer, HashMap<Integer, HashMap<Integer, TreeSet<Appointment>>>> appointments;

    public Calendar() {
        this.appointments = new HashMap<Integer, HashMap<Integer, HashMap<Integer, TreeSet<Appointment>>>>();

    }

    public void addAppointment(Appointment ap) {
        Date startDate = ap.getDate();
        if (!this.appointments.containsKey(startDate.getYear())) {
            this.appointments.put(startDate.getYear(), new HashMap<Integer, HashMap<Integer, TreeSet<Appointment>>>());
        }
        if (!this.appointments.get(startDate.getYear()).containsKey(startDate.getMonth())) {
            this.appointments.get(startDate.getYear()).put(startDate.getMonth(), new HashMap<Integer, TreeSet<Appointment>>());
        }
        if (!this.appointments.get(startDate.getYear()).get(startDate.getMonth()).containsKey(startDate.getDate())) {
            this.appointments.get(startDate.getYear()).get(startDate.getMonth()).put(startDate.getDate(), new TreeSet<Appointment>());
        }

        this.appointments.get(startDate.getYear()).get(startDate.getMonth()).get(startDate.getDate()).add(ap);

    }

    public TreeSet<Appointment> getAppointments(int date, int month, int year) {
        if (!this.appointments.containsKey(year)) {
            this.appointments.put(year, new HashMap<Integer, HashMap<Integer, TreeSet<Appointment>>>());
        }
        if (!this.appointments.get(year).containsKey(month)) {
            this.appointments.get(year).put(month, new HashMap<Integer, TreeSet<Appointment>>());
        }
        if (!this.appointments.get(year).get(month).containsKey(date)) {
            this.appointments.get(year).get(month).put(date, new TreeSet<Appointment>());
        }
        return this.appointments.get(year).get(month).get(date);
    }

    public static String getMonthName(int m) {
        if (m < 1 || m > 12) return null;
        return monthName.get(m);
    }

    public static int getMonthNum(String m) {
        if (!monthNum.containsKey(m)) return -1;
        return monthNum.get(m);
    }

    public static int getMonthDay(int m, int y) {
        if (m < 1 || m > 12 || y < 1) return -1;
        if (m == 2) {
            y -= 543;
            if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
                return monthDay.get(m) + 1;
            }
        }
        return monthDay.get(m);
    }

}
