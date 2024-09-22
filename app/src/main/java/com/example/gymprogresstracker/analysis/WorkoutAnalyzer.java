package com.example.gymprogresstracker.analysis;

import android.content.Context;

import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.JSONHelper;

import java.util.HashMap;
import java.util.List;

/*
TRACK THE FOLLOWING METRICS

These metrics track progress for a single exercise across multiple sessions.

    Progressive Overload (Weight, Reps, or Volume for one exercise):
        Tracks the increase in weight or reps for a specific exercise, reflecting strength gains over time.

    Strength Efficiency (Weight ÷ Reps for one exercise):
        Tracks how efficiently you're lifting heavier weights with a consistent or increased rep count.

    Fatigue Index (Performance drop-off between first and last sets for one exercise):
        Measures how much strength/endurance drops during an exercise session by comparing the first set to the last.

    Power Output (Weight × Reps ÷ Time per set for one exercise):
        Measures how much work you're doing per unit of time during a set, showing explosiveness or conditioning improvements.

    Efficiency vs. Fatigue Curve:
        Compares Strength Efficiency and Fatigue Index on the same graph for a single exercise to assess how efficiently you're handling fatigue over time.


 */
public class WorkoutAnalyzer {
    Context context;
    ProgressiveOverloadCalculator progressiveOverloadCalculator;
    StrengthEfficiencyCalculator strengthEfficiencyCalculator;
    SetWiseData setWiseData;
    ExerciseVolumeCalculator exerciseVolumeCalculator;
    DailyTonnageCalculator dailyTonnageCalculator;
    CalorieData calorieData;
    WeeklyMuscleSet weeklyMuscleSet;
    ExerciseDirectoryManager exerciseDirectoryManager;

    public WorkoutAnalyzer(Context context) {
        this.context = context;
        exerciseDirectoryManager = new ExerciseDirectoryManager(new JSONHelper(context));
        calorieData = new CalorieData(context);
        dailyTonnageCalculator = new DailyTonnageCalculator(getExerciseListByDate(), getDateList());
        weeklyMuscleSet = new WeeklyMuscleSet(getExerciseListByMuscle(), getDateList(), exerciseDirectoryManager);
    }

    public void analyze(List<List<String>> data) {
        progressiveOverloadCalculator = new ProgressiveOverloadCalculator(data);
        strengthEfficiencyCalculator = new StrengthEfficiencyCalculator(data);
        setWiseData = new SetWiseData(data);
        exerciseVolumeCalculator = new ExerciseVolumeCalculator(data);
    }

    public List<Float> getProgressiveOverLoadData() {
        return progressiveOverloadCalculator.getResult();
    }

    public List<Float> getStrengthEfficiencyData() {
        return strengthEfficiencyCalculator.getStrengthEfficiency();
    }

    public List<Float> getWeightList() {
        return setWiseData.getWeights();
    }

    public List<Integer> getSetList() {
        return setWiseData.getSets();
    }

    public List<Float> getVolumeList() {
        return exerciseVolumeCalculator.getVolume();
    }

    public List<Float> getCalorieList() {
        return calorieData.getResult();
    }

    public HashMap<String, List<List<String>>> getExerciseListByDate() {
        return calorieData.getDateToRecords();
    }

    public HashMap<String, List<List<String>>> getExerciseListByMuscle() {
        return calorieData.getMuscleToRecords();
    }

    public List<String> getDateList() {
        return calorieData.getDateList();
    }

    public List<Float> getDailyTonnage() {
        return dailyTonnageCalculator.getRes();
    }

    public HashMap<String, Integer> getMuscleToWeekCount() {
        return weeklyMuscleSet.getMuscleToWeekCount();
    }
}
