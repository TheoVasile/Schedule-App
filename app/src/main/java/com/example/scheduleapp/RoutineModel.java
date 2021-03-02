package com.example.scheduleapp;

public class RoutineModel {

    String title;
    String description;
    String startTime;
    String endTime;

    public void RoutineModel(String title, String description, String startTime, String endTime){
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
