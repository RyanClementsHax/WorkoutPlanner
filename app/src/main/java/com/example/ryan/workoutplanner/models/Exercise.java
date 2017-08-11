package com.example.ryan.workoutplanner.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryan on 8/8/2017.
 */
public class Exercise implements Serializable {
    public String name;
    public int numSets;
    public int numReps;
    public int weight;
    public WeightUnit weightUnit;
    public List<String> notes;

    public Exercise(String name, int numSets, int numReps, int weight, WeightUnit weightUnit, List<String> notes) {
        this.name = name;
        this.numSets = numSets;
        this.numReps = numReps;
        this.notes = notes;
        this.weight = weight;
        this.weightUnit = weightUnit;
    }

    public enum WeightUnit {
        NONE("None"), LBS("lbs"), KG("kg");
        private String weightUnitString;

        WeightUnit(String weightUnitString) {
            this.weightUnitString = weightUnitString;
        }

        public String getWeightUnitString() {
            return this.weightUnitString;
        }

        public static List<String> getSpinnerList() {
            ArrayList<String> list = new ArrayList<>();
            list.add(NONE.getWeightUnitString());
            list.add(LBS.getWeightUnitString());
            list.add(KG.getWeightUnitString());
            return list;

        }
    }
}
