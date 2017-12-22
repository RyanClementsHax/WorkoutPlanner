package com.example.ryan.workoutplanner;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.example.ryan.workoutplanner.activities.DayViewActivity;
import com.example.ryan.workoutplanner.config.StringConstants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.ryan.workoutplanner.Matchers.withToolbarSubtitle;
import static com.example.ryan.workoutplanner.Matchers.withToolbarTitle;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.is;

/**
 * Created by Ryan on 9/11/2017.
 */

@RunWith(AndroidJUnit4.class)
public class DayViewActivityInstrumentationTests {
    @Rule
    public IntentsTestRule<DayViewActivity> mActivityIntentsRule = new IntentsTestRule<DayViewActivity>(
            DayViewActivity.class) {
        @Override
        protected Intent getActivityIntent() {
            Context targetContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            Intent intent = new Intent(targetContext, DayViewActivity.class);
            intent.putExtra(StringConstants.DAY, testDay);
            intent.putExtra(StringConstants.DAY_DESCRIPTION, testDayDescription);
            return intent;
        }
    };

    private DayViewActivity dayViewActivity;
    private String testDay = "Saturday";
    private String testDayDescription = "Rest";

    @Before
    public void setUp() {
        dayViewActivity = mActivityIntentsRule.getActivity();
    }

    @Test
    public void menu_HasDayAndDayDescription() {
        onView(isAssignableFrom(Toolbar.class))
            .check(matches(withToolbarTitle(is(testDay))));
        onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarSubtitle(is(testDayDescription))));
    }
}
