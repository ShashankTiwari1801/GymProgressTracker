package com.example.gymprogresstracker;

import android.content.Context;
import android.util.Log;

import com.example.gymprogresstracker.util.Exercise;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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
