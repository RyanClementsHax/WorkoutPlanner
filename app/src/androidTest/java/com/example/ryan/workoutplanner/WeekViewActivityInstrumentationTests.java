package com.example.ryan.workoutplanner;

import android.app.Activity;
import android.app.Instrumentation.ActivityResult;
import android.content.Intent;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.ryan.workoutplanner.activities.DayViewActivity;
import com.example.ryan.workoutplanner.activities.WeekViewActivity;
import com.example.ryan.workoutplanner.config.StringConstants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.swipeLeft;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.ryan.workoutplanner.Utils.atPosition;
import static com.example.ryan.workoutplanner.Utils.clickChildViewWithId;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.AllOf.allOf;

/**
 * Created by Ryan on 9/1/2017.
 */

@RunWith(AndroidJUnit4.class)
public class WeekViewActivityInstrumentationTests {
    @Rule
    public IntentsTestRule<WeekViewActivity> mActivityIntentsRule = new IntentsTestRule<>(
            WeekViewActivity.class);

    private WeekViewActivity weekViewActivity;
    private String[] daysOfWeek;
    private String[] dayDescriptions;
    private int testPosition;

    @Before
    public void setUp() {
        //TODO: Learn how to mock SharedPreferences to keep the tests from modifying the state
        weekViewActivity = mActivityIntentsRule.getActivity();
        Intent intent = new Intent();
        intending(not(isInternal())).respondWith(new ActivityResult(Activity.RESULT_OK, intent));

        daysOfWeek = weekViewActivity.getResources().getStringArray(R.array.days_of_week);
        dayDescriptions = weekViewActivity.getResources().getStringArray(R.array.day_descriptions);
        testPosition = 1;
        assertTrue(daysOfWeek.length == dayDescriptions.length);
    }

    @Test
    public void recyclerViewItems_HaveInitializedValues() {
        for(int i = 0; i < daysOfWeek.length; i++) {
            String day = daysOfWeek[i];
            String dayDescription = daysOfWeek[i];

            onView(withId(R.id.week_days_recycler_view))
                    .check(matches(atPosition(i, hasDescendant(withText(day)))));
            onView(withId(R.id.week_days_recycler_view))
                    .check(matches(atPosition(i, hasDescendant(withText(dayDescription)))));
        }
    }

    @Test
    public void clickItem_DayViewActivityStarted() {
        String day = daysOfWeek[testPosition];

        onView(withId(R.id.week_days_recycler_view))
                .perform(actionOnItemAtPosition(1, click()));

        intended(allOf(
                hasComponent(DayViewActivity.class.getName()),
                hasExtra(StringConstants.DAY, day)
        ));
    }

    @Test
    public void clickEdit_CanEditDescription() {
        final String TEST_DESCRIPTION = "TEST";

        onView(withId(R.id.week_days_recycler_view))
                .perform(actionOnItemAtPosition(testPosition, swipeLeft()))
                .check(matches(atPosition(testPosition, hasDescendant(allOf(withId(R.id.edit_layout),
                        isDisplayed())))));
        onView(withId(R.id.week_days_recycler_view))
                .perform(actionOnItemAtPosition(testPosition, clickChildViewWithId(R.id.edit_layout)));
        onView(withId(R.id.input_layout_day_description))
                .check(matches(isDisplayed()));

        onView(withId(R.id.input_day_description))
                .perform(replaceText(TEST_DESCRIPTION));
        onView(withText(android.R.string.ok))
                .perform(click());
        onView(withId(R.id.week_days_recycler_view))
                .check(matches(atPosition(
                        testPosition,
                        hasDescendant(allOf(
                                withId(R.id.day_description),
                                withText(TEST_DESCRIPTION)
                        ))
                )));

        onView(withId(R.id.week_days_recycler_view))
                .perform(actionOnItemAtPosition(testPosition, swipeLeft()))
                .check(matches(atPosition(testPosition, hasDescendant(allOf(withId(R.id.edit_layout),
                        isDisplayed())))));
        onView(withId(R.id.week_days_recycler_view))
                .perform(actionOnItemAtPosition(testPosition, clickChildViewWithId(R.id.edit_layout)));
        onView(withId(R.id.input_layout_day_description))
                .check(matches(isDisplayed()));

        onView(withId(R.id.input_day_description))
                .perform(replaceText(dayDescriptions[testPosition]));
        onView(withText(android.R.string.ok))
                .perform(click());
        onView(withId(R.id.week_days_recycler_view))
                .check(matches(atPosition(
                        testPosition,
                        hasDescendant(allOf(
                                withId(R.id.day_description),
                                withText(dayDescriptions[testPosition])
                        ))
                )));
    }
}
