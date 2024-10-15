package com.example.gymprogresstracker.ui;
/*
M ▢
T ▢
W ▢
T ▢
F ▢
S ▢
S ▢
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gymprogresstracker.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CalendarWeekColumn {

    Context context;
    LinearLayout column;
    List<TextView> cells = new ArrayList<>();

    String[] WEEK = {"M","T","W","T","F","S","S"};
    String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    public CalendarWeekColumn(Context context, List<Integer> data, LinearLayout parent){
        this.context = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        column = (LinearLayout) inflater.inflate(R.layout.calendar_column, parent, false);
        if(parent ==null){
            Log.e("PARENT = ", "PARENT = ");
        }
        parent.addView(column);
        loadCells();
        populateCells(data);
    }
    public void loadCells(){
        for(int i = 0;i < column.getChildCount(); i++){
            cells.add((TextView) column.getChildAt(i));
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    public void populateCells(List<Integer> data){
        int i = 0;
        //Log.e("e","FOUND CELLS");
        for(int x: data){
            if(x < 0){
                cells.get(i).setText(WEEK[(x*-1)-1]);
                cells.get(i).setBackground(null);
            }
            else if(x == 0){
                cells.get(i).setBackground(context.getDrawable(R.drawable.calendar_unselected));
            }
            else if(x == 1000){
                cells.get(i).setBackground(null);
            }
            else if(x >= 5000) {
                int yr_id = x - 5000;
                if(yr_id != 0) {
                    cells.get(i).setText(MONTHS[yr_id-1]);
                }
                cells.get(i).setBackground(null);
            }
            i++;
        }
    }
}
