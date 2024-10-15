package com.example.gymprogresstracker.widget;

import android.content.Context;
import android.util.Log;

import com.example.gymprogresstracker.R;
import com.example.gymprogresstracker.ui.MuscleWeeklyProgressCard;
import com.example.gymprogresstracker.ui.MuscleWeeklyProgressContainer;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.JSONHelper;
import com.example.gymprogresstracker.util.MuscleIconManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MuscleWeeklyProgressContainerManager {

    Context context;
    MuscleWeeklyProgressContainer muscleWeeklyProgressContainer;

    ExerciseDirectoryManager exerciseDirectoryManager;
    MuscleIconManager muscleIconManager;

    List<MuscleWeeklyProgressCard> cardList = new ArrayList<>();
    List<String> muscleList = new ArrayList<>();

    Map<String, Integer[]> muscleColorMap = new HashMap<>();

    HashMap<String, Integer> muscleToWeekCount = new HashMap<>();
    public final int WEEKLY_SET_MAX = 20;

    public MuscleWeeklyProgressContainerManager(Context context, MuscleWeeklyProgressContainer muscleWeeklyProgressContainer){

        this.context = context;
        this.muscleWeeklyProgressContainer = muscleWeeklyProgressContainer;

        exerciseDirectoryManager = new ExerciseDirectoryManager(new JSONHelper(context));
        muscleIconManager = new MuscleIconManager(context);

        makeMuscleColorsMap();
        init();
        updateCards();

    }

    public void init(){
        cardList = muscleWeeklyProgressContainer.getCardList();
        muscleList = exerciseDirectoryManager.getMuscleList();
        Collections.sort(muscleList);
    }

    public void updateCards(){
        int I = 0;
        for(String muscle: muscleList){

            Log.i("EEE", muscle);
            cardList.get(I).setMuscleName(muscle);
            Integer[] colors = muscleColorMap.get(muscle);

            cardList.get(I).setColors(
                    colors[0],
                    colors[1]
            );


            int[] values = getValues(muscle);
            cardList.get(I).setCompletedValue(values[0]);
            cardList.get(I).setTotalValue(values[1]);
            cardList.get(I).reload();
            cardList.get(I).setMuscleIcon(muscleIconManager.getMuscleIconFromName(muscle));

            I++;
        }
    }
    public void setMuscleToValueMap(HashMap<String, Integer> muscleToWeekCount){
        this.muscleToWeekCount = muscleToWeekCount;
        Log.e("E", muscleToWeekCount.toString());
    }
    public int[] getValues(String muscleName){
        int[] res = {0,WEEKLY_SET_MAX};
        Log.e("E", muscleName);
        if(!muscleToWeekCount.containsKey(muscleName)){return res;}
        res[0] = muscleToWeekCount.get(muscleName);
        return res;
    }

    public List<String> getMuscleList(){
        return muscleList;
    }

    void makeMuscleColorsMap(){
        muscleColorMap.put("Chest", new Integer[]{R.color.chest_card, R.color.chest_card_complement});
        muscleColorMap.put("Shoulder", new Integer[]{R.color.shoulder_card, R.color.shoulder_card_complement});
        muscleColorMap.put("Biceps", new Integer[]{R.color.biceps_card, R.color.biceps_card_complement});
        muscleColorMap.put("Triceps", new Integer[]{R.color.triceps_card, R.color.triceps_card_complement});
        muscleColorMap.put("Back", new Integer[]{R.color.back_card, R.color.back_card_complement});
        muscleColorMap.put("Glutes", new Integer[]{R.color.glutes_card, R.color.glutes_card_complement});
        muscleColorMap.put("Abs", new Integer[]{R.color.abs_card, R.color.abs_card_complement});
        muscleColorMap.put("Calves", new Integer[]{R.color.calves_card, R.color.calves_card_complement});
        muscleColorMap.put("Forearms", new Integer[]{R.color.forearms_card, R.color.forearms_card_complement});
        muscleColorMap.put("Hamstrings", new Integer[]{R.color.hamstrings_card, R.color.hamstrings_card_complement});
        muscleColorMap.put("Quads", new Integer[]{R.color.quads_card, R.color.quads_card_complement});
        muscleColorMap.put("Traps", new Integer[]{R.color.traps_card, R.color.traps_card_complement});
        muscleColorMap.put("Lats", new Integer[]{R.color.lats_card, R.color.lats_card_complement});
    }
}
