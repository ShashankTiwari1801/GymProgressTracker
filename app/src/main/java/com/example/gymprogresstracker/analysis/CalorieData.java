package com.example.gymprogresstracker.analysis;

import android.content.Context;
import android.util.Log;

import com.example.gymprogresstracker.CalorieCalculator;
import com.example.gymprogresstracker.DatabaseManager;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.JSONHelper;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class CalorieData {
    Context context;
    DatabaseManager databaseManager;
    ExerciseDirectoryManager exerciseDirectoryManager;
    CalorieCalculator calorieCalculator;
    HashMap<String, List<List<String>>> muscleToRecords = new HashMap<>();
    HashMap<String, List<List<String>>> dateToRecords = new HashMap<>();
    List<String> dateList = new ArrayList<>();
    List<Float> calorieResult = new ArrayList<>();
    HashMap<String, Float> result = new HashMap<>();

    public CalorieData(Context context) {
        this.context = context;
        databaseManager = new DatabaseManager(context);
        calorieCalculator = new CalorieCalculator(context);
        exerciseDirectoryManager = new ExerciseDirectoryManager(new JSONHelper(context));
        load();
    }

    public void load() {
        List<String> list = databaseManager.showTables();
        HashSet<String> dates = new HashSet<>();
        for (String names : list) {
            String _id = names.substring(3);
            if (_id.charAt(0) == 'n') {
                continue;
            }
            //Log.e("e", _id);
            int id = Integer.parseInt(_id);
            String muscle = exerciseDirectoryManager.getMuscleGroup(id);
            if (!muscleToRecords.containsKey(muscle)) {
                muscleToRecords.put(muscle, new ArrayList<>());
            }
            List<List<String>> records = databaseManager.getSetList(id);
            for (List<String> temp : records) {
                dates.add(temp.get(0));
                muscleToRecords.get(muscle).add(temp);
                if (!dateToRecords.containsKey(temp.get(0))) {
                    dateToRecords.put(temp.get(0), new ArrayList<>());
                }
                dateToRecords.get(temp.get(0)).add(temp);
            }
        }
        sortDates(dates);
        calculateResult();
        //Log.e("E", muscleToRecords.toString());
    }

    public void sortDates(HashSet<String> dates) {
        dateList = new ArrayList<>(dates);
        dateList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String[] a = o1.split("-");
                String[] b = o2.split("-");
                int y1 = Integer.parseInt(a[0]);
                int y2 = Integer.parseInt(b[0]);
                int m1 = Integer.parseInt(a[1]);
                int m2 = Integer.parseInt(b[1]);
                int d1 = Integer.parseInt(a[2]);
                int d2 = Integer.parseInt(b[2]);
                if (y1 > y2) {
                    return 1;
                }
                if (y2 > y1) {
                    return -1;
                }

                if (m1 > m2) {
                    return 1;
                }
                if (m2 > m1) {
                    return -1;
                }

                if (d1 > d2) {
                    return 1;
                }
                if (d2 > d1) {
                    return -1;
                }

                return 0;
            }
        });
        Log.e("SORTED DATE", dateList.toString());
    }

    public void calculateResult() {
        for (String muscle : muscleToRecords.keySet()) {
            for (List<String> record : muscleToRecords.get(muscle)) {
                String date = record.get(0);
                int reps = Integer.parseInt(record.get(3));
                float cal = calorieCalculator.getCalories(0f, reps, muscle);
                if (!result.containsKey(date)) {
                    result.put(date, 0f);
                }
                result.put(date, result.get(date) + cal);
            }
        }
    }

    public List<Float> getResult() {
        List<Float> res = new ArrayList<>();
        for (String date : dateList) {
            res.add(result.get(date));
        }
        return res;
    }

    public HashMap<String, List<List<String>>> getDateToRecords() {
        return dateToRecords;
    }

    public List<String> getDateList() {
        return dateList;
    }

    public HashMap<String, List<List<String>>> getMuscleToRecords() {
        return muscleToRecords;
    }
}
