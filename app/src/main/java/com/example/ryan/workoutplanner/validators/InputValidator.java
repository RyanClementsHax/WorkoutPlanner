package com.example.ryan.workoutplanner.validators;

import android.text.TextUtils;

import com.example.ryan.workoutplanner.interfaces.IInputValidator;

/**
 * Created by Ryan on 8/10/2017.
 */

public class InputValidator implements IInputValidator {
    public boolean isNullOrEmpty(String string) {
        return TextUtils.isEmpty(string);
    }
}
