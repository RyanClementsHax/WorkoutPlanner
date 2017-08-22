package com.example.ryan.workoutplanner.components;

import com.example.ryan.workoutplanner.activities.AddAndEditExerciseActivity;
import com.example.ryan.workoutplanner.activities.DayViewActivity;
import com.example.ryan.workoutplanner.activities.WeekViewActivity;
import com.example.ryan.workoutplanner.adapters.DayViewAdapter;
import com.example.ryan.workoutplanner.adapters.WeekViewAdapter;
import com.example.ryan.workoutplanner.modules.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Ryan on 8/21/2017.
 */

@Singleton
@Component(modules = { AppModule.class })
public interface AppComponent {
    void inject(WeekViewActivity activity);
    void inject(DayViewActivity activity);
    void inject(AddAndEditExerciseActivity activity);
}
