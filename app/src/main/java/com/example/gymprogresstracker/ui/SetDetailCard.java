package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.gymprogresstracker.R;

public class SetDetailCard {
    Context context;
    LinearLayout card;
    LinearLayout parent;
    TextView TV_SET_ID, TV_SET_WEIGHT, TV_SET_REPS, TV_CARD_DELETE, TV_CARD_CALORIES, TV_SET_DATE;
    String date;
    int setId;
    public SetDetailCard(Context context, LinearLayout parent){
        this.context = context;
        this.parent = parent;
        load();
    }
    public void load(){
        LayoutInflater inflater = LayoutInflater.from(context);
        card = (LinearLayout) inflater.inflate(R.layout.set_card, parent, false);
        TV_SET_ID = card.getChildAt(0).findViewById(R.id.TV_CARD_SET_ID);
        TV_SET_WEIGHT = card.getChildAt(0).findViewById(R.id.TV_CARD_WEIGHT);
        TV_SET_REPS = card.getChildAt(0).findViewById(R.id.TV_CARD_REPS);
        TV_CARD_DELETE = card.getChildAt(0).findViewById(R.id.TV_CARD_REMOVE);
        TV_CARD_CALORIES = card.getChildAt(0).findViewById(R.id.TV_CARD_CAL_VIEW);
        TV_SET_DATE = card.getChildAt(0).findViewById(R.id.TV_CARD_DATE);
    }
    public LinearLayout getRoot(){
        return card;
    }
    public void setSetID(int ID){
        TV_SET_ID.setText(String.valueOf(ID));
        setId = ID;
    }
    public void setSetWeight(float weight){
        TV_SET_WEIGHT.setText(String.valueOf(weight)+" Kg");
    }
    public void setReps(int reps){
        TV_SET_REPS.setText(String.valueOf(reps));
    }
    public void setCalories(float calories){
        TV_CARD_CALORIES.setText(String.valueOf(calories));
    }
    public void setSetDate(String date){
        TV_SET_DATE.setText(date);
        this.date = date;
    }
    public void setOnRemoveBtnClickListener(View.OnClickListener onClickListener){
        TV_CARD_DELETE.setOnClickListener(onClickListener);
    }
    public String getDate(){
        return date;
    }
    public int getSetID(){
        return setId;
    }
}
