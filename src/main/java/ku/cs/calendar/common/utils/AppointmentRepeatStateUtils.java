package ku.cs.calendar.common.utils;

import ku.cs.calendar.common.models.*;

import java.util.HashMap;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class AppointmentRepeatStateUtils {

    private static HashMap<String, AppointmentRepeatState> states;
    static {
        states = new HashMap<String, AppointmentRepeatState>();
        states.put("Never", new AppointmentNeverRepeatState());
        states.put("Daily", new AppointmentDailyRepeatState());
        states.put("Weekly", new AppointmentWeeklyRepeatState());
        states.put("Monthly", new AppointmentMonthlyRepeatState());
    }

    public static AppointmentRepeatState getInstanceOfState(String state) {
        return states.get(state);
    }

    public static String getNameOfState(AppointmentRepeatState state) {
        String repeatStateClass = state.getClass().toString();
        String repeatState = repeatStateClass.replaceAll("class.ku.cs.calendar.common.models.Appointment","").replaceAll("RepeatState","");
        return repeatState;
    }

}
