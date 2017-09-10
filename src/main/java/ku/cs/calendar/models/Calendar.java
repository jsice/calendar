package ku.cs.calendar.models;

import jdk.nashorn.internal.objects.NativeArray;

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

    private HashMap<Integer, HashMap<Integer, HashMap<Integer, PriorityQueue<Appointment>>>> appointments;
    private ArrayList<Appointment> repeatedAppointments;

    public Calendar() {
        this.appointments = new HashMap<Integer, HashMap<Integer, HashMap<Integer, PriorityQueue<Appointment>>>>();
        this.repeatedAppointments = new ArrayList<Appointment>();
    }

    public void addAppointment(Appointment ap) {
        if (ap.getRepeated() == Appointment.REPEATED_NEVER) {
            Date startDate = ap.getDate();
            if (!this.appointments.containsKey(startDate.getYear())) {
                this.appointments.put(startDate.getYear(), new HashMap<Integer, HashMap<Integer, PriorityQueue<Appointment>>>());
            }
            if (!this.appointments.get(startDate.getYear()).containsKey(startDate.getMonth())) {
                this.appointments.get(startDate.getYear()).put(startDate.getMonth(), new HashMap<Integer, PriorityQueue<Appointment>>());
            }
            if (!this.appointments.get(startDate.getYear()).get(startDate.getMonth()).containsKey(startDate.getDate())) {
                this.appointments.get(startDate.getYear()).get(startDate.getMonth()).put(startDate.getDate(), new PriorityQueue<Appointment>());
            }

            this.appointments.get(startDate.getYear()).get(startDate.getMonth()).get(startDate.getDate()).add(ap);
        } else {
            this.repeatedAppointments.add(ap);
        }

    }

    public PriorityQueue<Appointment> getAppointments(int date, int month, int year) {
        if (!this.appointments.containsKey(year)) {
            this.appointments.put(year, new HashMap<Integer, HashMap<Integer, PriorityQueue<Appointment>>>());
        }
        if (!this.appointments.get(year).containsKey(month)) {
            this.appointments.get(year).put(month, new HashMap<Integer, PriorityQueue<Appointment>>());
        }
        if (!this.appointments.get(year).get(month).containsKey(date)) {
            this.appointments.get(year).get(month).put(date, new PriorityQueue<Appointment>());
        }
        PriorityQueue<Appointment> aps = new PriorityQueue<Appointment>(this.appointments.get(year).get(month).get(date));
        for (Appointment ap: this.repeatedAppointments) {
            if (year > ap.getDate().getYear() ||
                    year == ap.getDate().getYear() && month > ap.getDate().getMonth() ||
                    year == ap.getDate().getYear() && month == ap.getDate().getMonth() && date >= ap.getDate().getDate()) {
                int repeated = ap.getRepeated();
                if (repeated == Appointment.REPEATED_DAILY) {
                    aps.add(ap);
                } else if (repeated == Appointment.REPEATED_MONTHLY){
                    if (date == ap.getDate().getDate()) {
                        aps.add(ap);
                    }
                } else {
                    java.util.Calendar c1 = new GregorianCalendar(year - 543, month - 1, date);
                    int day1 = c1.get(java.util.Calendar.DAY_OF_WEEK);
                    java.util.Calendar c2 = new GregorianCalendar(ap.getDate().getYear() - 543, ap.getDate().getMonth() - 1, ap.getDate().getDate());
                    int day2 = c2.get(java.util.Calendar.DAY_OF_WEEK);
                    if (day1 == day2) aps.add(ap);
                }
            }
        }
        return aps;
    }

    public boolean hasAppointmentsOnDate(int date, int month, int year) {
        if (!this.appointments.containsKey(year)) {
            return false;
        }
        if (!this.appointments.get(year).containsKey(month)) {
            return false;
        }
        if (!this.appointments.get(year).get(month).containsKey(date)) {
            return false;
        }
        return this.appointments.get(year).get(month).get(date).size() != 0;
    }

    public void removeAppointment(Appointment ap) {
        if (ap.getRepeated() == Appointment.REPEATED_NEVER) {
            if (this.appointments.containsKey(ap.getDate().getYear()) &&
                    this.appointments.get(ap.getDate().getYear()).containsKey(ap.getDate().getMonth()) &&
                    this.appointments.get(ap.getDate().getYear()).get(ap.getDate().getMonth()).containsKey(ap.getDate().getDate())) {
                PriorityQueue<Appointment> t = this.appointments.get(ap.getDate().getYear()).get(ap.getDate().getMonth()).get(ap.getDate().getDate());
                if (t.contains(ap)) t.remove(ap);
            }
        } else {
            this.repeatedAppointments.remove(ap);
        }

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
