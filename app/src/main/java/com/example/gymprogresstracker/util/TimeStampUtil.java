package com.example.gymprogresstracker.util;

import java.time.LocalTime;

public class TimeStampUtil {
    public TimeStampUtil(){

    }
    public String getTimeStamp() {
        String time = "";
        LocalTime currentTime = LocalTime.now();
        int hour = currentTime.getHour();
        int minute = currentTime.getMinute();
        int second = currentTime.getSecond();
        time = hour + ":" + minute + ":" + second;
        return time;
    }
}
