package com.example.ryan.workoutplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.ryan.workoutplanner.adapters.DayViewAdapter;
import com.example.ryan.workoutplanner.models.Exercise;

import java.util.ArrayList;
import java.util.List;

public class DayViewActivity extends AppCompatActivity {
    TextView textView;
    RecyclerView recyclerView;
    RecyclerView.Adapter recyclerViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String dayOfWeek = getIntent().getExtras().getString(StringConstants.DAY);
        String dayDescription = getIntent().getExtras().getString(StringConstants.DAY_DESCRIPTION);

        setContentView(R.layout.activity_day_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(dayOfWeek);
        getSupportActionBar().setSubtitle(dayDescription);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView)findViewById(R.id.exercises_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new DayViewAdapter(getExercises(dayOfWeek));
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
    }

    private List<Exercise> getExercises(String dayOfWeek) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise("Back Squats", 5, 5, null));
        exercises.add(new Exercise("Front Squats", 4, 12, null));
        exercises.add(new Exercise("Leg Press", 4, 12, null));
        exercises.add(new Exercise("Leg Extensions", 4, 15, null));
        exercises.add(new Exercise("Back Squats", 5, 5, null));
        exercises.add(new Exercise("Front Squats", 4, 12, null));
        exercises.add(new Exercise("Leg Press", 4, 12, null));
        exercises.add(new Exercise("Leg Extensions", 4, 15, null));
        exercises.add(new Exercise("Back Squats", 5, 5, null));
        exercises.add(new Exercise("Front Squats", 4, 12, null));
        exercises.add(new Exercise("Leg Press", 4, 12, null));
        exercises.add(new Exercise("Leg Extensions", 4, 15, null));
        exercises.add(new Exercise("Back Squats", 5, 5, null));
        exercises.add(new Exercise("Front Squats", 4, 12, null));
        exercises.add(new Exercise("Leg Press", 4, 12, null));
        exercises.add(new Exercise("Leg Extensions", 4, 15, null));

        return exercises;
    }

}
