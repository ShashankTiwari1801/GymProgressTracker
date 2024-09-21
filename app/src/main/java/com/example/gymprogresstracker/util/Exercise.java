package com.example.gymprogresstracker.util;

public class Exercise {
    public int ID = 0;
    public String name = "";
    public String muscleGroup = "";
    public Exercise(int ID){
        this.ID = ID;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setMuscleGroup(String muscleGroup){
        this.muscleGroup = muscleGroup;
    }
}
