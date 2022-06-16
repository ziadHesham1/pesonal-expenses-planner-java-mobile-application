package com.example.mma7faztyv2;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.Calendar;

public class dateTime {
    private String date,time;
    public static Calendar calendar;
    public static String todaysDate;
    public static String currentTime;
    private static final String TAG = "dateTime";
    public dateTime(String date, String time) {
        this.date = date;
        this.time = time;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public static dateTime getCurrentDateTime()
    {
        calendar = Calendar.getInstance();
        todaysDate =   calendar.get(Calendar.DAY_OF_MONTH)  + "/"+ (calendar.get(Calendar.MONTH) + 1)+ "/" + calendar.get(Calendar.YEAR);
        currentTime = pad(calendar.get(Calendar.HOUR)) + ":" + pad(calendar.get(Calendar.MINUTE));
        return new dateTime(todaysDate,currentTime);
    }public static dateTime getyesterdayDateTime()
    {
        calendar = Calendar.getInstance();
        todaysDate =   calendar.get(Calendar.DAY_OF_MONTH)-1  + "/"+ (calendar.get(Calendar.MONTH) + 1)+ "/" + calendar.get(Calendar.YEAR);
        currentTime = pad(calendar.get(Calendar.HOUR)) + ":" + pad(calendar.get(Calendar.MINUTE));
        return new dateTime(todaysDate,currentTime);
    }
    public static String pad(int time) {
        if (time < 10)
            return "0" + time;
        return String.valueOf(time);

    }
    public static int getMonth() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int getYear() {
        calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }
    public static int getday()
    {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void getweek()
    {
        final ZonedDateTime input = ZonedDateTime.now();
        System.out.println(input);
        final ZonedDateTime startOfLastWeek = input.minusWeeks(1).with(DayOfWeek.MONDAY);
        System.out.println(startOfLastWeek);
        final ZonedDateTime endOfLastWeek = startOfLastWeek.plusDays(6);
        System.out.println(endOfLastWeek);
    }

}
