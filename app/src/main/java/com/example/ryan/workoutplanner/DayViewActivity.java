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

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.ryan.workoutplanner.adapters.DayViewAdapter;
import com.example.ryan.workoutplanner.adapters.ExercisesSharedPreferencesAdapter;
import com.example.ryan.workoutplanner.interfaces.IRecyclerViewDataManager;
import com.example.ryan.workoutplanner.models.Exercise;

import java.util.List;

public class DayViewActivity extends AppCompatActivity implements IRecyclerViewDataManager {
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

        if(getIntent().getExtras() != null) {
            dayOfWeek = getIntent().getExtras().getString(StringConstants.DAY);
            dayDescription = getIntent().getExtras().getString(StringConstants.DAY_DESCRIPTION);
        } else {
            SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
            dayOfWeek = sharedPref.getString(StringConstants.DAY, "N/A");
            dayDescription = sharedPref.getString(StringConstants.DAY_DESCRIPTION, "N/A");
        }

        exercisesAdapter = new ExercisesSharedPreferencesAdapter(this, dayOfWeek);

        setContentView(R.layout.activity_day_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(dayOfWeek);
        getSupportActionBar().setSubtitle(dayDescription);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.add_exercise);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddAndEditExerciseActivity.class);
                startActivityForResult(intent, AddAndEditExerciseActivity.ADD_EXERCISE_RESULT_CODE);
            }
        });

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
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra(getResources().getString(R.string.request_code), requestCode);
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_CANCELED) {
            return;
        }
        if(requestCode == AddAndEditExerciseActivity.ADD_EXERCISE_RESULT_CODE) {
            Exercise addExercise = (Exercise)data.getExtras().getSerializable(getResources().getString(R.string.add_exercise_result));
            addExercise(addExercise);
            scrollToPosition(exercises.size() - 1);
        } else if(requestCode == AddAndEditExerciseActivity.EDIT_EXERCISE_RESULT_CODE) {
            Exercise editExercise = (Exercise)data.getExtras().getSerializable(getResources().getString(R.string.edit_exercise_result));
            editExercise(editExercise);
            scrollToPosition(exercises.indexOf(editExercise));
        }
    }

    @Override
    public void removeItem(int position) {
        exercisesAdapter.remove(position);
        setEmptyMessageIfExercisesEmpty();
    }

    @Override
    public void editItem(int position) {
        Exercise exercise = exercises.get(position);
        Intent intent = new Intent(this, AddAndEditExerciseActivity.class);
        intent.putExtra(getResources().getString(R.string.edit_exercise), exercise);
        startActivityForResult(intent, AddAndEditExerciseActivity.EDIT_EXERCISE_RESULT_CODE);
    }

    private void addExercise(Exercise exercise) {
        exercisesAdapter.addExercise(exercise);
        recyclerViewAdapter.notifyDataSetChanged();

        noExercisesMessage.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    private void editExercise(Exercise exercise) {
        exercisesAdapter.editExercise(exercise);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void scrollToPosition(int position) {
        recyclerViewLayoutManager.scrollToPosition(position);
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
