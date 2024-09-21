package com.example.gymprogresstracker.util;

public class Workout {
    Exercise exercise;
    int reps = 0;
    float weight = 0f;
    String date = "";
    public Workout(Exercise exercise, int reps, float weight, String date){
        this.date = date;
        this.exercise = exercise;
        this.reps = reps;
        this.weight = weight;
    }
}
