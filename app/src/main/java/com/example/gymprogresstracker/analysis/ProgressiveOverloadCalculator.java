package com.example.gymprogresstracker.analysis;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProgressiveOverloadCalculator {

    List<String> dateList = new ArrayList<>();
    HashMap<String, List<List<String>>> dataMap = new HashMap<>();
    List<List<String>> data = new ArrayList<>();

    public ProgressiveOverloadCalculator(List<List<String>> data) {
        /*
        Daily Progressive Overload = Dimax - Di-1max / Di-1max)  *  100

        Di = Sum(Wi * Ri) / Sum(Ri)
         */
        this.data = data;

        parseDataByDate();

        getResList();
    }

    public List<Float> getResList() {
        List<Float> res = new ArrayList<>();
        res.add(0f);
        for (int i = 1; i < dateList.size(); i++) {
            float Wi = getWeight(dataMap.get(dateList.get(i)));
            float Wi_1 = getWeight(dataMap.get(dateList.get(i - 1)));
            float progressiveOverLoad = ((Wi - Wi_1) / Wi_1) * 100f;
            res.add(progressiveOverLoad);
        }
        Log.e("", res.toString());
        return res;
    }

    public float getWeight(List<List<String>> data) {
        float weight = 0f;
        float reps = 0;
        for (List<String> record : data) {
            weight += Float.parseFloat(record.get(2));
            reps += Float.parseFloat(record.get(3));
        }
        return weight / reps;
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
        Log.e("e", dataMap.toString());
    }

    public List<Float> getResult() {
        return getResList();
    }
}
