package com.example.gymprogresstracker.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ExerciseDirectoryManager {
    /*
    ExerciseMap = {
        exerciseID : [ExerciseName, TargetMuscle]
    }
     */
    HashMap<Integer, String[]> ExerciseMap = new HashMap<>();
    JSONHelper jsonHelper;
    List<String> IDList = new ArrayList<>();

    Set<String> muscles = new HashSet<>();

    public ExerciseDirectoryManager(JSONHelper jsonHelper) {
        this.jsonHelper = jsonHelper;
        load("workoutjson.json");
    }

    public void load(String fileName) {
        JSONObject jsonObject = jsonHelper.parseJSONFile(fileName);
        try {
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String id = it.next();
                IDList.add(id);
                JSONArray jsonArray = (JSONArray) jsonObject.get(id);
                muscles.add(jsonArray.getString(1));
                ExerciseMap.put(Integer.parseInt(id), new String[]{jsonArray.getString(0), jsonArray.getString(1)});
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String getExerciseName(int id) {
        //Log.e(this.getClass().getSimpleName(), " ID + "  + id);
        return Objects.requireNonNull(ExerciseMap.get(id))[0];
    }

    public String getMuscleGroup(int id) {
        return Objects.requireNonNull(ExerciseMap.get(id))[1];
    }

    public List<String> getIDList(){
        return IDList;
    }
    public int getID(String exerciseName){
        for(int ID: ExerciseMap.keySet()){
            if(ExerciseMap.get(ID)[0].equals(exerciseName)){return  ID;}
        }
        return -1;
    }
    public List<String> getMuscleList(){
        return new ArrayList<>(muscles);
    }
}
