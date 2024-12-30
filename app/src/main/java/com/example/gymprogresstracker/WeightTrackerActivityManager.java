package com.example.gymprogresstracker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.example.gymprogresstracker.ui.WeightActivityButton;

public class WeightTrackerActivityManager {
    Context context;
    WeightActivityButton weightActivityButton;
    public WeightTrackerActivityManager(Context context, WeightActivityButton weightActivityButton){
        this.context = context;
        this.weightActivityButton = weightActivityButton;
        init();
    }
    public void init(){
        weightActivityButton.setOnClickListener(openWeightTracerActivity());
    }
    public View.OnClickListener openWeightTracerActivity(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, WeightTrackerActivity.class);
                context.startActivity(intent);
            }
        };
    }
}
