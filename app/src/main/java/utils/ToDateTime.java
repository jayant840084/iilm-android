package utils;

import java.util.Calendar;

/**
 * Created by sherlock on 28/8/17.
 */

public class ToDateTime {

    private Calendar calendar = Calendar.getInstance();

    public ToDateTime(long seconds) {
        calendar.setTimeInMillis(seconds);
    }

    public String getDate() {
        return calendar.get(Calendar.DATE) + " / "
                + (calendar.get(Calendar.MONTH) + 1)
                + " / " + calendar.get(Calendar.YEAR);
    }

    public String getTime() {
        return calendar.get(Calendar.HOUR)
                + " : " + calendar.get(Calendar.MINUTE)
                + " " + ((calendar.get(Calendar.AM_PM) == Calendar.AM) ? "AM" : "PM");
    }
}
