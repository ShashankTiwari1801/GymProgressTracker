package com.example.gymprogresstracker;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;

public class CalorieCalculator {

    HashMap<String, Float> METMap = new HashMap<>();
    final float OxygenConsumptionRate = 3.5f;
    Context context;
    JSONObject jsonObject;
    public float body_weight = 67f;


    public CalorieCalculator(Context context) {
        this.context = context;
        METMap.put("Chest", 3.5f);
        METMap.put("Shoulder", 4.0f);
        METMap.put("Biceps", 3.5f);
        METMap.put("Triceps", 3.5f);
        METMap.put("Back", 4.0f);
        METMap.put("Glutes", 4.0f);
        METMap.put("Abs", 3.8f);
        METMap.put("Calves", 3.8f);
        METMap.put("Forearms", 3.5f);
        METMap.put("Hamstrings", 4.5f);
        METMap.put("Quads", 4.5f);
        METMap.put("Traps", 4.5f);
        METMap.put("Lats", 4.5f);

    }
    public float getTimeInReps(int reps){
        /*
        1 rep = 8 sec => 8/60 min = 2/15 min
        n reps = n * 2/15
         */
        return (float) reps * (float) (2/15f);
    }
    public float getCalories(float body_weight, String exercise, int reps, String muscleType) {
        float res = 0f;
        float defaultMET = 4;
        res = METMap.get(muscleType) * this.body_weight * OxygenConsumptionRate * getTimeInReps(reps);
        res /= 200;
        return res;
    }
    public float getCalories(float body_weight, int reps, String muscle_type){
        float res = 0f;
        res = METMap.get(muscle_type) * this.body_weight * OxygenConsumptionRate * getTimeInReps(reps);
        res/=200f;
        return res;
    }
}
