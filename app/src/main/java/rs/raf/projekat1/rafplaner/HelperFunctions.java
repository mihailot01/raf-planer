package rs.raf.projekat1.rafplaner;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import rs.raf.projekat1.rafplaner.model.Priority;

public class HelperFunctions {

    public static Date trimDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        date = calendar.getTime();
        return date;
    }

    public static String getFormattedTime(int time) {
        int hour = time / 60;
        int minute = time % 60;
        String formattedTime = "";
        if (hour < 10) {
            formattedTime += "0" + hour;
        } else {
            formattedTime += hour;
        }
        formattedTime += ":";
        if (minute < 10) {
            formattedTime += "0" + minute;
        } else {
            formattedTime += minute;
        }
        return formattedTime;
    }

    public static int getPriorityColor(Priority priority) {
        if (priority == null) {
            return R.color.gray;
        }
        switch (priority) {
            case LOW:
                return R.color.lowPriority;
            case MEDIUM:
                return R.color.mediumPriority;
            case HIGH:
                return R.color.highPriority;
        }
        return R.color.gray;
    }
}
