package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.gymprogresstracker.R;

public class WeekDay {
    Context context;
    LinearLayout LLWeekDay;
    TextView TVDayName, TVDayDate;
    String dayName = "";
    int dayDate = 0;
    public WeekDay(Context context, View LLWeekDay){
        this.context = context;
        this.LLWeekDay = (LinearLayout) LLWeekDay;
        load();
    }
    private void load(){
        TVDayName = (TextView) LLWeekDay.getChildAt(0);
        TVDayDate = (TextView) LLWeekDay.getChildAt(1);
        dayName = TVDayName.getText().toString();
        dayDate = Integer.parseInt(TVDayDate.getText().toString());
    }
    public void setDayName(String dayName){
        this.dayName = dayName;
        TVDayName.setText(dayName);
    }
    public void setDayDate(int dayDate){
        this.dayDate = dayDate;
        TVDayDate.setText(String.valueOf(dayDate));
    }

    public String toString(){
        return String.format("dayName = %s | dayDate = %d", dayName, dayDate);
    }

    public void highlight(){
        TVDayDate.setBackground(ContextCompat.getDrawable(context, R.drawable.down));
    }
    public void select(){
        LLWeekDay.setBackground(ContextCompat.getDrawable(context, R.drawable.up));
    }
    public void deSelect(){
        LLWeekDay.setBackground(null);
    }
    public void setOnClickListener(View.OnClickListener onClickListener){
        LLWeekDay.setOnClickListener(onClickListener);
    }
}
