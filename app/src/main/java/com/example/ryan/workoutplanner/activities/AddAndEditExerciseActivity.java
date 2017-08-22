package com.example.ryan.workoutplanner.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.example.ryan.workoutplanner.R;
import com.example.ryan.workoutplanner.WorkoutPlannerApplication;
import com.example.ryan.workoutplanner.models.Exercise;
import com.example.ryan.workoutplanner.validators.InputValidator;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.List;

import javax.inject.Inject;

public class AddAndEditExerciseActivity extends AppCompatActivity {
    @Inject
    InputValidator validator;
    public static final int ADD_EXERCISE_RESULT_CODE = 1;
    public static final int EDIT_EXERCISE_RESULT_CODE = 2;
    private boolean isEditing = false;
    private EditText exerciseName;
    private EditText weight;
    private EditText numSets;
    private EditText numReps;
    private MaterialSpinner weightUnit;
    private FloatingActionButton saveBtn;
    private List<String> spinnerList;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((WorkoutPlannerApplication) getApplication()).getAppComponent().inject(this);

        setContentView(R.layout.activity_add_and_edit_exercise);
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

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null && extras.getInt(getResources().getString(R.string.request_code), 0) == EDIT_EXERCISE_RESULT_CODE) {
            isEditing = true;
            getSupportActionBar().setTitle(getResources().getString(R.string.title_activity_edit_exercise));
            exercise = (Exercise)extras.getSerializable(getResources().getString(R.string.edit_exercise));
            exerciseName.setText(exercise.name);
            weight.setText(String.valueOf(exercise.weight));
            numSets.setText(String.valueOf(exercise.numSets));
            numReps.setText(String.valueOf(exercise.numReps));
            weightUnit.setSelectedIndex(spinnerList.indexOf(exercise.weightUnit.getWeightUnitString()));
        }

        saveBtn = (FloatingActionButton)findViewById(R.id.save_exercise);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveExercise();
            }
        });
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
            Intent resultIntent = new Intent(this, AddAndEditExerciseActivity.class);
            if(isEditing) {
                resultExercise.uuid = exercise.uuid;
                resultIntent.putExtra(getResources().getString(R.string.edit_exercise_result), resultExercise);
                setResult(EDIT_EXERCISE_RESULT_CODE, resultIntent);
            } else {
                resultIntent.putExtra(getResources().getString(R.string.add_exercise_result), resultExercise);
                setResult(ADD_EXERCISE_RESULT_CODE, resultIntent);
            }
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
