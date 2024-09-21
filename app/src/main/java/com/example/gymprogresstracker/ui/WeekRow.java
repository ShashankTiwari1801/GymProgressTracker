package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class WeekRow {
    Context context;
    LinearLayout LLWeekRow;
    View parent;
    GridLayout GLContainer;
    List<WeekDay> weekDays = new ArrayList<>();
    String className = this.getClass().getName();

    public WeekRow(Context context, LinearLayout LLWeekRow, View parent){
        this.context = context;
        this.LLWeekRow = LLWeekRow;
        this.parent = parent;
        load();
    }
    public void load(){
        GLContainer = (GridLayout) LLWeekRow.getChildAt(0);
        for(int i = 0; i < GLContainer.getChildCount(); i+=2){
            View container = GLContainer.getChildAt(i);
            WeekDay weekDay = new WeekDay(context, container);
            weekDays.add(weekDay);
        }
    }
    public WeekDay getDay(int dayId){
        return weekDays.get(dayId);
    }
    public List<WeekDay> getChildren(){
        return weekDays;
    }
}
