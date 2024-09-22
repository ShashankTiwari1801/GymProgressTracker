package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;

public class AddSetDialogCard {
    Context context;
    GridLayout parent;
    EditText ET_WEIGHT, ET_REPS;
    TextView TV_ADD_BTN;

    public AddSetDialogCard(Context context, View parent) {
        this.context = context;
        this.parent = (GridLayout) parent;
        load();
    }

    public void load() {
        ET_WEIGHT = (EditText) parent.getChildAt(2);
        ET_REPS = (EditText) parent.getChildAt(3);
        TV_ADD_BTN = (TextView) parent.getChildAt(4);
    }

    public float getWeight() {
        return Float.parseFloat(ET_WEIGHT.getText().toString());
    }

    public int getReps() {
        return Integer.parseInt(ET_REPS.getText().toString());
    }

    public void setAddBtnOnClickListener(View.OnClickListener onClickListener) {
        TV_ADD_BTN.setOnClickListener(onClickListener);
    }

    public void deFocus() {
        ET_WEIGHT.clearFocus();
        ET_REPS.clearFocus();
    }
}
