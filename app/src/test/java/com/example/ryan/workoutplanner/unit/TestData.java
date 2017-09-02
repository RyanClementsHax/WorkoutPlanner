package com.example.ryan.workoutplanner.unit;

import com.example.ryan.workoutplanner.models.DayOfWeek;
import com.example.ryan.workoutplanner.models.Exercise;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 8/23/2017.
 */

public class TestData {
    public static List<Exercise> createExerciseList() {
        List<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise("1", 1, 1, 1, Exercise.WeightUnit.LBS, new ArrayList<String>()));
        exercises.add(new Exercise("2", 2, 2, 2, Exercise.WeightUnit.KG, new ArrayList<String>()));
        exercises.add(new Exercise("3", 3, 3, 3, Exercise.WeightUnit.NONE, new ArrayList<String>()));

        return exercises;
    }

    public static List<DayOfWeek> createDayOfWeekList() {
        List<DayOfWeek> days = new ArrayList<>();

        days.add(new DayOfWeek("Test1", "TestDescription1"));
        days.add(new DayOfWeek("Test2", "TestDescription2"));
        days.add(new DayOfWeek("Test3", "TestDescription3"));

        return days;
    }
}
