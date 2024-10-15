package com.example.gymprogresstracker;

import android.content.Context;
import android.os.CountDownTimer;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.LinearLayout;

import com.example.gymprogresstracker.ui.CalendarScroller;
import com.example.gymprogresstracker.ui.CalendarWeekColumn;
import com.example.gymprogresstracker.util.DayUtil;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CalendarScrollerManager {
    Context context;
    CalendarScroller calendarScroller;
    LinearLayout LLContent;
    DayUtil dayUtil;
    DatabaseManager databaseManager;

    public CalendarScrollerManager(Context context, CalendarScroller calendarScroller){
        this.context = context;
        this.calendarScroller = calendarScroller;
        LLContent = calendarScroller.getContentRoot();
        dayUtil = new DayUtil();
        databaseManager = new DatabaseManager(context);

        populateData();
    }

    int i = 0;
    int I = 0;
    LocalDate startLocalDate;
    List<Integer> weekData;
    int curr_month = -1;
    public void populateData(){

        int today_month = dayUtil.getTODAYLCDate().getMonthValue();
        int today_year = dayUtil.getTODAYLCDate().getYear();
        int start_month = today_month-3;
        int start_year = today_year;
        if(start_month < 0){start_month = 12 + start_month; start_year--;}

        startLocalDate = LocalDate.of(start_year, start_month, 1);
        weekData = new ArrayList<>();
        I = startLocalDate.getDayOfWeek().getValue()-1;
        weekData = makeDefault(weekData);

        new CountDownTimer(10000, 10) {

            @Override
            public void onTick(long millisUntilFinished) {
                if(i == 300){this.cancel();}

                if(I==7){
                    I=0;
                    Log.e("WEEK DATA", weekData.toString());
                    CalendarWeekColumn calendarWeekColumn = new CalendarWeekColumn(context, weekData, LLContent);
                    weekData = makeDefault(weekData);
                }
                I%=7;
                if(startLocalDate.getDayOfMonth() == 1){
                    CalendarWeekColumn monthName = new CalendarWeekColumn(context, getMonthName(startLocalDate.getMonthValue()), LLContent);
                    CalendarWeekColumn calendarWeekColumn = new CalendarWeekColumn(context, getWeekLabelData(), LLContent);
                }

                int totalExerciseForDate = databaseManager.getExerciseCountForDate(startLocalDate.toString());
                //Log.e("EEE", startLocalDate.toString() + " | " + totalExerciseForDate);
                if(totalExerciseForDate > 0){
                    weekData.set(I, 1);
                }
                else{
                    if(startLocalDate.getMonthValue() != curr_month){weekData.set(I,1000);}
                    else{
                        weekData.set(I, 0);
                    }
                }
                curr_month = startLocalDate.getMonthValue();
                startLocalDate = startLocalDate.plusDays(1);


                i++;
                I++;
            }

            @Override
            public void onFinish() {

            }
        }.start();

        for(int i = 0; i < 300; i++){


            I++;
        }
    }
    public List<Integer> makeDefault(List<Integer> weekData){
        weekData = new ArrayList<>();
        for(int i = 0; i < 7; i++){
            weekData.add(1000);
        }
        return weekData;
    }
    public List<Integer> getWeekLabelData(){
        List<Integer> res = new ArrayList<>();
        for(int i = 0;i<7;i++){
            res.add((i*-1)-1);
        }
        return res;
    }
    public List<Integer> getMonthName(int monthID){
        List<Integer> res= new ArrayList<>();
        res.add(monthID+5000);
        for(int i=0;i<6;i++){
            res.add(5000);
        }
        return res;
    }
}
