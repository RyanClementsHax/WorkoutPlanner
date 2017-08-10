package com.example.ryan.workoutplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.example.ryan.workoutplanner.adapters.WeekViewAdapter;

import java.util.Arrays;

public class WeekViewActivity extends AppCompatActivity implements View.OnClickListener{
    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_week_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView)findViewById(R.id.week_days_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerViewAdapter = new WeekViewAdapter(
                Arrays.asList(getResources().getStringArray(R.array.days_of_week)),
                Arrays.asList(getResources().getStringArray(R.array.day_descriptions)),
                this);
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
    public void onClick(View v) {
        String dayOfWeek = ((TextView)v.findViewById(R.id.day_of_week)).getText().toString();
        String dayDescription = ((TextView)v.findViewById(R.id.day_description)).getText().toString();
        Intent intent = new Intent(this, DayViewActivity.class);
        intent.putExtra(StringConstants.DAY, dayOfWeek);
        intent.putExtra(StringConstants.DAY_DESCRIPTION, dayDescription);
        startActivity(intent);
    }
}
