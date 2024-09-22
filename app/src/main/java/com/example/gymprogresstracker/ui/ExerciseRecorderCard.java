package com.example.gymprogresstracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gymprogresstracker.R;

import java.util.List;

public class ExerciseRecorderCard {
    Context context;
    LayoutInflater inflater;

    public ExerciseRecorderCard(Context context) {
        this.context = context;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public void addCard(LinearLayout containerParent, List<String> data, View.OnClickListener removeOnClickListener, float cals) {
        View child = inflater.inflate(R.layout.set_card, containerParent, false);
        containerParent.addView(child);
        ((TextView) child.findViewById(R.id.TV_CARD_DATE)).setText(data.get(0));
        ((TextView) child.findViewById(R.id.TV_CARD_WEIGHT)).setText(data.get(2) + "Kg");
        ((TextView) child.findViewById(R.id.TV_CARD_REPS)).setText(data.get(3));
        ((TextView) child.findViewById(R.id.TV_CARD_SET_ID)).setText("#" + data.get(1));
        ((TextView) child.findViewById(R.id.TV_CARD_CAL_VIEW)).setText(cals + " kcals");
        TextView tvRemove = child.findViewById(R.id.TV_CARD_REMOVE);
        tvRemove.setOnClickListener(removeOnClickListener);
    }
}
