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
import android.widget.TextView;

import com.example.ryan.workoutplanner.adapters.DayViewAdapter;
import com.example.ryan.workoutplanner.adapters.ExercisesSharedPreferencesAdapter;
import com.example.ryan.workoutplanner.interfaces.IRecyclerViewDataManager;
import com.example.ryan.workoutplanner.models.Exercise;

import java.util.List;

public class DayViewActivity extends AppCompatActivity implements View.OnClickListener, IRecyclerViewDataManager {
    private ExercisesSharedPreferencesAdapter exercisesAdapter;
    private RecyclerView recyclerView;
    private DayViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private String dayOfWeek;
    private String dayDescription;
    private List<Exercise> exercises;
    private TextView noExercisesMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        exercisesAdapter = new ExercisesSharedPreferencesAdapter(this, dayOfWeek);

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

        exercises = getExercises();

        recyclerView = (RecyclerView)findViewById(R.id.exercises_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new DayViewAdapter(exercises, this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);

        noExercisesMessage = (TextView)findViewById(R.id.no_exercises_message);
        setEmptyMessageIfExercisesEmpty();
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

    private List<Exercise> getExercises() {
        return exercisesAdapter.getExercises();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, AddExerciseActivity.class);
        startActivityForResult(intent, AddExerciseActivity.ADD_EXERCISE_RESULT_CODE);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(requestCode == AddExerciseActivity.ADD_EXERCISE_RESULT_CODE) {
            Exercise addExercise = (Exercise)data.getExtras().getSerializable(getResources().getString(R.string.add_exercise_result));
            addExercise(addExercise);
            scrollToBottom();
        }
    }

    @Override
    public void removeItem(int item) {
        exercisesAdapter.remove(item);
        setEmptyMessageIfExercisesEmpty();
    }

    private void addExercise(Exercise exercise) {
        exercisesAdapter.addExercise(exercise);
        recyclerViewAdapter.notifyDataSetChanged();

        noExercisesMessage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void scrollToBottom() {
        recyclerViewLayoutManager.scrollToPosition(exercises.size() - 1);
    }

    private void setEmptyMessageIfExercisesEmpty() {
        if(exercises.size() > 0) {
            noExercisesMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            noExercisesMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }
}
