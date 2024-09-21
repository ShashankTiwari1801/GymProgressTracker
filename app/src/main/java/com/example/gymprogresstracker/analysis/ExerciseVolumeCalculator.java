package com.example.gymprogresstracker.analysis;

import android.util.Log;

import com.example.gymprogresstracker.util.ExerciseDirectoryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExerciseVolumeCalculator {
    List<List<String>> data;
    List<String> dateList = new ArrayList<>();
    HashMap<String, List<List<String>>> dataMap = new HashMap<>();

    public ExerciseVolumeCalculator(List<List<String>> data) {
        this.data = data;
        init();
    }

    public void init() {
        parseDataByDate();
    }

    public void parseDataByDate() {
        int I = 0;
        for (List<String> record : data) {
            String date = record.get(0);
            if (dateList.isEmpty()) {
                dateList.add(date);
                dataMap.put(date, new ArrayList<>());
            }
            if (!dateList.get(dateList.size() - 1).equals(date)) {
                dateList.add(date);
                dataMap.put(date, new ArrayList<>());
            }
            dataMap.get(date).add(record);
        }
        Log.e("e", dataMap.toString());
    }
    public List<Float> getVolume(){
        List<Float> res = new ArrayList<>();
        for(String date: dateList){
            float volume = 0f;
            for(List<String> record: dataMap.get(date)){
                volume += parseRecordToVolume(record);
            }
            res.add(volume);
        }
        return res;
    }
    public float parseRecordToVolume(List<String> record){
        float weight = Float.parseFloat(record.get(2));
        float reps = Float.parseFloat(record.get(3));
        return weight * reps;
    }
}
