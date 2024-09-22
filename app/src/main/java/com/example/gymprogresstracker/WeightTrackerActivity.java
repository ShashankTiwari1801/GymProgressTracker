package com.example.gymprogresstracker;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalDate;
import java.util.List;

public class WeightTrackerActivity extends AppCompatActivity {
    TextView TV_MORNING, TV_NIGHT, TV_WEIGHT_SUBMIT;
    LinearLayout LL_WEIGHT_CONTENT;
    EditText ET_WEIGHT_INPUT;
    int timeOfDay = 0;
    String selectedColor = "#0EDF12";
    DatabaseManager databaseManager;

    public void INIT() {
        TV_MORNING = findViewById(R.id.TV_MORNING);
        TV_NIGHT = findViewById(R.id.TV_NIGHT);
        ET_WEIGHT_INPUT = findViewById(R.id.ET_WEIGHT_INPUT);
        TV_WEIGHT_SUBMIT = findViewById(R.id.TV_WEIGHT_SUBMIT);
        LL_WEIGHT_CONTENT = findViewById(R.id.LL_WEIGHT_CONTENT);
        databaseManager = new DatabaseManager(this);
        TV_MORNING.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSwitch(0);
            }
        });
        TV_NIGHT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSwitch(1);
            }
        });
        TV_WEIGHT_SUBMIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });
    }

    public void onClickSwitch(int type) {
        if (type == 0) {
            TV_NIGHT.setBackgroundColor(Color.parseColor("#00000000"));
            TV_MORNING.setBackgroundColor(Color.parseColor(selectedColor));
        } else {
            TV_MORNING.setBackgroundColor(Color.parseColor("#00000000"));
            TV_NIGHT.setBackgroundColor(Color.parseColor(selectedColor));
        }
        timeOfDay = type;
    }

    public void onSubmit() {
        databaseManager.makeWeightTable();
        String date = getDate();
        float weight = Float.parseFloat(ET_WEIGHT_INPUT.getText().toString());
        databaseManager.addWeightRecord(date, weight, timeOfDay);
        inflateRecords();
    }

    public String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    public void addCard(List<String> data) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.weight_card, LL_WEIGHT_CONTENT, false);
        LL_WEIGHT_CONTENT.addView(child);
        LinearLayout parent = (LinearLayout) child;
        ((TextView) parent.findViewById(R.id.TV_WEIGHT_MORNING)).setText(data.get(1) + " Kg");
        ((TextView) parent.findViewById(R.id.TV_WEIGHT_NIGHT)).setText(data.get(2) + " Kg");
        ((TextView) parent.findViewById(R.id.TV_CARD_DATE)).setText(data.get(0));
    }

    public void inflateRecords() {
        LL_WEIGHT_CONTENT.removeAllViews();
        List<List<String>> records = databaseManager.getWeightTable();
        for (List<String> temp : records) {
            addCard(temp);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weight_tracker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        INIT();
        inflateRecords();
    }

}