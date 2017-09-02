package com.example.ryan.workoutplanner;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ryan.workoutplanner.activities.WeekViewActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Ryan on 9/1/2017.
 */

@RunWith(AndroidJUnit4.class)
public class WeekViewActivityInstrumentationTests {
    @Rule
    public ActivityTestRule<WeekViewActivity> mActivityRule =
            new ActivityTestRule<>(WeekViewActivity.class);

    private WeekViewActivity weekViewActivity;

    @Before
    public void setUp() {
        weekViewActivity = mActivityRule.getActivity();
    }

    @Test
    public void itWorks() {
        assert(true);
    }
}
