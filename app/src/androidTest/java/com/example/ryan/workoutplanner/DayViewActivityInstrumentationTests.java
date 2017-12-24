package com.example.ryan.workoutplanner;

import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.example.ryan.workoutplanner.activities.AddAndEditExerciseActivity;
import com.example.ryan.workoutplanner.activities.DayViewActivity;
import com.example.ryan.workoutplanner.config.StringConstants;
import com.example.ryan.workoutplanner.models.Exercise;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.deps.guava.base.Predicates.equalTo;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.BundleMatchers.hasEntry;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.ryan.workoutplanner.Matchers.withToolbarSubtitle;
import static com.example.ryan.workoutplanner.Matchers.withToolbarTitle;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

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
    private String testDay = "Monday";
    private String testDayDescription = "Test";

    @Before
    public void setUp() {
        dayViewActivity = mActivityIntentsRule.getActivity();
    }

    @Test
    public void toolbar_HasDayAndDayDescription() {
        onView(isAssignableFrom(Toolbar.class))
            .check(matches(withToolbarTitle(is(testDay))));
        onView(isAssignableFrom(Toolbar.class))
                .check(matches(withToolbarSubtitle(is(testDayDescription))));
    }
}
