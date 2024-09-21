package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/*
THIS IS STRICTLY FOR UI INTERACTIONS
FOR ANY LOGIC OR OTHER INTERACTIONS USE DailyExerciseViewerManager
 */
public class DailyExerciseViewer {
    Context context;
    ScrollView SVExerciseViewer;
    LinearLayout LLContents;
    public DailyExerciseViewer(Context context, ScrollView SVExerciseViewer){
        this.context = context;
        this.SVExerciseViewer = SVExerciseViewer;
        load();
    }
    public void load(){
        LLContents = (LinearLayout) SVExerciseViewer.getChildAt(0);
    }

    public LinearLayout getRoot(){
        return LLContents;
    }
    public void addExerciseViewerCard(ExerciseViewerCard exerciseViewerCard){
        LLContents.addView(exerciseViewerCard.getRootView());
    }
    public void removeExerciseViewerCard(ExerciseViewerCard exerciseViewerCard){
        LLContents.removeView(exerciseViewerCard.getRootView());
    }
    public void clear(){
        LLContents.removeAllViews();
    }
}
