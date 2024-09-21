package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.widget.LinearLayout;

public class ExerciseSelectorList {
    Context context;
    LinearLayout LL_List;
    public ExerciseSelectorList(Context context, LinearLayout LL_List){
        this.context = context;
        this.LL_List = LL_List;
    }
    public void clear(){
        LL_List.removeAllViews();
    }
    public void addExerciseSelectorActivityCard(ExerciseSelectorActivityCard exerciseSelectorActivityCard){
        LL_List.addView(exerciseSelectorActivityCard.getRoot());
    }
    public LinearLayout getRoot(){
        return LL_List;
    }
}
