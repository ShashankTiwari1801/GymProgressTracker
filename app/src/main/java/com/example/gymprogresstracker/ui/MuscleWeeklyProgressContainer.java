package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.JSONHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class MuscleWeeklyProgressContainer {

    GridLayout parent;
    Context context;

    List<MuscleWeeklyProgressCard> cardList = new ArrayList<>();

    public MuscleWeeklyProgressContainer(Context context, LinearLayout parent){
        this.context = context;
        this.parent = (GridLayout) parent.getChildAt(0);
        init();
    }
    public void init(){
        for(int i = 0; i < parent.getChildCount();i++){
            cardList.add(
              new MuscleWeeklyProgressCard(context, (LinearLayout) parent.getChildAt(i))
            );
        }
    }
    public List<MuscleWeeklyProgressCard> getCardList(){
        return cardList;
    }

}
