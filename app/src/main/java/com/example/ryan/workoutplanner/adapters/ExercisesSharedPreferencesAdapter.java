package com.example.ryan.workoutplanner.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.ryan.workoutplanner.models.Exercise;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 8/11/2017.
 */

public class ExercisesSharedPreferencesAdapter {
    private SharedPreferences sharedPreferences;
    private List<Exercise> exercises;
    private String preferenceKey;

    public ExercisesSharedPreferencesAdapter(Context context, String preferenceKey) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.preferenceKey = preferenceKey;
    }

    public List<Exercise> getExercises() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Exercise>>(){}.getType();
        exercises = gson.fromJson(sharedPreferences.getString(preferenceKey, ""), listType);

        if(exercises == null) {
            exercises = new ArrayList<>();
        }

        return exercises;
    }

    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        putObject(exercises);
    }

    public void remove(int position) {
        exercises.remove(position);
        putObject(exercises);
    }

    private void putObject(Object value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(preferenceKey, gson.toJson(value));
        editor.commit();
    }
}
