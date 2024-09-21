package com.example.gymprogresstracker.analysis;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kotlin.jvm.internal.markers.KMutableMap;

public class StrengthEfficiencyCalculator {
    List<List<String>> data;
    List<String> dateList = new ArrayList<>();
    HashMap<String, List<List<String>>> dataMap = new HashMap<>();

    public StrengthEfficiencyCalculator(List<List<String>> data) {
        this.data = data;
        parseDataByDate();
    }

    public List<Float> getStrengthEfficiency() {
        List<Float> res = new ArrayList<>();
        parseDataByDate();
        for (String date : dateList) {
            int reps = 0;
            float weight = 0;
            for (List<String> record : dataMap.get(date)) {
                reps += Integer.parseInt(record.get(3));
                weight += Float.parseFloat(record.get(2)) * Integer.parseInt(record.get(3));
            }
            res.add(weight / (float) reps);
        }
        return res;
    }

    public void parseDataByDate() {
        int I = 0;
        for (List<String> record : data) {
            String date = record.get(0);
            if (dateList.size() == 0) {
                dateList.add(date);
                dataMap.put(date, new ArrayList<>());
            }
            if (!dateList.get(dateList.size() - 1).equals(date)) {
                dateList.add(date);
                dataMap.put(date, new ArrayList<>());
            }
            dataMap.get(date).add(record);
        }
        //Log.e("e", dataMap.toString());
    }

}


