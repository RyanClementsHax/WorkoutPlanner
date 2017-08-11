package com.example.ryan.workoutplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.ryan.workoutplanner.models.Exercise;
import com.example.ryan.workoutplanner.validators.InputValidator;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

public class AddExerciseActivity extends AppCompatActivity {
    public static final int ADD_EXERCISE_RESULT_CODE = 1;
    private InputValidator validator;
    private EditText exerciseName;
    private EditText weight;
    private EditText numSets;
    private EditText numReps;
    private MaterialSpinner weightUnit;
    private FloatingActionButton saveBtn;
    private List<String> spinnerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exerciseName = (EditText)findViewById(R.id.input_exercise_name);
        weight = (EditText)findViewById(R.id.input_weight);
        numSets = (EditText)findViewById(R.id.input_num_sets);
        numReps = (EditText)findViewById(R.id.input_num_reps);
        weightUnit = (MaterialSpinner) findViewById(R.id.input_weight_unit);;

        setUpInputValidation(exerciseName);
        setUpInputValidation(weight);
        setUpInputValidation(numSets);
        setUpInputValidation(numReps);

        spinnerList = Exercise.WeightUnit.getSpinnerList();
        weightUnit.setItems(spinnerList);

        saveBtn = (FloatingActionButton)findViewById(R.id.save_exercise);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExercise();
            }
        });

        validator = new InputValidator();
    }

    private void setUpInputValidation(final EditText editText) {
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && !isValidEditText(editText)) {
                    setErrorMessage(editText);
                }
            }
        });
    }

    private void saveExercise() {
        validateEditTexts();
        if(allFieldsValid()) {
            Exercise resultExercise = new Exercise(
                    exerciseName.getText().toString(),
                    Integer.parseInt(numSets.getText().toString()),
                    Integer.parseInt(numReps.getText().toString()),
                    Integer.parseInt(weight.getText().toString()),
                    Exercise.WeightUnit.valueOf(spinnerList.get(weightUnit.getSelectedIndex()).toUpperCase()),
                    null
            );
            Intent resultIntent = new Intent(this, AddExerciseActivity.class);
            resultIntent.putExtra(getResources().getString(R.string.add_exercise_result), resultExercise);
            setResult(ADD_EXERCISE_RESULT_CODE, resultIntent);
            finish();
        }
    }

    private boolean allFieldsValid() {
        return isValidEditText(exerciseName)
                && isValidEditText(weight)
                && isValidEditText(numSets)
                && isValidEditText(numReps);
    }

    private void validateEditTexts() {
        if(!isValidEditText(exerciseName)) {
            setErrorMessage(exerciseName);
        }
        if(!isValidEditText(weight)) {
            setErrorMessage(weight);
        }
        if(!isValidEditText(numSets)) {
            setErrorMessage(numSets);
        }
        if(!isValidEditText(numReps)) {
            setErrorMessage(numReps);
        }
    }

    private void setErrorMessage(EditText editText) {
        editText.setError(getResources().getString(R.string.empty_input_error_message));
    }

    private boolean isValidEditText(EditText editText) {
        return !validator.isNullOrEmpty(editText.getText().toString());
    }
}
