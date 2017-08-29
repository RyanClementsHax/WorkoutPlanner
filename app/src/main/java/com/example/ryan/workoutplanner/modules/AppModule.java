package com.example.ryan.workoutplanner.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;

import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.ryan.workoutplanner.adapters.DayViewAdapter;
import com.example.ryan.workoutplanner.adapters.WeekViewAdapter;
import com.example.ryan.workoutplanner.callbacks.ItemTouchHelperCallback;
import com.example.ryan.workoutplanner.interfaces.IInputValidator;
import com.example.ryan.workoutplanner.interfaces.ISharedPreferencesService;
import com.example.ryan.workoutplanner.services.SharedPreferencesService;
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
    public SharedPreferences provideSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    public ISharedPreferencesService provideSharedPreferencesAdapter(SharedPreferences sharedPreferences, Gson gson) {
        return new SharedPreferencesService(sharedPreferences, gson);
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
    public IInputValidator provideInputValidator() {
        return new InputValidator();
    }

    @Provides
    public WeekViewAdapter provideWeekViewAdapter(ViewBinderHelper viewBinderHelper) {
        return new WeekViewAdapter(viewBinderHelper);
    }

    @Provides
    public DayViewAdapter provideDayViewAdapter(ViewBinderHelper viewBinderHelper) {
        return new DayViewAdapter(viewBinderHelper);
    }

    @Provides
    public LinearLayoutManager provideLinearLayoutManager(Application application) {
        return new LinearLayoutManager(application);
    }

    @Provides
    public ViewBinderHelper provideViewBinderHelper() {
        return new ViewBinderHelper();
    }
}
