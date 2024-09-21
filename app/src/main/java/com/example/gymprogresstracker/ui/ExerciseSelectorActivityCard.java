package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gymprogresstracker.R;

public class ExerciseSelectorActivityCard {
    Context context;
    LinearLayout card;
    TextView TVExerciseName, TVExerciseID;
    LinearLayout parent;
    boolean isTitle = false;
    public String SELECTED_COLOR = "#00897B";
    public String DEFAULT_COLOR = "#FF151515";
    public int exerciseID = 0;
    public ExerciseSelectorActivityCard(Context context, LinearLayout parent, boolean isTitle){
        this.context = context;
        this.parent = parent;
        this.isTitle = isTitle;
        load();
    }
    public void load(){
        LayoutInflater inflater = LayoutInflater.from(context);
        card = (LinearLayout) inflater.inflate(R.layout.exercise_list_card, parent, false);

        TVExerciseName = card.findViewById(R.id.TV_EXERCISE_LIST_NAME);
        TVExerciseID = card.findViewById(R.id.TV_EXERCISE_LIST_ID);

        if(isTitle){
            makeTitle();
        }
        //parent.addView(card);
    }

    public void setExerciseName(String name){
        TVExerciseName.setText(name);
    }
    public void setExerciseID(int id){
        this.exerciseID = id;
        TVExerciseID.setText("#" + String.valueOf(id));
    }
    public LinearLayout getRoot(){
        return card;
    }
    public void makeTitle(){
        card.setBackgroundColor(Color.parseColor("#ffffff"));
        TVExerciseName.setTextColor(Color.parseColor("#000000"));
    }
    public void select(){
        card.setBackgroundColor(Color.parseColor(SELECTED_COLOR));
    }
    public void deSelect(){
        card.setBackgroundColor(Color.parseColor(DEFAULT_COLOR));
    }
    public int getExerciseID(){
        return exerciseID;
    }
    public void setOnClickListener(View.OnClickListener onClickListener){
        card.setOnClickListener(onClickListener);
    }
}
