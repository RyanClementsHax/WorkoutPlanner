package com.example.ryan.workoutplanner.models;

import java.util.List;

/**
 * Created by Ryan on 8/8/2017.
 */
public class Exercise {
    public String name;
    public int numSets;
    public int numReps;
    public List<String> notes;

    public Exercise(String name, int numSets, int numReps, List<String> notes) {
        this.name = name;
        this.numSets = numSets;
        this.numReps = numReps;
        this.notes = notes;
    }
}
