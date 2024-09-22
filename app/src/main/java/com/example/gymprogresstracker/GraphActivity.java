package com.example.gymprogresstracker;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.List;

public class GraphActivity extends AppCompatActivity {

    int exercise_id;
    Context context;
    LineChart lineChart;
    LineDataSet lineDataSet;
    List<ILineDataSet> iLineDataSets;
    LineData lineData;
    DatabaseManager databaseManager;
    float YMAX = 0f;
    float YMIN = 0f;
    List<Entry> weight = new ArrayList<>();
    List<Entry> reps = new ArrayList<>();
    public void init(){
        lineChart = findViewById(R.id.LineChart);
        databaseManager = new DatabaseManager(context);
        makeGraph();
        customizeChart();
    }


    public void makeGraph(){
        lineDataSet = customizeDataSet();
        //lineDataSet.setFillColor(Color.parseColor("#00ACC1"));
        iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(lineDataSet);

        lineDataSet = repsDataSet();
        iLineDataSets.add(lineDataSet);
        lineData = new LineData(iLineDataSets);

        lineChart.setData(lineData);
        //lineChart.setData(lineData);
        //lineChart.invalidate();
    }
    public LineDataSet customizeDataSet(){
        lineDataSet = new LineDataSet(weightDataSet(), "");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setColor(Color.WHITE);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setLineWidth(3);
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.gradient));
        return lineDataSet;
    }
    public LineDataSet repsDataSet(){
        lineDataSet = new LineDataSet(reps, "");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setColor(Color.WHITE);
        lineDataSet.setValueTextColor(Color.WHITE);
        lineDataSet.setMode(LineDataSet.Mode.HORIZONTAL_BEZIER);
        lineDataSet.setLineWidth(3);
        lineDataSet.setFillDrawable(ContextCompat.getDrawable(this, R.drawable.gradient_red));
        return lineDataSet;
    }
    public void customizeChart(){
        /// Customize lineChart
        YAxis yAxisL = lineChart.getAxisLeft();
        YAxis yAxisR = lineChart.getAxisRight();
        XAxis xAxisU = lineChart.getXAxis();
        xAxisU.setDrawGridLines(false);
        yAxisR.setEnabled(false);
        yAxisL.setAxisMaximum(YMAX+10);
        yAxisL.setAxisMinimum(YMIN-10);
        yAxisL.setDrawGridLines(false);
        yAxisL.setTextColor(Color.WHITE);
        lineChart.setHighlightPerDragEnabled(false);
        lineChart.setHighlightPerTapEnabled(false);
        lineChart.setDrawGridBackground(false);
        lineChart.setDrawBorders(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.setDescription(null);
        //lineChart.setBackgroundColor(Color.BLACK);
        xAxisU.setEnabled(false);
        //yAxisL.setEnabled(false);
    }
    public List<Entry> weightDataSet(){
        weight = new ArrayList<>();
        reps = new ArrayList<>();
        /*
        for(float i = 0; i <= 10; i+=0.2f){
            list.add(new Entry(i, (float) Math.sin(i)));
        }
        return list;
         */
        List<List<String>> data  = databaseManager.getSetList(exercise_id);
        int I = 0;
        for(List<String> record: data){
            weight.add(new Entry(I++, Float.parseFloat(record.get(2))));
            reps.add(new Entry(I-1, Float.parseFloat(record.get(3))));

            YMAX = Math.max(YMAX, Float.parseFloat(record.get(2)));
            YMIN = Math.min(YMIN, Float.parseFloat(record.get(2)));
        }
        return weight;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_graph);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        this.context = this;

        exercise_id = getIntent().getIntExtra("EXID",0);
        init();
    }
}