package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.gymprogresstracker.R;

public class ProgressFillBar {
    RelativeLayout RLBar;
    TextView TVProgress, TVText;
    int W = 0;
    String muscle = "";
    Context context;
    LinearLayout parent;

    public ProgressFillBar(Context context, LinearLayout parent) {
        this.context = context;
        this.parent = parent;
        load();
    }

    public void load() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        RLBar = (RelativeLayout) layoutInflater.inflate(R.layout.progressbar, parent, false);
        RLBar.post(new Runnable() {
            @Override
            public void run() {
                W = RLBar.getWidth();
            }
        });
        TVProgress = (TextView) RLBar.getChildAt(0);
        TVText = (TextView) RLBar.getChildAt(1);
    }

    public RelativeLayout getRoot() {
        return RLBar;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
        setProgress(0.05f);
    }

    public void setProgress(float percentage) {
        RLBar.post(new Runnable() {
            @Override
            public void run() {
                float progressWidth = W * percentage;
                //TVProgress.setWidth((int)(progressWidth));
                RelativeLayout.LayoutParams lllp = (RelativeLayout.LayoutParams) TVProgress.getLayoutParams();
                lllp.width = (int) progressWidth;
                //Log.e("EEEEEEEEEEEÊ", String.valueOf(progressWidth));
                TVProgress.setLayoutParams(lllp);
                TVText.setText(muscle + " " + ((percentage * 10000) / 100) + "%");
            }
        });
        // percentage is in a fraction < 1 | ex. 43% = 0.43x
    }
}
