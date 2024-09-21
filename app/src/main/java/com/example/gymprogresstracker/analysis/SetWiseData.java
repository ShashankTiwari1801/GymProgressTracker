package com.example.gymprogresstracker.analysis;

import java.util.ArrayList;
import java.util.List;

public class SetWiseData {
    List<List<String>> data = new ArrayList<>();
    public SetWiseData(List<List<String>> data){
        this.data = data;
    }
    public List<Float> getWeights(){
        List<Float> res = new ArrayList<>();
        for(List<String> record: data){
            res.add(Float.parseFloat(record.get(2)));
        }
        return res;
    }
    public List<Integer> getSets(){
        List<Integer> res = new ArrayList<>();
        for(List<String> record: data){
            res.add(Integer.parseInt(record.get(3)));
        }
        return res;
    }
}
