package com.example.gymprogresstracker;

import android.content.Context;
import android.view.View;

import com.example.gymprogresstracker.ui.WeekDay;
import com.example.gymprogresstracker.ui.WeekRow;
import com.example.gymprogresstracker.util.DayUtil;

import java.time.LocalDate;

public class WeekRowManager {
    Context context;
    WeekRow weekRow;
    int selectedDayId = -1;
    int dayOfWeekId = -1;
    LocalDate currentDate;
    private final String[] WEEKDAYS = new String[]{"MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"};
    DayUtil dayUtil;
    DailyExerciseViewerManager dailyExerciseViewerManager;
    public WeekRowManager(Context context, WeekRow weekRow, DayUtil dayUtil){
        this.context = context;
        this.weekRow = weekRow;
        this.dayUtil = dayUtil;
        init();
    }
    public void init(){
        setOnClickFunctionality();
        currentDate = LocalDate.now();
        dayOfWeekId = getDayOfWeek();
        setWeekRowValues();
    }
    public void setWeekRowValues(){
        int i = 0;
        for(WeekDay weekDay: weekRow.getChildren()){
            weekDay.setDayName(WEEKDAYS[i]);
            weekDay.setDayDate(currentDate.minusDays(dayOfWeekId - i).getDayOfMonth());
            if(i == dayOfWeekId){
                weekDay.highlight();
            }
            i++;
        }
    }
    public void setOnClickFunctionality(){
        /*
        When a WeekDay is Clicked():
            1. populate the DailyExerciseView with the contents of the respective dayId
            2. select the clicked Weekday card
            3. deSelect the previous selected card (if any)
         */
        int i = 0;
        for(WeekDay weekDay: weekRow.getChildren()){
            weekDay.setOnClickListener(onCardClickListener(i));
            i++;
        }
    }
    public View.OnClickListener onCardClickListener(int selectedId){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Deselect the previously selected Weekday
                if(selectedDayId != -1){
                    weekRow.getDay(selectedDayId).deSelect();
                }
                // If clicked the sameDay twice, de-select the Weekday and load the DayOfWeek
                if(selectedId == selectedDayId){
                    dayUtil.setSELECTED_DAY_ID(dayUtil.getTodayDayID());
                    weekRow.getDay(selectedDayId).deSelect();
                    selectedDayId = dayUtil.getTodayDayID();
                    dailyExerciseViewerManager.fetchData(selectedDayId);
                    return;
                }
                weekRow.getDay(selectedId).select();
                selectedDayId = selectedId;
                dailyExerciseViewerManager.fetchData(selectedDayId);
                dayUtil.setSELECTED_DAY_ID(selectedDayId);
                //Log.e("SELECTDEDDDDDDD", String.valueOf(dayUtil.getSelectedDayID()));
            }
        };
    }
    public void setDailyExerciseViewerManager(DailyExerciseViewerManager dailyExerciseViewerManager){
        this.dailyExerciseViewerManager = dailyExerciseViewerManager;
        dailyExerciseViewerManager.fetchData(dayUtil.getTodayDayID());
    }
    public int getDayOfWeek(){
        return currentDate.getDayOfWeek().getValue()-1;
    }
}
