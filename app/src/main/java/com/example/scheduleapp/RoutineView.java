package com.example.scheduleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RoutineView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_view);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String startTime = intent.getStringExtra("startTime");
        String endTime = intent.getStringExtra("endTime");

        TextView routineTitle = findViewById(R.id.routine_view_title);
        routineTitle.setText(title);

        TextView routineStartTime = findViewById(R.id.routine_view_start_time);
        routineStartTime.setText(startTime);

        TextView routineEndTime = findViewById(R.id.routine_view_end_time);
        routineEndTime.setText(endTime);

    }
}
