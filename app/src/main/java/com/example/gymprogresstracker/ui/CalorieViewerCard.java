package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.widget.TextView;

public class CalorieViewerCard {
    Context context;
    TextView TVCalorieViewer;

    public CalorieViewerCard(Context context, TextView TVCalorieViewer) {
        this.context = context;
        this.TVCalorieViewer = TVCalorieViewer;
    }

    public void setCalorieValue(float calorieValue) {
        TVCalorieViewer.setText(String.valueOf(calorieValue));
    }
}
