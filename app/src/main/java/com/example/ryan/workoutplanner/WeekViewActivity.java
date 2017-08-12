package com.example.ryan.workoutplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.ryan.workoutplanner.adapters.SharedPreferencesAdapter;
import com.example.ryan.workoutplanner.adapters.WeekViewAdapter;
import com.example.ryan.workoutplanner.interfaces.IRecyclerViewDataManager;
import com.example.ryan.workoutplanner.models.DayOfWeek;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WeekViewActivity extends AppCompatActivity implements IRecyclerViewDataManager<DayOfWeek>{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;
    private View.OnClickListener itemClickListener;
    private SharedPreferencesAdapter sharedPreferencesAdapter;
    private List<DayOfWeek> daysOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Type dayListType = new TypeToken<List<DayOfWeek>>(){}.getType();
        this.sharedPreferencesAdapter = new SharedPreferencesAdapter(this, dayListType, getResources().getString(R.string.week_view_preference_key));
        this.daysOfWeek = (List<DayOfWeek>) sharedPreferencesAdapter.getItem();
        if(daysOfWeek == null) {
            daysOfWeek = new ArrayList<>();
        }
        setUpIfFirstTimeAppStartUp();

        itemClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dayOfWeek = ((TextView)v.findViewById(R.id.day_of_week)).getText().toString();
                String dayDescription = ((TextView)v.findViewById(R.id.day_description)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), DayViewActivity.class);
                intent.putExtra(StringConstants.DAY, dayOfWeek);
                intent.putExtra(StringConstants.DAY_DESCRIPTION, dayDescription);
                startActivity(intent);
            }
        };

        recyclerView = (RecyclerView)findViewById(R.id.week_days_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new WeekViewAdapter(this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_week_view, menu);
        return true;
    }

    @Override
    public View.OnClickListener getItemClickListener() {
        return itemClickListener;
    }

    @Override
    public void removeItem(int position) {
        //don't care about this
    }

    @Override
    public void editItem(int position) {

    }

    @Override
    public void updateItems(List<DayOfWeek> data) {
        //don't care about this
    }

    @Override
    public List<DayOfWeek> getItems() {
        return daysOfWeek;
    }

    private void setUpIfFirstTimeAppStartUp() {
        SharedPreferences preferences = getSharedPreferences(getResources().getString(R.string.week_view_preference_key), Context.MODE_PRIVATE);
        String firstTimeKey = getResources().getString(R.string.is_first_time_app_start_up);
        if(preferences.getBoolean(firstTimeKey, true)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(firstTimeKey, false);
            editor.commit();

            List<String> days = Arrays.asList(getResources().getStringArray(R.array.days_of_week));
            List<String> descriptions = Arrays.asList(getResources().getStringArray(R.array.day_descriptions));

            for(int i = 0; i < days.size(); i++) {
                DayOfWeek dayOfWeek = new DayOfWeek(days.get(i), descriptions.get(i));
                daysOfWeek.add(dayOfWeek);
            }

            sharedPreferencesAdapter.updateItem(daysOfWeek);
        }
    }
}
