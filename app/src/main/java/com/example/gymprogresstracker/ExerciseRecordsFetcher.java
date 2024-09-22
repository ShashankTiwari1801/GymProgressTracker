package com.example.gymprogresstracker;

import android.content.Context;

import com.example.gymprogresstracker.util.Exercise;

import java.util.ArrayList;
import java.util.List;

public class ExerciseRecordsFetcher {
    Context context;
    DatabaseManager databaseManager;
    /*
    Gets a Week Day and returns the ListOfExercise of that day
     */
    List<Exercise> exercises;
    public ExerciseRecordsFetcher(Context context) {
        this.context = context;
        databaseManager = new DatabaseManager(context);
        exercises = new ArrayList<>();
    }

}
