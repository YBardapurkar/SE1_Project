package com.se1.team3.campuscarrental;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.US);

    public static Calendar getNextStartTime() {
        Calendar calendar = Calendar.getInstance();
        calendar = getNextTime(calendar.getTimeInMillis());

        Calendar endTime = Calendar.getInstance();
        Integer[] end = getClosingHours(calendar.get(Calendar.DAY_OF_WEEK));
        setTime(endTime, end);

        if (!calendar.before(endTime)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Integer[] start = getOpeningHours(calendar.get(Calendar.DAY_OF_WEEK));
            setTime(calendar, start);
        }
        return calendar;
    }

    public static Calendar getNextEndTime(Calendar startTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(startTime.getTimeInMillis());
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        Calendar endTime = Calendar.getInstance();
        Integer[] end = getClosingHours(calendar.get(Calendar.DAY_OF_WEEK));
        setTime(endTime, end);

        if (!calendar.before(endTime)) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            Integer[] start = getOpeningHours(calendar.get(Calendar.DAY_OF_WEEK));
            setTime(calendar, start);
        }
        return calendar;
    }

    public static Calendar getNextTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);

        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.add(Calendar.HOUR_OF_DAY, 1);

        return calendar;
    }

    private static Integer[] getClosingHours(int dayOfWeek) {
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return new Integer[]{17, 0, 0};
        } else {
            return new Integer[]{20, 0, 0};
        }
    }

    private static Integer[] getOpeningHours(int dayOfWeek) {
        if (dayOfWeek == Calendar.SUNDAY) {
            return new Integer[]{12, 0, 0};
        } else {
            return new Integer[]{8, 0, 0};
        }
    }

    private static void setTime(Calendar calendar, Integer[] time) {
        calendar.set(Calendar.HOUR_OF_DAY, time[0]);
        calendar.set(Calendar.MINUTE, time[1]);
        calendar.set(Calendar.SECOND, time[2]);
    }

    public static String toDateTimeString(long timestamp) {
        return dateTimeFormat.format(new Date(timestamp));
    }

    public static String toDateString(long timestamp) {
        return dateFormat.format(new Date(timestamp));
    }

    public static String toTimeString(long timestamp) {
        return timeFormat.format(new Date(timestamp));
    }
}
