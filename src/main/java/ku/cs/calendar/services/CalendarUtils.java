package ku.cs.calendar.services;

import java.util.HashMap;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class CalendarUtils {

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

    public static boolean isValidDate(int d, int m, int y) {
        y -= 543;
        if ((y % 4 == 0 && y % 100 != 0) || y % 400 == 0) {
            if (m == 2 && d == 29) return true;
        }
        if (m > 0 && m < 13)
            if (d > 0 && d <= monthDay.get(m)) return true;
        return false;
    }
}
