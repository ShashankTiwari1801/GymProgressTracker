package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.widget.LinearLayout;

public class ExerciseSetRecorderList {
    Context context;
    LinearLayout parent;
    public ExerciseSetRecorderList(Context context, LinearLayout parent){
        this.context = context;
        this.parent = parent;
    }
    public void addSetCard(SetDetailCard setDetailCard){
        parent.addView(setDetailCard.getRoot(),0);
    }
}
