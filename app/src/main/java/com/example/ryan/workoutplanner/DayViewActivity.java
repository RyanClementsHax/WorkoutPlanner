package com.example.ryan.workoutplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.ryan.workoutplanner.adapters.DayViewAdapter;
import com.example.ryan.workoutplanner.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class DayViewActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private String dayOfWeek;
    private String dayDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getExtras() != null) {
            dayOfWeek = getIntent().getExtras().getString(StringConstants.DAY);
            dayDescription = getIntent().getExtras().getString(StringConstants.DAY_DESCRIPTION);
        } else {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            dayOfWeek = sharedPref.getString(StringConstants.DAY, "N/A");
            dayDescription = sharedPref.getString(StringConstants.DAY_DESCRIPTION, "N/A");
        }

        setContentView(R.layout.activity_day_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(dayOfWeek);
        getSupportActionBar().setSubtitle(dayDescription);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.add_exercise);
        fab.setOnClickListener(this);

        recyclerView = (RecyclerView)findViewById(R.id.exercises_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new DayViewAdapter(getExercises(dayOfWeek));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(StringConstants.DAY, dayOfWeek);
        editor.putString(StringConstants.DAY_DESCRIPTION, dayDescription);
        editor.commit();
    }

    private List<Exercise> getExercises(String dayOfWeek) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Back Squats", 5, 5, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Front Squats", 4, 12, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Leg Press", 4, 12, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Leg Extensions", 4, 20, 15, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Back Squats", 5, 5, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Front Squats", 4, 12, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Leg Press", 4, 12, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Leg Extensions", 4, 15, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Back Squats", 5, 5, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Front Squats", 4, 12, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Leg Press", 4, 12, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Leg Extensions", 4, 15, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Back Squats", 5, 5, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Front Squats", 4, 12, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Leg Press", 4, 12, 20, Exercise.WeightUnit.LBS, null));
        exercises.add(new Exercise("Leg Extensions", 4, 15, 20, Exercise.WeightUnit.LBS, null));

        return exercises;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddExerciseActivity.class);
        startActivity(intent);
    }
}
