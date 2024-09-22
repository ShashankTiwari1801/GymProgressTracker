package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class AnalysisRowCard {
    Context context;
    GridLayout card;
    LinearLayout LL_ANALYSIS_BTN;

    public AnalysisRowCard(Context context, GridLayout card) {
        this.context = context;
        this.card = card;
        load();
    }

    public void load() {
        LL_ANALYSIS_BTN = (LinearLayout) card.getChildAt(1);
    }

    public void setCardOnClickLListener(View.OnClickListener onClickListener) {
        LL_ANALYSIS_BTN.setOnClickListener(onClickListener);
    }
}
