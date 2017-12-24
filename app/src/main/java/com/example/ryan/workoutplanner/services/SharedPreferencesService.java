package com.example.ryan.workoutplanner.services;

import android.content.SharedPreferences;

import com.example.ryan.workoutplanner.interfaces.ISharedPreferencesService;
import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by Ryan on 8/12/2017.
 */

public class SharedPreferencesService implements ISharedPreferencesService {
    private SharedPreferences sharedPreferences;
    private Gson gson;

    public SharedPreferencesService(SharedPreferences sharedPreferences, Gson gson) {
        this.sharedPreferences = sharedPreferences;
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

    public boolean getBoolean(String preferenceKey, boolean defaultValue) {
        return sharedPreferences.getBoolean(preferenceKey, defaultValue);
    }

    public void putBoolean(String preferenceKey, boolean value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(preferenceKey, value);
        editor.commit();
    }
}
