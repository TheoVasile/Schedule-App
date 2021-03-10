package com.example.scheduleapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import java.text.DecimalFormat;

public class RoutineView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routine_view);

        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String startTime = intent.getStringExtra("startTime");
        int startHour = Integer.parseInt(startTime.split(":")[0]);
        int startMinute = Integer.parseInt(startTime.split(":")[1].split(" ")[0]);
        String endTime = intent.getStringExtra("endTime");
        int endHour = Integer.parseInt(endTime.split(":")[0]);
        int endMinute = Integer.parseInt(endTime.split(":")[1].split(" ")[0]);
        float difference = ((float) endHour + (float) endMinute / 60) - ((float) startHour + (float) startMinute / 60);

        TextView routineTitle = findViewById(R.id.routine_view_title);
        routineTitle.setText(title);

        TextView routineStartTime = findViewById(R.id.routine_view_start_time);
        routineStartTime.setText(startTime);

        TextView routineEndTime = findViewById(R.id.routine_view_end_time);
        routineEndTime.setText(endTime);

        ImageButton backButton = findViewById(R.id.routine_back_button);
        backButton.setOnClickListener(new onBackClick());

        LinearLayout routineTimeLL = findViewById(R.id.routine_time_LL);
        DecimalFormat am = new DecimalFormat("0.00 am");
        DecimalFormat pm = new DecimalFormat("0.00 pm");
        for (int i = 0; i < 8; i++) {
            float time = ((float) startHour + (float) startMinute / 60) + i * difference / 8;
            RelativeLayout timeBar = new RelativeLayout(this);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            lp.setMargins(0, (int) (30 * this.getResources().getDisplayMetrics().density), 0, 0);
            timeBar.setLayoutParams(lp);
            TextView timeBarValue = new TextView(this);
            if (time > 12) {
                timeBarValue.setText(pm.format(time - 2));
            }
            else {
                timeBarValue.setText(am.format(time));
            }
            timeBarValue.setTypeface(ResourcesCompat.getFont(this, R.font.gothic));
            timeBarValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
            timeBar.addView(timeBarValue);
            routineTimeLL.addView(timeBar);
        }

    }

    public class onBackClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(RoutineView.this, MainActivity.class);
            startActivity(intent);
        }
    }
}
