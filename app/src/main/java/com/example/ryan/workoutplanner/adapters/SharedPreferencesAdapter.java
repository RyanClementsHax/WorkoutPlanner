package com.example.ryan.workoutplanner.adapters;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Ryan on 8/12/2017.
 */

public class SharedPreferencesAdapter {
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferencesAdapter(Application application, Gson gson) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application);
        this.gson = gson;
    }

    public Object getObject(String preferenceKey, Type type) {
        return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), type);
    }

    public void updateObject(String preferenceKey, Type type, Object newItem) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceKey, gson.toJson(newItem, type));
        editor.commit();
    }
}
