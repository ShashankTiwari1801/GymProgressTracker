package com.example.gymprogresstracker;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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

import com.example.gymprogresstracker.util.GraphHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeightTrackerActivity extends AppCompatActivity {
    TextView TV_MORNING, TV_NIGHT, TV_WEIGHT_SUBMIT;
    LinearLayout LL_WEIGHT_CONTENT;
    EditText ET_WEIGHT_INPUT;
    int timeOfDay = 0;
    String selectedColor = "#0EDF12";
    LineChart LC_BODY_WEIGHT;
    DatabaseManager databaseManager;
    List<Float> bodyWeightList = new ArrayList<>();

    public void INIT() {
        TV_MORNING = findViewById(R.id.TV_MORNING);
        ET_WEIGHT_INPUT = findViewById(R.id.ET_WEIGHT_INPUT);
        TV_WEIGHT_SUBMIT = findViewById(R.id.TV_WEIGHT_SUBMIT);
        LL_WEIGHT_CONTENT = findViewById(R.id.LL_WEIGHT_CONTENT);
        LC_BODY_WEIGHT = findViewById(R.id.LC_BODY_WEIGHT);
        databaseManager = new DatabaseManager(this);
        TV_WEIGHT_SUBMIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmit();
            }
        });

        INIT_GRAPH();
    }

    public void INIT_GRAPH(){
        LC_BODY_WEIGHT.clear();
        LC_BODY_WEIGHT.invalidate();
        GraphHelper graphHelper = new GraphHelper(this);
        graphHelper.customizeLineChart(LC_BODY_WEIGHT);

        List<Entry> graphData  = graphHelper.parseDataForGraph(getWeights());
        Log.e("---", graphData.toString());
        LC_BODY_WEIGHT.setData(graphHelper.getLineData(graphData,
                R.drawable.gradient_red,
                Color.parseColor("#D81B60")));
        LC_BODY_WEIGHT.invalidate();
    }
    public void resetGraph(){
        List<Entry> graphData  = new GraphHelper(this).parseDataForGraph(new ArrayList<>());

        LC_BODY_WEIGHT.setData(new GraphHelper(this).getLineData(graphData,
                R.drawable.gradient_red,
                Color.parseColor("#D81B60")));
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

    public List<Float> getWeights(){
        return bodyWeightList;
    }
    public void addCard(List<String> data) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View child = inflater.inflate(R.layout.weight_card, LL_WEIGHT_CONTENT, false);
        LL_WEIGHT_CONTENT.addView(child,0);
        LinearLayout parent = (LinearLayout) child;
        ((TextView) parent.findViewById(R.id.TV_WEIGHT_MORNING)).setText(data.get(1) + " Kg");
        bodyWeightList.add(Float.parseFloat(data.get(1)));
        ((TextView) parent.findViewById(R.id.TV_CARD_DATE)).setText(data.get(0));

        ((TextView) parent.findViewById(R.id.TV_WEIGHT_CARD_REMOVE)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeRecord(data.get(0));
            }
        });
    }

    public void inflateRecords() {
        LL_WEIGHT_CONTENT.removeAllViews();
        List<List<String>> records = databaseManager.getWeightTable();
        for (List<String> temp : records) {
            addCard(temp);
        }

        INIT_GRAPH();
    }

    public void removeRecord(String date){
        databaseManager.removeWeightRecord(date);
        inflateRecords();
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