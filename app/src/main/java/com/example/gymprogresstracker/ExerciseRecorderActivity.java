package com.example.gymprogresstracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymprogresstracker.ui.AddSetDialogCard;
import com.example.gymprogresstracker.ui.ExerciseRecorderHeader;
import com.example.gymprogresstracker.ui.ExerciseSetRecorderList;
import com.example.gymprogresstracker.ui.SetDetailCard;
import com.example.gymprogresstracker.util.DayUtil;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.JSONHelper;

import java.util.List;

public class ExerciseRecorderActivity extends AppCompatActivity {

    int exerciseID;
    Context context;
    ExerciseRecorderHeader exerciseRecorderHeader;
    SetDetailCard setDetailCard;
    ExerciseSetRecorderList exerciseSetRecorderList;
    AddSetDialogCard addSetDialogCard;
    ExerciseDirectoryManager exerciseDirectoryManager;
    JSONHelper jsonHelper;
    LinearLayout LLMain, LLExerciseHeader, LLAddSet, LLSetList;
    DatabaseManager databaseManager;
    DayUtil dayUtil;
    CalorieCalculator calorieCalculator;
    TextView TV_GRAPH_BTN;

    public void initComponents() {
        // INIT UI VIEWS
        LLMain = findViewById(R.id.main);
        LLExerciseHeader = findViewById(R.id.LL_EX_HEADER);
        LLAddSet = findViewById(R.id.LL_ADD_SET);
        LLSetList = findViewById(R.id.LL_SET_LIST);
        TV_GRAPH_BTN = findViewById(R.id.TV_GRAPH_BTN);

        // INIT UTILITIES
        jsonHelper = new JSONHelper(context);
        exerciseDirectoryManager = new ExerciseDirectoryManager(jsonHelper);
        databaseManager = new DatabaseManager(context);
        dayUtil = new DayUtil();
        calorieCalculator = new CalorieCalculator(context);

        // INIT UI COMPONENTS
        exerciseRecorderHeader = new ExerciseRecorderHeader(context, LLExerciseHeader);
        initExerciseRecorderHeader();
        setDetailCard = new SetDetailCard(context, LLSetList);
        exerciseSetRecorderList = new ExerciseSetRecorderList(context, LLSetList);
        initExerciseSetRecorderList();
        addSetDialogCard = new AddSetDialogCard(context, LLAddSet.getChildAt(0));
        initAddSetDialogueCard();
        initGraphBtn();
    }

    public void initExerciseRecorderHeader() {
        String exerciseName = exerciseDirectoryManager.getExerciseName(exerciseID);
        exerciseRecorderHeader.setExerciseID(exerciseID);
        exerciseRecorderHeader.setExerciseName(exerciseName);

        exerciseRecorderHeader.setBackButtonAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initExerciseSetRecorderList() {
        LLSetList.removeAllViews();
        List<List<String>> setList = databaseManager.getSetList(exerciseID);
        for (List<String> record : setList) {

            String date = record.get(0);
            String setNo = record.get(1);
            String weight = record.get(2);
            String reps = record.get(3);

            SetDetailCard card = new SetDetailCard(context, LLSetList);

            card.setSetID(exerciseID);
            card.setReps(Integer.parseInt(reps));
            card.setSetWeight(Float.parseFloat(weight));
            card.setSetID(Integer.parseInt(setNo));
            card.setSetDate(date);
            card.setCalories(getCalories(Integer.parseInt(reps)));
            card.setOnRemoveBtnClickListener(removeSetCard(card));

            exerciseSetRecorderList.addSetCard(card);
        }
    }

    public float getCalories(int reps) {
        String exercise = exerciseDirectoryManager.getExerciseName(exerciseID);
        String muscleType = exerciseDirectoryManager.getMuscleGroup(exerciseID);
        return calorieCalculator.getCalories(0f, exercise, reps, muscleType);
    }

    public void initAddSetDialogueCard() {
        addSetDialogCard.setAddBtnOnClickListener(addSetToDataBase());
    }

    public void initGraphBtn() {
        TV_GRAPH_BTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Intent intent = new Intent(context, GraphActivity.class);
                intent.putExtra("EXID", exerciseID);
                startActivity(intent);
                */
                Intent intent = new Intent(context, AnalysisActivity.class);
                intent.putExtra("ExName", exerciseDirectoryManager.getExerciseName(exerciseID));
                startActivity(intent);
            }
        });
    }

    public View.OnClickListener removeSetCard(SetDetailCard card) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("e", "DELETED");
                databaseManager.removeExerciseSet(exerciseID, card.getDate(), card.getSetID());
                initExerciseSetRecorderList();
            }
        };
    }

    public View.OnClickListener addSetToDataBase() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int id = exerciseID;
                String date = dayUtil.getToday();
                int setId = databaseManager.getLastSetID(id, date) + 1;
                float weight = addSetDialogCard.getWeight();
                int reps = addSetDialogCard.getReps();
                databaseManager.addSetInExercise(id, date, setId, weight, reps);
                removeKeyboard();
                addSetDialogCard.deFocus();
                initExerciseSetRecorderList();
            }
        };
    }

    public void removeKeyboard(){
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise_recorder);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.context = this;
        Intent intent = getIntent();
        exerciseID = intent.getIntExtra("EX_ID", 0);
        initComponents();
    }
}