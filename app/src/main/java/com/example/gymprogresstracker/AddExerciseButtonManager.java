package com.example.gymprogresstracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.gymprogresstracker.ui.AddExerciseButton;
import com.example.gymprogresstracker.util.DayUtil;

public class AddExerciseButtonManager {
    Context context;
    AddExerciseButton addExerciseButton;
    int selectedDayID;
    DayUtil dayUtil;
    public AddExerciseButtonManager(Context context, AddExerciseButton addExerciseButton, DayUtil dayUtil){
        this.context = context;
        this.addExerciseButton = addExerciseButton;
        this.dayUtil =dayUtil;
        selectedDayID = dayUtil.getSelectedDayID();
        init();
    }

    public void openExerciseSelectorActivity(){
        selectedDayID = dayUtil.getSelectedDayID();
        Intent intent = new Intent(context, ExerciseSelectorActivity.class);
        intent.putExtra("DAY", selectedDayID);
        Log.e("GOPT", String.valueOf(selectedDayID));
        context.startActivity(intent);
    }
    public void init(){
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openExerciseSelectorActivity();
            }
        });
    }
}
