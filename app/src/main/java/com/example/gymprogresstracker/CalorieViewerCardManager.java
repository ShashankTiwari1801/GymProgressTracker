package com.example.gymprogresstracker;

import android.content.Context;

import com.example.gymprogresstracker.ui.CalorieViewerCard;
import com.example.gymprogresstracker.util.DayUtil;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;

import java.util.List;

public class CalorieViewerCardManager {
    Context context;
    CalorieViewerCard calorieViewerCard;
    DayUtil dayUtil;
    CalorieCalculator calorieCalculator;
    DatabaseManager databaseManager;
    ExerciseDirectoryManager exerciseDirectoryManager;
    public CalorieViewerCardManager(Context context, CalorieViewerCard calorieViewerCard, DayUtil dayUtil, DatabaseManager databaseManager, ExerciseDirectoryManager exerciseDirectoryManager){
        this.context = context;
        this.calorieViewerCard = calorieViewerCard;
        this.dayUtil = dayUtil;
        this.databaseManager = new DatabaseManager(context);
        this.exerciseDirectoryManager = exerciseDirectoryManager;

        calorieCalculator = new CalorieCalculator(context);
        fetchData(dayUtil.getTodayDayID());
    }
    public void fetchData(int dayId){
        List<Integer> exerciseIds = databaseManager.getRecord(dayId);

        String  todayDate = dayUtil.getToday();
        float total_calories = 0;
        for(int id: exerciseIds){
            String QRY = "SELECT * FROM EXC"+id + " WHERE EX_DATE = ? ;";
            String[] args = {todayDate};
            List<List<String>> records = databaseManager.getRecords(QRY, args);
            //   public float getCalories(float body_weight, String exercise, int reps, String muscleType) {
            for(List<String> record: records){
                total_calories += getCalorie(id, record);
            }
        }
        total_calories = (int)(total_calories * 100);
        total_calories = total_calories/100f;
        calorieViewerCard.setCalorieValue(total_calories);
    }

    public float getCalorie(int id, List<String> record){
        float body_weight = 0f;
        String exercise = exerciseDirectoryManager.getExerciseName(id);
        String muscle = exerciseDirectoryManager.getMuscleGroup(id);
        int reps = Integer.parseInt(record.get(3));

        return  calorieCalculator.getCalories(body_weight, exercise, reps, muscle);
    }
    public void reload(){
        fetchData(dayUtil.getTodayDayID());
    }
}
