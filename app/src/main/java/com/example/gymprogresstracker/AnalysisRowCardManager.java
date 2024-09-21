package com.example.gymprogresstracker;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.gymprogresstracker.ui.AnalysisRowCard;

public class AnalysisRowCardManager {
    Context context;
    AnalysisRowCard analysisRowCard;
    public AnalysisRowCardManager(Context context, AnalysisRowCard analysisRowCard){
        this.context = context;
        this.analysisRowCard = analysisRowCard;
        init();
    }
    public void init(){
        analysisRowCard.setCardOnClickLListener(openAnalysisActivity());
    }
    public View.OnClickListener openAnalysisActivity(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AnalysisActivity.class);
                context.startActivity(intent);
            }
        };
    }
}
