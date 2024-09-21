package com.example.gymprogresstracker.util;

import java.time.LocalDate;

public class DayUtil {
    LocalDate TODAY;
    LocalDate SELECTED_DAY;
    int SELECTED_DAY_ID;
    int TODAY_ID;
    public DayUtil(){
        TODAY = LocalDate.now();
        SELECTED_DAY = TODAY;
        TODAY_ID = TODAY.getDayOfWeek().getValue()-1;
        SELECTED_DAY_ID = TODAY_ID;
    }
    public int getTodayDayID(){
        return TODAY_ID;
    }
    public int getSelectedDayID(){
        return SELECTED_DAY_ID;
    }
    public void setSELECTED_DAY_ID(int id){
        SELECTED_DAY_ID = id;
    }
    public String getToday(){
        return TODAY.toString();
    }
}
