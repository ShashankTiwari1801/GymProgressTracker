package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.widget.TextView;

import com.example.gymprogresstracker.CalorieCalculator;

public class CalorieViewerCard {
    Context context;
    TextView TVCalorieViewer;
    public CalorieViewerCard(Context context, TextView TVCalorieViewer){
        this.context = context;
        this.TVCalorieViewer = TVCalorieViewer;
    }
    public void setCalorieValue(float calorieValue){
        TVCalorieViewer.setText(String.valueOf(calorieValue));
    }
}
