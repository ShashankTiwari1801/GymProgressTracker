package com.example.gymprogresstracker.widget.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.example.gymprogresstracker.R;

public class Card {
    Context context;
    LinearLayout card;

    public Card(Context context) {
        this.context = context;
    }

    public void load() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        card = (LinearLayout) layoutInflater.inflate(R.layout.exercise_card, null, false);
    }

    public void setText(String data) {
        TextView temp = (TextView) card.getChildAt(0);
        temp.setTypeface(ResourcesCompat.getFont(context, R.font.norwester));
        temp.setText(data);
    }

    public LinearLayout getRoot() {
        return card;
    }
}
