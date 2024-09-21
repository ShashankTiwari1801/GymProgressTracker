package com.example.gymprogresstracker.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DailyTonnageCalculator {
    HashMap<String, List<List<String>>> dateToRecords = new HashMap<>();
    List<String> dateList = new ArrayList<>();
    List<Float> res = new ArrayList<>();
    public DailyTonnageCalculator(HashMap<String, List<List<String>>> dateToRecords,List<String> dateList){
        this.dateList = dateList;
        this.dateToRecords = dateToRecords;
        load();
    }
    public void load(){
        for(String date: dateList){
            float tonnes = 0f;
            for(List<String> record: dateToRecords.get(date)){
                tonnes += calculateTonnage(record);
            }
            res.add(tonnes);
        }
    }
    public float calculateTonnage(List<String> record){
        float weight = Float.parseFloat(record.get(2));
        int reps = Integer.parseInt(record.get(3));
        return (float) (weight * reps);
    }

    public List<Float> getRes() {
        return res;
    }
}
