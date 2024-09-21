package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

public class ExerciseSelectorSubmitButton {
    Context context;
    TextView BTN_Submit;
    public  ExerciseSelectorSubmitButton(Context context, TextView BTN_Submit){
        this.context = context;
        this.BTN_Submit = BTN_Submit;
    }
    public void setOnClickListener(View.OnClickListener onClickListener){
        BTN_Submit.setOnClickListener(onClickListener);
    }
}
