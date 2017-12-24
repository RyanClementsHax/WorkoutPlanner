package com.example.ryan.workoutplanner.interfaces;

import java.lang.reflect.Type;

/**
 * Created by Ryan on 8/22/2017.
 */

public interface ISharedPreferencesService {

    Object getObject(String preferenceKey, Type type);
    void updateObject(String preferenceKey, Type type, Object newItem);
    boolean getBoolean(String preferenceKey, boolean defaultValue);
    void putBoolean(String preferenceKey, boolean value);
}
