package com.example.ryan.workoutplanner.adapters;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ryan.workoutplanner.models.DayOfWeek;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Ryan on 8/12/2017.
 */

public class SharedPreferencesAdapter {
    private SharedPreferences sharedPreferences;
    private String preferenceKey;
    private Object item;
    private Gson gson;
    private Type type;

    public SharedPreferencesAdapter(Context context, Type type, String preferenceKey) {
        this.sharedPreferences = context.getSharedPreferences(preferenceKey, Context.MODE_PRIVATE);
        this.preferenceKey = preferenceKey;
        this.gson = new Gson();
        this.type = type;

        item = gson.fromJson(sharedPreferences.getString(preferenceKey, ""), type);
    }

    public Object getItem() {
        return item;
    }

    public void updateItem(Object newItem) {
        item = newItem;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceKey, gson.toJson(newItem, type));
        editor.commit();
    }
}
