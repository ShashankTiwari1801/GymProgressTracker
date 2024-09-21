package com.example.gymprogresstracker;

import android.content.Context;
import android.util.Log;

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

public class RenameDatabase {

    Context context;
    JSONObject jsonObject;
    DatabaseManager databaseManager;
    HashMap<String, ArrayList<String>> EXERCISE_MAP = new HashMap<>();

    HashMap<Integer, String> ExerciseMap = new HashMap<>();
    HashMap<String, Integer> NEWMAP = new HashMap<>();
    int I = 0;
    public void readJSON(){

        try {
            InputStream inputStream = context.getAssets().open("workouts.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();
            jsonObject = new JSONObject(jsonString.toString());
            //Log.e("e", jsonObject.getJSONArray("Chest Exercises").toString());

            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String keys = it.next();
                EXERCISE_MAP.put(keys, new ArrayList<>());
            }
            for(String key:EXERCISE_MAP.keySet()){
                try {
                    for (int i = 0;i<jsonObject.getJSONArray(key).length();i++){
                        EXERCISE_MAP.get(key).add((String)jsonObject.getJSONArray(key).get(i));
                        //addExercise((String)jsonObject.getJSONArray(key).get(i),I++,false);
                        ExerciseMap.put(I++, (String)jsonObject.getJSONArray(key).get(i));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void readNewJson(){
        try {
            InputStream inputStream = context.getAssets().open("workoutjson.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();
            jsonObject = new JSONObject(jsonString.toString());
            //Log.e("e", jsonObject.getJSONArray("Chest Exercises").toString());
            for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                String id = it.next();
                jsonObject.getJSONArray(id);
                //Log.e("", String.valueOf(id)+ " | " + jsonObject.getJSONArray(id).toString());
                NEWMAP.put( (String) jsonObject.getJSONArray(id).get(0), Integer.parseInt(id));
                Log.e((String) jsonObject.getJSONArray(id).get(0), (String) "" + Integer.parseInt(id));
            }

        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }
    }
    public void getExerciseTableFromDatabase(){
        List<String> tabs = databaseManager.showTables();
        Log.e("dhfkjafghkahsdfkahjsdfjkahskdfjhaskdfhk", tabs.toString());
        for(String ids: tabs){
            int id = Integer.parseInt(ids.substring(2));
            Log.e("fhsfjhkjsdfhaksdjf", String.valueOf(id) + " | " + ExerciseMap.get(id) + " | " + NEWMAP.get(ExerciseMap.get(id)));
            String OLD = "EX" + id;
            String NEW = "EXC" + NEWMAP.get(ExerciseMap.get(id));
            //renameDataBaseTable(OLD, NEW);
        }
    }
    public void updateDailyTable(){
        for(int i = 0; i< 7 ;i++){
            List<Integer> data = databaseManager.getRecord(i);
            databaseManager.clearTable(i);
            for(int x: data){
                databaseManager.addRecord(i, NEWMAP.get(ExerciseMap.get(i)));
            }
        }
    }

    public void renameDataBaseTable(String OLD, String NEW){
        databaseManager.renameDatabase(OLD, NEW);
    }

    public RenameDatabase(Context context, DatabaseManager databaseManager){
        this.context = context;
        this.databaseManager = databaseManager;

        readNewJson();
        readJSON();
        getExerciseTableFromDatabase();
        //updateDailyTable();
        Log.e("RENAMEEEEEEE", ExerciseMap.toString());
    }
}
