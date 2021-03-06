package com.demo.android.demoapplication;

import java.util.Date;

/**
 * Created by DELL on 7/15/2018.
 */

public class DateUtil {

    public static long getTimeDiff(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : "+ endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;


        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;


        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        System.out.printf(
                " %d hours, %d minutes, %d seconds%n",
                elapsedHours, elapsedMinutes, elapsedSeconds);

        return elapsedHours + elapsedMinutes + elapsedSeconds;
    }
}
