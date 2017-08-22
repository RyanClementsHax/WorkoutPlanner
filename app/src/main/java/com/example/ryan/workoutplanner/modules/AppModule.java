package com.example.ryan.workoutplanner.modules;

import android.app.Application;
import android.support.v7.widget.LinearLayoutManager;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.ryan.workoutplanner.adapters.DayViewAdapter;
import com.example.ryan.workoutplanner.adapters.SharedPreferencesAdapter;
import com.example.ryan.workoutplanner.adapters.WeekViewAdapter;
import com.example.ryan.workoutplanner.callbacks.ItemTouchHelperCallback;
import com.example.ryan.workoutplanner.validators.InputValidator;
import com.google.gson.Gson;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Ryan on 8/21/2017.
 */

@Module
public class AppModule {
    private Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    public SharedPreferencesAdapter provideSharedPreferencesAdapter(Application application, Gson gson) {
        return new SharedPreferencesAdapter(application, gson);
    }

    @Provides
    public Gson provideGson() {
        return new Gson();
    }

    @Provides
    public ItemTouchHelperCallback provideItemTouchHelperCallback() {
        return new ItemTouchHelperCallback();
    }

    @Provides
    public InputValidator provideInputValidator() {
        return new InputValidator();
    }

    @Provides
    public WeekViewAdapter provideWeekViewAdapter() {
        return new WeekViewAdapter();
    }

    @Provides
    public DayViewAdapter provideDayViewAdapter() {
        return new DayViewAdapter();
    }

    @Provides
    public LinearLayoutManager provideLinearLayoutManager(Application application) {
        return new LinearLayoutManager(application);
    }
}
