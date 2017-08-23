package com.example.ryan.workoutplanner.unit.validators;

import android.text.TextUtils;

import com.example.ryan.workoutplanner.interfaces.IInputValidator;
import com.example.ryan.workoutplanner.validators.InputValidator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.rule.PowerMockRule;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by Ryan on 8/23/2017.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(TextUtils.class)
public class InputValidatorUnitTests {
    IInputValidator validator;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(anyString())).thenCallRealMethod();

        validator = new InputValidator();
    }

    @Test
    public void isNullOrEmpty_Null_True() {
        boolean response = validator.isNullOrEmpty(null);
        assert(response);
    }

    @Test
    public void isNullOrEmpty_Empty_True() {
        boolean response = validator.isNullOrEmpty("");
        assert(response);
    }

    @Test
    public void isNullOrEmpty_NotNullOrEmpty_False() {
        boolean response = validator.isNullOrEmpty("Test");
        assertFalse(response);
    }
}
