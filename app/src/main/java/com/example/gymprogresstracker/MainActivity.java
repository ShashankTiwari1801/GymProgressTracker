package com.example.gymprogresstracker;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymprogresstracker.ui.AddExerciseButton;
import com.example.gymprogresstracker.ui.AnalysisRowCard;
import com.example.gymprogresstracker.ui.CalorieViewerCard;
import com.example.gymprogresstracker.ui.DailyExerciseViewer;
import com.example.gymprogresstracker.ui.WeekRow;
import com.example.gymprogresstracker.util.DayUtil;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.JSONHelper;

public class MainActivity extends AppCompatActivity {

    private LinearLayout LLWeekDayRow, LLAddEx, LLContents, LLWeight;
    ScrollView SVExerciseViewer;
    TextView TVCalView;
    DatabaseManager databaseManager;
    Context context;
    WeekRow weekRow;
    DailyExerciseViewer dailyExerciseViewer;
    WeekRowManager weekRowManager;
    DailyExerciseViewerManager dailyExerciseViewerManager;
    LinearLayout parent;
    JSONHelper jsonHelper;
    ExerciseDirectoryManager exerciseDirectory;
    AddExerciseButton addExerciseButton;
    AddExerciseButtonManager addExerciseButtonManager;
    DayUtil dayUtil;
    CalorieViewerCard calorieViewerCard;
    CalorieViewerCardManager calorieViewerCardManager;
    GridLayout GL_ANALYSIS_ROW;
    AnalysisRowCard analysisRowCard;
    AnalysisRowCardManager analysisRowCardManager;
    public void componentsInit(){
        context = this;
        parent = findViewById(R.id.main);
        LLWeekDayRow = findViewById(R.id.LLWeekDayRow);
        LLAddEx = findViewById(R.id.LLAddExerciseBTN);
        LLContents = findViewById(R.id.LLContents);
        TVCalView = findViewById(R.id.TVDailyCaloriesBurned);
        SVExerciseViewer = findViewById(R.id.SVExerciseViewer);
        GL_ANALYSIS_ROW = findViewById(R.id.GL_ANALYSIS_ROW);

        //INIT UTILITIES
        jsonHelper = new JSONHelper(context);
        exerciseDirectory = new ExerciseDirectoryManager(jsonHelper);
        dayUtil = new DayUtil();

        // INIT UI COMPONENTS
        weekRow = new WeekRow(context, LLWeekDayRow, parent);
        dailyExerciseViewer = new DailyExerciseViewer(context, SVExerciseViewer);
        addExerciseButton = new AddExerciseButton(context, LLAddEx);
        calorieViewerCard = new CalorieViewerCard(context, TVCalView);
        analysisRowCard = new AnalysisRowCard(context, GL_ANALYSIS_ROW);

        // INIT UI MANAGERS
        weekRowManager = new WeekRowManager(context, weekRow, dayUtil);
        dailyExerciseViewerManager = new DailyExerciseViewerManager(context, dailyExerciseViewer,exerciseDirectory);
        weekRowManager.setDailyExerciseViewerManager(dailyExerciseViewerManager);
        addExerciseButtonManager = new AddExerciseButtonManager(context, addExerciseButton, dayUtil);
        calorieViewerCardManager = new CalorieViewerCardManager(context, calorieViewerCard, dayUtil, databaseManager, exerciseDirectory);
        analysisRowCardManager = new AnalysisRowCardManager(context, analysisRowCard);
    }

    private void INIT(){
        /* no-op */
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        INIT();
        componentsInit();
        //addExercise.reinflateOnResume();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(this.getClass().getName(), "RESUME");
        dailyExerciseViewerManager.fetchData(dayUtil.getSelectedDayID());
        calorieViewerCardManager.reload();
    }
}
