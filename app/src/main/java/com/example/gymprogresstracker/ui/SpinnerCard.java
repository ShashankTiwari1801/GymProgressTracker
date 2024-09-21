package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerCard {
    Context context;
    Spinner spinner;
    public SpinnerCard(Context context, Spinner spinner){
        this.context = context;
        this.spinner = spinner;
    }
    public void setAdapter(ArrayAdapter<String> arrayAdapter){
        spinner.setAdapter(arrayAdapter);
    }
    public void setBackgroundColor(int color){
        spinner.setBackgroundColor(color);
    }
    public void setOnItemSelectListener(AdapterView.OnItemSelectedListener listener){
        spinner.setOnItemSelectedListener(listener);
    }
    public void makeInvisible(){
        spinner.setVisibility(View.GONE);
    }
}
