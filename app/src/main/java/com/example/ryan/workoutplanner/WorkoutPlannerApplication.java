package com.example.ryan.workoutplanner;

import android.app.Application;

import com.example.ryan.workoutplanner.components.AppComponent;
import com.example.ryan.workoutplanner.components.DaggerAppComponent;
import com.example.ryan.workoutplanner.modules.AppModule;

/**
 * Created by Ryan on 8/21/2017.
 */

public class WorkoutPlannerApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
