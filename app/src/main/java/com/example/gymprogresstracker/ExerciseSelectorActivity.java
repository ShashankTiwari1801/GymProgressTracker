package com.example.gymprogresstracker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymprogresstracker.ui.ExerciseSelectorActivityCard;
import com.example.gymprogresstracker.ui.ExerciseSelectorList;
import com.example.gymprogresstracker.ui.ExerciseSelectorSubmitButton;
import com.example.gymprogresstracker.util.ExerciseDirectoryManager;
import com.example.gymprogresstracker.util.JSONHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ExerciseSelectorActivity extends AppCompatActivity {

    Context context;
    TextView BTN_Submit;
    LinearLayout LL_ExerciseSelectorList;
    JSONHelper jsonHelper;
    ExerciseDirectoryManager exerciseDirectoryManager;
    ExerciseSelectorActivityCard exerciseSelectorActivityCard;
    ExerciseSelectorList exerciseSelectorList;
    ExerciseSelectorSubmitButton exerciseSelectorSubmitButton;
    Set<Integer> selectedExerciseIDs = new HashSet<>();
    int DAY_ID;
    DatabaseManager databaseManager;
    public void init(){
        Intent intent = getIntent();
        DAY_ID = intent.getIntExtra("DAY",0);

        this.context = this;
        databaseManager = new DatabaseManager(context);

        BTN_Submit = findViewById(R.id.BTN_Submit);
        LL_ExerciseSelectorList = findViewById(R.id.LL_ExerciseList);


        jsonHelper = new JSONHelper(context);
        exerciseDirectoryManager = new ExerciseDirectoryManager(jsonHelper);
        exerciseSelectorList = new ExerciseSelectorList(context, LL_ExerciseSelectorList);
        exerciseSelectorSubmitButton = new ExerciseSelectorSubmitButton(context ,BTN_Submit);
        loadExerciseIDs();
        loadView();

        exerciseSelectorSubmitButton.setOnClickListener(submitButtonOnClickListener());
    }

    public void loadView(){
        List<String> ids = exerciseDirectoryManager.getIDList();
        HashMap<String, List<ExerciseSelectorActivityCard>> groupCard = new HashMap<>();
        String muscleGroup = "";
        for(String id: ids){

            int ID = Integer.parseInt(id);

            muscleGroup = exerciseDirectoryManager.getMuscleGroup(ID);
            if(!groupCard.containsKey(muscleGroup)){
                groupCard.put(muscleGroup, new ArrayList<>());
            }

            exerciseSelectorActivityCard = new ExerciseSelectorActivityCard(context,exerciseSelectorList.getRoot(), false);
            exerciseSelectorActivityCard.setExerciseID(ID);
            exerciseSelectorActivityCard.setExerciseName(exerciseDirectoryManager.getExerciseName(ID));
            exerciseSelectorActivityCard.setOnClickListener(setCardOnClickListener(exerciseSelectorActivityCard));

            if(selectedExerciseIDs.contains(ID)){
                exerciseSelectorActivityCard.select();
                selectedExerciseIDs.remove(ID);
            }
            groupCard.get(muscleGroup).add(exerciseSelectorActivityCard);
        }
        displayLoadedCards(groupCard);
    }
    public void displayLoadedCards(HashMap<String, List<ExerciseSelectorActivityCard>> groupCard){
        for(String key: groupCard.keySet()){

            exerciseSelectorActivityCard = new ExerciseSelectorActivityCard(context,exerciseSelectorList.getRoot(), true);
            exerciseSelectorActivityCard.setExerciseName(key);

            exerciseSelectorList.addExerciseSelectorActivityCard(exerciseSelectorActivityCard);

            for(ExerciseSelectorActivityCard temp: groupCard.get(key)){
                exerciseSelectorList.addExerciseSelectorActivityCard(temp);
            }
        }
    }
    public void loadExerciseIDs(){
        String QRY = "SELECT * FROM D" + DAY_ID + ";";
        String[] args = null;
        List<List<String>> records = databaseManager.getRecords(QRY, args);
        for(List<String> temp: records){
            selectedExerciseIDs.add(Integer.parseInt(temp.get(0)));
        }
    }
    public View.OnClickListener setCardOnClickListener(ExerciseSelectorActivityCard exerciseSelectorActivityCard){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedExerciseIDs.contains(exerciseSelectorActivityCard.getExerciseID())){
                    selectedExerciseIDs.remove(exerciseSelectorActivityCard.getExerciseID());
                    exerciseSelectorActivityCard.deSelect();
                }
                else{
                    selectedExerciseIDs.add(exerciseSelectorActivityCard.getExerciseID());
                    exerciseSelectorActivityCard.select();
                }
                Log.e("SELECTED IDS", selectedExerciseIDs.toString());
            }
        };
    }

    public View.OnClickListener submitButtonOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseManager.addRecord(DAY_ID, new ArrayList<>(selectedExerciseIDs));
                finish();
            }
        };
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }
}