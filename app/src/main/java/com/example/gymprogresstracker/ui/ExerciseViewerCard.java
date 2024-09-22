package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gymprogresstracker.R;

/*
THIS CARD IS DISPLAYED ON THE DAILY EXERCISE LIST
 */
public class ExerciseViewerCard {
    Context context;
    LinearLayout parent;
    GridLayout card;
    LayoutInflater layoutInflater;
    ImageView IVMuscleGroup, IVRemoveBTN;
    TextView TVExerciseName;
    int exerciseId;

    public ExerciseViewerCard(Context context, LinearLayout parent) {
        this.context = context;
        this.parent = parent;
        load();
    }

    private void load() {
        layoutInflater = LayoutInflater.from(context);
        card = (GridLayout) layoutInflater.inflate(R.layout.exercise_card, parent, false);
        IVMuscleGroup = (ImageView) card.getChildAt(0);
        TVExerciseName = (TextView) card.getChildAt(1);
        IVRemoveBTN = (ImageView) card.getChildAt(2);
    }

    public GridLayout getRootView() {
        return card;
    }

    public void setExerciseName(String name) {
        TVExerciseName.setText(name);
    }

    public void setMuscleImage(int imageResourceID) {
        IVMuscleGroup.setImageResource(imageResourceID);
    }

    public void setCardOnClickListener(View.OnClickListener listener) {
        card.setOnClickListener(listener);
    }

    public void setExitOnCardListener(View.OnClickListener listener) {
        IVRemoveBTN.setOnClickListener(listener);
    }
}
