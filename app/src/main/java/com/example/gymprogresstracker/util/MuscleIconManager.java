package com.example.gymprogresstracker.util;

import android.content.Context;
import android.util.Log;

import com.example.gymprogresstracker.R;

import java.util.HashMap;

public class MuscleIconManager {
    Context context;
    HashMap<String, Integer> muscleToDrawableMap = new HashMap<>();
    ExerciseDirectoryManager exerciseDirectoryManager;
    public MuscleIconManager(Context context){
        this.context = context;
        exerciseDirectoryManager = new ExerciseDirectoryManager(new JSONHelper(context));
        init();
    }
    public void init(){
        muscleToDrawableMap.put("Chest", R.drawable.chest);
        muscleToDrawableMap.put("Biceps", R.drawable.biceps);
        muscleToDrawableMap.put("Triceps", R.drawable.triceps);
        muscleToDrawableMap.put("Calves", R.drawable.calves);
        muscleToDrawableMap.put("Hamstring", R.drawable.hamstring);
        muscleToDrawableMap.put("Lats", R.drawable.lats);
        muscleToDrawableMap.put("Forearms", R.drawable.forearm);
        muscleToDrawableMap.put("Quads", R.drawable.quads);
        muscleToDrawableMap.put("Shoulder", R.drawable.shoulder);
        muscleToDrawableMap.put("Traps", R.drawable.traps);
        muscleToDrawableMap.put("Back", R.drawable.back);
    }
    public Integer getMuscleIcon(int ID){
        String muscle = exerciseDirectoryManager.getMuscleGroup(ID);
        Log.e("FOUND MUSCLE", muscle);
        if(!muscleToDrawableMap.containsKey(muscle)){return muscleToDrawableMap.get("Biceps");}
        return muscleToDrawableMap.get(muscle);
    }
}
