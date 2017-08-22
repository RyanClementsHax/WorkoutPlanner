package com.example.ryan.workoutplanner.models;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Ryan on 8/12/2017.
 */

public class DayOfWeek implements Serializable {
    public String day;
    public String description;
    public UUID uuid;

    public DayOfWeek(String day, String description) {
        this.day = day;
        this.description = description;
        uuid = UUID.randomUUID();
    }
}
