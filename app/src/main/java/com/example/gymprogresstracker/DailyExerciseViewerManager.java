package com.example.gymprogresstracker;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.gymprogresstracker.ui.DailyExerciseViewer;
import com.example.gymprogresstracker.ui.ExerciseViewerCard;
import com.example.gymprogresstracker.util.Exercise;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.MuscleIconManager;

import java.util.ArrayList;
import java.util.List;

/*
This class Manages an instance of DailyExerciseViewer and does the following:
    1. Fetch data from database for a particular dayID
    2. Populate the DailyExerciseViewer with ExerciseViewerCards with info
    3. give functionality to the Cards
 */

public class DailyExerciseViewerManager {
    Context context;
    DailyExerciseViewer dailyExerciseViewer;
    DatabaseManager databaseManager;
    ExerciseDirectoryManager exerciseDirectory;
    MuscleIconManager muscleIconManager;
    int dayId = 0;
    public DailyExerciseViewerManager(Context context, DailyExerciseViewer dailyExerciseViewer, ExerciseDirectoryManager exerciseDirectory) {
        this.context = context;
        this.dailyExerciseViewer = dailyExerciseViewer;
        databaseManager = new DatabaseManager(context);
        muscleIconManager = new MuscleIconManager(context);
        this.exerciseDirectory = exerciseDirectory;
        fetchData(0);
    }

    public void fetchData(int dayId) {
        this.dayId = dayId;
        String QRY = "SELECT * FROM D" + dayId  + ";";
        String[] args = {};
        List<List<String>> data = databaseManager.getRecords(QRY, args);
        List<Exercise> exercises = parseData(data);
        populateDailyExerciseViewer(exercises);
    }

    public void populateDailyExerciseViewer(List<Exercise> exercises) {
        dailyExerciseViewer.clear();
        for (Exercise exercise : exercises) {
            ExerciseViewerCard temp = new ExerciseViewerCard(context, dailyExerciseViewer.getRoot());
            temp.setExerciseName(String.valueOf(exercise.name));
            dailyExerciseViewer.addExerciseViewerCard(temp);
            temp.setExitOnCardListener(removeButtonListener(temp, exercise.ID));
            temp.setMuscleImage(muscleIconManager.getMuscleIcon(exercise.ID));
            temp.setCardOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openExerciseRecorderActivity(exercise.ID);
                }
            });
        }
    }

    public View.OnClickListener removeButtonListener(ExerciseViewerCard exerciseViewerCard, int exerciseID) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dailyExerciseViewer.removeExerciseViewerCard(exerciseViewerCard);
                databaseManager.removeRecord(dayId, exerciseID);
            }
        };
    }

    public List<Exercise> parseData(List<List<String>> data) {
        List<Exercise> parsedExercsiseList = new ArrayList<>();
        for (List<String> record : data) {
            int id = Integer.parseInt(record.get(0));
            Exercise temp = new Exercise(id);
            temp.setName(exerciseDirectory.getExerciseName(id));
            temp.setMuscleGroup(exerciseDirectory.getMuscleGroup(id));
            parsedExercsiseList.add(temp);
        }
        return parsedExercsiseList;
    }

    public void openExerciseRecorderActivity(int exerciseID){
        Intent intent = new Intent(context, ExerciseRecorderActivity.class);
        intent.putExtra("EX_ID",exerciseID);
        context.startActivity(intent);
    }
}
