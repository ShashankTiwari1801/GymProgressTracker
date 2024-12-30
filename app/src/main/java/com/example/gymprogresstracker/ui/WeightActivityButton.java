package com.example.gymprogresstracker.ui;

import android.view.View;
import android.widget.LinearLayout;

public class WeightActivityButton {
    LinearLayout LL_BTN;
    public WeightActivityButton(LinearLayout LL_BTN){
        this.LL_BTN = LL_BTN;
    }
    public void setOnClickListener(View.OnClickListener onClickListener){
        LL_BTN.setOnClickListener(onClickListener);
    }
}

