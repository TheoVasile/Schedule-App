package com.example.scheduleapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;

import java.nio.channels.Selector;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {

    LinearLayout timeLinearLayout;
    LinearLayout routineLinearLayout;
    ArrayList<RoutineModel> routineModelArrayList;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        final NavigationView navigationView = findViewById(R.id.nav_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(new DrawerArrowDrawable(this));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                                                 @Override
                                                 public void onClick(View v) {
                                                     if (drawerLayout.isDrawerOpen(navigationView)) {
                                                         drawerLayout.closeDrawer(navigationView);
                                                     }
                                                     else {
                                                         drawerLayout.openDrawer(navigationView);
                                                     }
                                                 }
                                             }
        );

        timeLinearLayout = findViewById(R.id.time_linear_layout);
        routineLinearLayout = findViewById(R.id.routine_linear_layout);
        routineModelArrayList = new ArrayList<RoutineModel>();
        ArrayList<String> goals = new ArrayList<>();
        goals.add("fitness");
        goals.add("health");
        routineModelArrayList.add(new RoutineModel("Workout", "Morning bulk workout.", "10:40", "13:30", 7, 3, 2021, new int[] {42, 148, 214}, new ArrayList<RoutineModel>(), goals));

        for (int i = 0; i < 24; i++) {
            // add new grid line for each hour
            LinearLayout timeBar = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.time_bar, timeLinearLayout, false);
            TextView timeBarText = timeBar.findViewById(R.id.time_bar_text);
            String text;
            if (i <= 12) {
                text = i + ":00 am";
            }

            else {
                text = i - 12 + ":00 pm";
            }
            timeBarText.setText(text);

            timeLinearLayout.addView(timeBar);
        }

        float height = this.getResources().getDimension(R.dimen.gridHeight); // distance between grid lines

        for (RoutineModel routineModel : routineModelArrayList) {
            // put routine on screen
            RelativeLayout routineCard = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.routine_card, routineLinearLayout, false);

            // set routine title
            TextView routineTitle = routineCard.findViewById(R.id.routine_title);
            routineTitle.setText(routineModel.getTitle());

            // set routine start time
            TextView routineStartTime = routineCard.findViewById(R.id.routine_start_time);
            routineStartTime.setText(routineModel.getStartTime());

            // set routine end time
            TextView routineEndTime = routineCard.findViewById(R.id.routine_end_time);
            routineEndTime.setText(routineModel.getEndTime());


            // int startColor = (int) Integer.parseInt(routineModel.getHEX(), 16);
            float[] hsv = {0f, 0f, 0f};
            Color.RGBToHSV(routineModel.getRGB()[0], routineModel.getRGB()[1], routineModel.getRGB()[2], hsv);
            int startColor = Color.HSVToColor(hsv);
            hsv[0] *= 0.95;
            hsv[1] *= 0.5;
            hsv[2] *= 1.2;
            int endColor = Color.HSVToColor(hsv);

            System.out.println(startColor);

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[] {startColor, endColor});
            RelativeLayout routineRL = routineCard.findViewById(R.id.routine_RL);
            routineRL.setBackgroundDrawable(gd);

            // add goal tags
            GridLayout goalsGridLayout = routineCard.findViewById(R.id.goal_grid_layout);
            for (String goal : goals) {
                CardView goalTag = (CardView) LayoutInflater.from(this).inflate(R.layout.goal_tag, routineCard, false);

                // set goal title
                TextView title = goalTag.findViewById(R.id.goal_title);
                title.setText(goal);

                goalsGridLayout.addView(goalTag);
            }

            // set position on screen
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            params.setMargins((int) height, (int) ((routineModel.getStartHour() + 0.5 + (float) routineModel.getStartMinute() / 60) * height), 0, 0);
            params.height = (int) ((routineModel.getEndHour() - routineModel.getStartHour() + (float) (routineModel.getEndMinute() - routineModel.getStartMinute()) / 60) * height);
            routineCard.setLayoutParams(params);

            routineCard.setOnClickListener(new onRoutineClick());

            routineLinearLayout.addView(routineCard);
        }

        // time line indicating current time
        RelativeLayout layout = findViewById(R.id.main_layout);
        LinearLayout line = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.time_line, layout, false);
        int currentHour = Calendar.getInstance().get(Calendar.HOUR);
        if (Calendar.getInstance().get(Calendar.PM) == Calendar.PM) {
            currentHour += 12;
        }
        int am_pm = Calendar.getInstance().get(Calendar.AM_PM);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        RelativeLayout.LayoutParams lineParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        lineParams.setMargins(0, (int) ((currentHour + am_pm * 12 + 0.5 + (float) currentMinute / 60) * height - 15 * this.getResources().getDisplayMetrics().density), 0, 0);
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
