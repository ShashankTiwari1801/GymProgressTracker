package com.example.gymprogresstracker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.gymprogresstracker.analysis.WorkoutAnalyzer;
import com.example.gymprogresstracker.ui.SpinnerCard;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;

import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpinnerCardManager {
    Context context;
    SpinnerCard spinnerCard;
    DatabaseManager databaseManager;
    ExerciseDirectoryManager exerciseDirectoryManager;
    List<String> names;
    public SpinnerCardManager(Context context, SpinnerCard spinnerCard){
        this.context = context;
        databaseManager = new DatabaseManager(context);
        this.spinnerCard = spinnerCard;

        init();
    }
    public void init(){
        //addOnListClickListeners();
        //populateSpinnerCard();
    }

    public void populateSpinnerCard(){
        List<String> IDS = databaseManager.showTables();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                parseIDListToExerciseNames(IDS));

        //Log.e("", names.toString());
        spinnerCard.setAdapter(arrayAdapter);
        spinnerCard.setBackgroundColor(Color.parseColor("#00ACC1"));
    }
    public List<String> parseIDListToExerciseNames(List<String> IDs){
        names = new ArrayList<>();

        for(String s: IDs){
            if(!s.startsWith("EXC") || s.contains("null")){continue;}
            names.add(exerciseDirectoryManager.getExerciseName(Integer.parseInt(s.substring(3))));
        }

        Collections.sort(names);
        return names;
    }
    public void addOnListClickListeners(AdapterView.OnItemSelectedListener listener){
        spinnerCard.setOnItemSelectListener(listener);
    }
    public void setExerciseDirectoryManager(ExerciseDirectoryManager exerciseDirectoryManager){
        this.exerciseDirectoryManager = exerciseDirectoryManager;
        populateSpinnerCard();
    }
}
