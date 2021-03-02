package com.example.scheduleapp;

public class RoutineModel {

    private String title;
    private String description;
    private String startTime;
    private String endTime;

    public RoutineModel(String title, String description, String startTime, String endTime){
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public int getStartHour() {
        return Integer.valueOf(this.startTime.split(":")[0]);
    }

    public int getStartMinute() {
        return Integer.valueOf(this.startTime.split(":")[1]);
    }

    public String getEndTime() {
        return this.endTime;
    }

    public int getEndHour() {
        return Integer.valueOf(this.endTime.split(":")[0]);
    }

    public int getEndMinute() {
        return Integer.valueOf(this.endTime.split(":")[1]);
    }
}
