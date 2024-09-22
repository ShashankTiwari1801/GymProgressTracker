package com.example.gymprogresstracker.analysis;

import android.util.Log;

import com.example.gymprogresstracker.util.DayUtil;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WeeklyMuscleSet {
    List<List<String>> data;
    DayUtil dayUtil;
    HashMap<String, List<List<String>>> muscleToRecords = new HashMap<>();
    List<String> dateList = new ArrayList<>();
    Set<String> weekdays = new HashSet<>();
    HashMap<String, Integer> muscleToWeekCount = new HashMap<>();
    String mon = "", sun = "";
    ExerciseDirectoryManager exerciseDirectoryManager;

    public WeeklyMuscleSet(HashMap<String, List<List<String>>> muscleToRecords, List<String> dateList, ExerciseDirectoryManager exerciseDirectoryManager) {
        this.muscleToRecords = muscleToRecords;
        this.dateList = dateList;
        this.exerciseDirectoryManager = exerciseDirectoryManager;
        dayUtil = new DayUtil();
        load();
        getWeekdays();
        calculateWeeklyMuscleSet();
    }

    public void load() {
        int dow = dayUtil.getTodayDayID();
        LocalDate today = LocalDate.now();
        mon = today.minusDays(dow).toString();
        sun = today.minusDays(dow).plusDays(6).toString();
        Log.e("MON | SUN", mon + "  |  " + sun);
    }

    public void getWeekdays() {
        for (String date : dateList) {
            if (isWeekDay(date)) {
                weekdays.add(date);
            }
        }
        Log.e("DAYS IN WEEK", weekdays.toString());
    }

    public void calculateWeeklyMuscleSet() {
        for (String muscle : exerciseDirectoryManager.getMuscleList()) {
            muscleToWeekCount.put(muscle, 0);
        }
        for (String muscle : muscleToRecords.keySet()) {
            for (List<String> record : muscleToRecords.get(muscle)) {
                if (weekdays.contains(record.get(0))) {
                    muscleToWeekCount.put(muscle, muscleToWeekCount.get(muscle) + 1);
                }
            }
        }
        Log.e("EEEEE", muscleToWeekCount.toString());
    }

    public boolean isWeekDay(String date) {
        String[] M = mon.split("-");
        String[] S = sun.split("-");
        String[] T = date.split("-");

        if (Integer.parseInt(T[0]) < Integer.parseInt(M[0])) {
            return false;
        }
        if (Integer.parseInt(T[1]) < Integer.parseInt(M[1])) {
            return false;
        }
        if (Integer.parseInt(T[2]) < Integer.parseInt(M[2])) {
            return false;
        }

        if (Integer.parseInt(T[0]) > Integer.parseInt(S[0])) {
            return false;
        }
        if (Integer.parseInt(T[1]) > Integer.parseInt(S[1])) {
            return false;
        }
        return Integer.parseInt(T[2]) <= Integer.parseInt(S[2]);
    }

    public HashMap<String, Integer> getMuscleToWeekCount() {
        return muscleToWeekCount;
    }
}
