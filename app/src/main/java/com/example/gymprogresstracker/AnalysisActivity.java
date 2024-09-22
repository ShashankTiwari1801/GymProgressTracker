package com.example.gymprogresstracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymprogresstracker.analysis.WorkoutAnalyzer;
import com.example.gymprogresstracker.ui.ProgressFillBar;
import com.example.gymprogresstracker.ui.SpinnerCard;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.GraphHelper;
import com.example.gymprogresstracker.util.JSONHelper;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnalysisActivity extends AppCompatActivity {

    Context context;
    LineChart LCProgressiveOverload, LCStrengthEfficiency, LCWeightRep, LCExerciseVolume, LCCalories, LCTonnage;
    LinearLayout LL_PROGRESS_CONTAINER;
    GraphHelper graphHelper;
    ExerciseDirectoryManager exerciseDirectoryManager;
    Spinner spinner;
    SpinnerCard spinnerCard;
    WorkoutAnalyzer workoutAnalyzer;
    SpinnerCardManager spinnerCardManager;
    DatabaseManager databaseManager;
    String IntentExerciseName;
    public final int WEEKLY_SET_MAX = 20;
    HashMap<String, ProgressFillBar> progressFillBarHashMap = new HashMap<>();
    public void init() {
        this.context = this;

        LCProgressiveOverload = findViewById(R.id.LC_PROGRESSIVE_OVERLOAD);
        LCStrengthEfficiency = findViewById(R.id.LC_STRENGTH_EFFICENCY);
        LCWeightRep = findViewById(R.id.LC_WEIGHT_REP);
        LCExerciseVolume = findViewById(R.id.LC_EXERCISE_VOLUME);
        LCCalories = findViewById(R.id.LC_CALORIES);
        LCTonnage = findViewById(R.id.LC_TONNAGE);
        spinner = findViewById(R.id.SPINNER);
        LL_PROGRESS_CONTAINER = findViewById(R.id.LL_PROGRESS_CONTAINER);

        spinnerCard = new SpinnerCard(context, spinner);
        spinnerCardManager = new SpinnerCardManager(context, spinnerCard);
        databaseManager = new DatabaseManager(context);

        workoutAnalyzer = new WorkoutAnalyzer(context);
        graphHelper = new GraphHelper(context);
        exerciseDirectoryManager = new ExerciseDirectoryManager(new JSONHelper(context));
        spinnerCardManager.setExerciseDirectoryManager(exerciseDirectoryManager);
        spinnerCardManager.addOnListClickListeners(loadAnalysisOfExercise());

        graphHelper.customizeLineChart(LCProgressiveOverload);
        graphHelper.customizeLineChart(LCStrengthEfficiency);
        graphHelper.customizeLineChart(LCWeightRep);
        graphHelper.customizeLineChart(LCExerciseVolume);
        graphHelper.customizeLineChart(LCCalories);
        graphHelper.customizeLineChart(LCTonnage);

        if(IntentExerciseName != null){
            updateGraphs(IntentExerciseName);
            spinnerCard.makeInvisible();
        }
        loadPermanentGraphs();
        loadProgressBars();
    }
    public void loadProgressBars(){
        List<String> muscles = exerciseDirectoryManager.getMuscleList();
        HashMap<String, Integer> muscleToWeekCount = workoutAnalyzer.getMuscleToWeekCount();
        for(String muscle: muscles){
            progressFillBarHashMap.put(muscle, new ProgressFillBar(context, LL_PROGRESS_CONTAINER));
            progressFillBarHashMap.get(muscle).setMuscle(muscle);
            LL_PROGRESS_CONTAINER.addView(progressFillBarHashMap.get(muscle).getRoot());
            float progress = muscleToWeekCount.get(muscle)/ (float)WEEKLY_SET_MAX;
            progressFillBarHashMap.get(muscle).setProgress(progress);
        }
    }
    public void loadPermanentGraphs(){
        List<Entry> graphData = parseDataForGraph(workoutAnalyzer.getCalorieList());
        LCCalories.setData(graphHelper.getLineData(graphData,
                R.drawable.gradient_orange,
                Color.parseColor("#F4511E")));

        graphData = parseDataForGraph(workoutAnalyzer.getDailyTonnage());
        LCTonnage.setData(graphHelper.getLineData(graphData,
                R.drawable.gradient_purple,
                Color.parseColor("#5E35B1")));

    }
    public void updateGraphs(String exerciseName){
        int ID = exerciseDirectoryManager.getID(exerciseName);
        List<List<String>> data = databaseManager.getSetList(ID);
        workoutAnalyzer.analyze(data);

        List<Entry> graphData = parseDataForGraph(workoutAnalyzer.getProgressiveOverLoadData());
        LCProgressiveOverload.setData(graphHelper.getLineData(graphData,
                R.drawable.gradient_green,
                Color.parseColor("#43A047")));

        LCProgressiveOverload.invalidate();

        graphData = parseDataForGraph(workoutAnalyzer.getStrengthEfficiencyData());
        LCStrengthEfficiency.setData(graphHelper.getLineData(graphData,
                R.drawable.gradient,
                Color.parseColor("#00ACC1")));

        LCStrengthEfficiency.invalidate();

        graphData = parseDataForGraph(workoutAnalyzer.getWeightList());
        LCWeightRep.setData(graphHelper.getLineData(graphData,
                R.drawable.gradient_yellow,
                Color.parseColor("#FDD835")));

        LCWeightRep.invalidate();

        graphData = parseDataForGraph(workoutAnalyzer.getVolumeList());
        LCExerciseVolume.setData(graphHelper.getLineData(graphData,
                R.drawable.gradient_red,
                Color.parseColor("#D81B60")));

        LCExerciseVolume.invalidate();
    }
    public AdapterView.OnItemSelectedListener loadAnalysisOfExercise(){
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String exerciseName = spinnerCardManager.names.get(position);
                updateGraphs(exerciseName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }

    public List<Entry> parseDataForGraph(List<Float> data){
        int I = 0;
        List<Entry> res = new ArrayList<>();
        for(Float x: data){
            res.add(new Entry(I++, x));
        }
        return res;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_analysis);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        IntentExerciseName = intent.getStringExtra("ExName");
        init();
    }
}