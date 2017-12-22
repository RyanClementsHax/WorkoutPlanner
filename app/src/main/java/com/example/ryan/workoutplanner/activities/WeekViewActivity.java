package com.example.ryan.workoutplanner.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ryan.workoutplanner.R;
import com.example.ryan.workoutplanner.WorkoutPlannerApplication;
import com.example.ryan.workoutplanner.adapters.WeekViewAdapter;
import com.example.ryan.workoutplanner.config.StringConstants;
import com.example.ryan.workoutplanner.interfaces.IRecyclerViewDataManager;
import com.example.ryan.workoutplanner.interfaces.ISharedPreferencesService;
import com.example.ryan.workoutplanner.models.DayOfWeek;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

public class WeekViewActivity extends AppCompatActivity implements IRecyclerViewDataManager<DayOfWeek> {
    @Inject
    ISharedPreferencesService sharedPreferencesService;
    @Inject
    WeekViewAdapter recyclerViewAdapter;
    @Inject
    LinearLayoutManager recyclerViewLayoutManager;

    private RecyclerView recyclerView;
    private View.OnClickListener itemClickListener;
    private List<DayOfWeek> daysOfWeek;
    private Type dayListType;
    private String preferenceKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((WorkoutPlannerApplication) getApplication()).getAppComponent().inject(this);
        setContentView(R.layout.activity_week_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.preferenceKey = StringConstants.WEEK_VIEW_PREFERENCE_KEY;
        this.dayListType = new TypeToken<List<DayOfWeek>>(){}.getType();
        this.daysOfWeek = (List<DayOfWeek>) sharedPreferencesService.getObject(preferenceKey, dayListType);
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

        recyclerViewAdapter.setDataManager(this);
        recyclerView = (RecyclerView)findViewById(R.id.week_days_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedPreferencesService.updateObject(preferenceKey, dayListType, daysOfWeek);
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
        final DayOfWeek dayOfWeek = daysOfWeek.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getResources().getString(R.string.edit_day_description_alert_dialog_title));
        View inflatedView = LayoutInflater.from(this).inflate(
                R.layout.edit_day_description_alert_dialog,
                (ViewGroup)findViewById(android.R.id.content),
                false
        );

        final EditText dayDescriptionEditText = (EditText)inflatedView.findViewById(R.id.input_day_description);
        dayDescriptionEditText.setText(dayOfWeek.description);

        builder.setView(inflatedView);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dayOfWeek.description = dayDescriptionEditText.getText().toString();
                recyclerViewAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        final AlertDialog editDayDescriptionDiaglog = builder.create();
        editDayDescriptionDiaglog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                int colorAccent = getResources().getColor(R.color.colorAccent);
                Button btnPositive = editDayDescriptionDiaglog.getButton(Dialog.BUTTON_POSITIVE);
                Button btnNegative = editDayDescriptionDiaglog.getButton(DialogInterface.BUTTON_NEGATIVE);
                btnPositive.setTextColor(colorAccent);
                btnNegative.setTextColor(colorAccent);
            }
        });

        editDayDescriptionDiaglog.show();
    }

    @Override
    public List<DayOfWeek> getItems() {
        return daysOfWeek;
    }

    private void setUpIfFirstTimeAppStartUp() {
        if(sharedPreferencesService.getBoolean(StringConstants.IS_FIRST_TIME_APP_STARTUP_KEY, true)) {
            sharedPreferencesService.putBoolean(StringConstants.IS_FIRST_TIME_APP_STARTUP_KEY, false);

            List<String> days = Arrays.asList(getResources().getStringArray(R.array.days_of_week));
            List<String> descriptions = Arrays.asList(getResources().getStringArray(R.array.day_descriptions));

            for(int i = 0; i < days.size(); i++) {
                DayOfWeek dayOfWeek = new DayOfWeek(days.get(i), descriptions.get(i));
                daysOfWeek.add(dayOfWeek);
            }

            sharedPreferencesService.updateObject(preferenceKey, dayListType, daysOfWeek);
        }
    }
}
