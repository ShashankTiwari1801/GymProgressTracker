package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

public class AddExerciseButton {
    Context context;
    LinearLayout LL_AddExButton;

    public AddExerciseButton(Context context, LinearLayout LL_AddExButton) {
        this.context = context;
        this.LL_AddExButton = LL_AddExButton;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        LL_AddExButton.setOnClickListener(onClickListener);
    }
}
