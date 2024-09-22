package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ExerciseRecorderHeader {
    TextView TV_BACK, TV_EXERCISE_ID, TV_EXERCISE_NAME;
    LinearLayout parent;
    Context context;

    public ExerciseRecorderHeader(Context context, LinearLayout parent) {
        this.context = context;
        this.parent = parent;
        load();
    }

    public void load() {
        TV_BACK = (TextView) parent.getChildAt(0);
        TV_EXERCISE_ID = (TextView) parent.getChildAt(1);
        TV_EXERCISE_NAME = (TextView) parent.getChildAt(2);
    }

    public void setExerciseID(int id) {
        TV_EXERCISE_ID.setText("#" + id);
    }

    public void setExerciseName(String exerciseName) {
        TV_EXERCISE_NAME.setText(exerciseName);
    }

    public void setBackButtonAction(View.OnClickListener onClickListener) {
        TV_BACK.setOnClickListener(onClickListener);
    }
}
