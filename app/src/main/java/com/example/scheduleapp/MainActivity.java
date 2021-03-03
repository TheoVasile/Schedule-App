package com.example.scheduleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    LinearLayout timeLinearLayout;
    LinearLayout routineLinearLayout;
    ArrayList<RoutineModel> routineModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeLinearLayout = findViewById(R.id.time_linear_layout);
        routineLinearLayout = findViewById(R.id.routine_linear_layout);
        routineModelArrayList = new ArrayList<RoutineModel>();
        routineModelArrayList.add(new RoutineModel("Workout", "Morning bulk workout.", "10:40", "12:30"));

        for (int i = 0; i < 24; i++) {
            LinearLayout timeBar = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.time_bar, timeLinearLayout, false);
            TextView timeBarText = timeBar.findViewById(R.id.time_bar_text);
            String text = i + ":00";
            timeBarText.setText(text);

            timeLinearLayout.addView(timeBar);
        }

        float height = this.getResources().getDimension(R.dimen.gridHeight);

        for (RoutineModel routineModel : routineModelArrayList) {
            RelativeLayout routineCard = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.routine_card, routineLinearLayout, false);

            TextView routineTitle = routineCard.findViewById(R.id.routine_title);
            routineTitle.setText(routineModel.getTitle());

            TextView routineStartTime = routineCard.findViewById(R.id.routine_start_time);
            routineStartTime.setText(routineModel.getStartTime());

            TextView routineEndTime = routineCard.findViewById(R.id.routine_end_time);
            routineEndTime.setText(routineModel.getEndTime());

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins((int) height, (int) ((routineModel.getStartHour() + 0.5 + (float) routineModel.getStartMinute() / 60) * height), 0, 0);
            params.height = (int) ((routineModel.getEndHour() - routineModel.getStartHour() + (float) (routineModel.getEndMinute() - routineModel.getStartMinute()) / 60) * height);
            routineCard.setLayoutParams(params);

            routineCard.setOnClickListener(new onRoutineClick());

            routineLinearLayout.addView(routineCard);
        }

        RelativeLayout layout = findViewById(R.id.main_layout);
        LinearLayout line = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.time_line, layout, false);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lineParams.setMargins(0, (int) ((currentHour + 0.5 + (float) currentMinute / 60) * height - 15 * this.getResources().getDisplayMetrics().density), 0, 0);
        line.setLayoutParams(lineParams);

        layout.addView(line);

    }

    public class onRoutineClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent myIntent = new Intent(MainActivity.this, RoutineView.class);
            TextView title = v.findViewById(R.id.routine_title);
            TextView startTime = v.findViewById(R.id.routine_start_time);
            TextView endTime = v.findViewById(R.id.routine_end_time);
            myIntent.putExtra("title", title.getText());
            myIntent.putExtra("startTime", startTime.getText());
            myIntent.putExtra("endTime", endTime.getText());
            startActivity(myIntent);
        }
    }
}
