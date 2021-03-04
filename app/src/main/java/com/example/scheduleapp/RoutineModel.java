package com.example.scheduleapp;

import java.util.ArrayList;

public class RoutineModel {

    final private String title;
    final private String description;
    final private String startTime;
    final private String endTime;
    final private int day;
    final private int month;
    final private int year;
    final private ArrayList<RoutineModel> subRoutines;
    final private ArrayList<String> goals;

    public RoutineModel(String title, String description, String startTime, String endTime, int day, int month, int year, ArrayList<RoutineModel> subRoutines, ArrayList<String> goals){
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = day;
        this.month = month;
        this.year = year;
        this.subRoutines = subRoutines;
        this.goals = goals;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String getStartTime() {
        if (this.getStartHour() > 12) {
            return this.getStartHour() - 12 + ":" + this.startTime.split(":")[1] + " pm";
        }
        else {
            return this.startTime + " am";
        }
    }

    public int getStartHour() {
        return Integer.parseInt(this.startTime.split(":")[0]);
    }

    public int getStartMinute() {
        return Integer.parseInt(this.startTime.split(":")[1]);
    }

    public String getEndTime() {
        if (this.getEndHour() > 12) {
            return this.getEndHour() - 12 + ":" + this.endTime.split(":")[1] + " pm";
        }
        else {
            return this.endTime + " am";
        }
    }

    public int getEndHour() {
        return Integer.parseInt(this.endTime.split(":")[0]);
    }

    public int getEndMinute() {
        return Integer.parseInt(this.endTime.split(":")[1]);
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public ArrayList<RoutineModel> getSubRoutines() {
        return this.subRoutines;
    }

    public ArrayList<String> getGoals() {
        return this.goals;
    }
}
