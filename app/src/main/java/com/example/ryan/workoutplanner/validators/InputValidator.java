package com.example.ryan.workoutplanner.validators;

import android.text.TextUtils;

/**
 * Created by Ryan on 8/10/2017.
 */

public class InputValidator {
    public boolean isNullOrEmpty(String string) {
        return TextUtils.isEmpty(string);
    }
}
