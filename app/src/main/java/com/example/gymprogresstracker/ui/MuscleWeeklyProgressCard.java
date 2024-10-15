package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.gymprogresstracker.R;

public class MuscleWeeklyProgressCard {

    Context context;
    LinearLayout parent;
    TextView TVMuscleName, TVCompletedSet, TVTotalSet, TVProgressValue, TVProgressBar;
    ImageView IVMuscleIcon;

    String muscleName;
    int completedSets;
    int totalSets;
    int DEF_WIDTH = 0;

    int primary_color, secondary_color;

    public MuscleWeeklyProgressCard(Context context, LinearLayout parent){
        this.context = context;
        this.parent = parent;
        loadComponents();
    }
    public void loadComponents(){
        TVMuscleName = parent.findViewById(R.id.TV_PROGRESS_CARD_MUSCLE_NAME);
        TVCompletedSet = parent.findViewById(R.id.TV_PROGRESS_CARD_COMPLETED_SET);
        TVTotalSet = parent.findViewById(R.id.TV_PROGRESS_CARD_TOTAL_SET);
        TVProgressValue = parent.findViewById(R.id.TV_PROGRESS_CARD_PROGRESS_VALUE);
        TVProgressBar = parent.findViewById(R.id.TV_PROGRESS_CARD_PROGRESS_BAR);
        IVMuscleIcon = parent.findViewById(R.id.IV_PROGRESS_CARD_MUSCLE_ICON);
    }
    public void setMuscleName(String muscleName){
        this.muscleName = muscleName;
        TVMuscleName.setText(muscleName);
    }
    public void setCompletedValue(int completedValue){
        this.completedSets = completedValue;
        TVCompletedSet.setText(String.valueOf(completedSets));
    }
    public void setTotalValue(int totalSets){
        this.totalSets = totalSets;
        TVTotalSet.setText("/"+totalSets);
    }
    public void setMuscleIcon(int imageResourceID){
        IVMuscleIcon.setImageResource(imageResourceID);
    }
    public void setColors(Integer primary_color, Integer secondary_color){
        this.primary_color = primary_color;
        this.secondary_color = secondary_color;

        parent.setBackgroundColor(ContextCompat.getColor(context, primary_color));
        TVProgressValue.setBackgroundColor(ContextCompat.getColor(context, secondary_color));
    }
    public void reload(){

        TVProgressValue.post(new Runnable() {
            @Override
            public void run() {
                float progress = (float)(completedSets/(float)totalSets);
                DEF_WIDTH = TVProgressValue.getWidth();
                int PROG_WIDTH = (int)(progress * DEF_WIDTH);
                progress = (int)(progress*10000);
                progress = progress/100f;

                TVProgressValue.setText(progress+"%");

                Log.e("EEE",DEF_WIDTH + " | " + PROG_WIDTH);
                RelativeLayout.LayoutParams lllp = (RelativeLayout.LayoutParams) TVProgressBar.getLayoutParams();
                lllp.width = PROG_WIDTH;
                TVProgressBar.getParent().requestLayout();
            }
        });



    }
}
