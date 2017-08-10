package com.example.ryan.workoutplanner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ryan.workoutplanner.R;
import com.example.ryan.workoutplanner.models.Exercise;

import java.util.List;

/**
 * Created by Ryan on 8/8/2017.
 */
public class DayViewAdapter extends RecyclerView.Adapter<DayViewAdapter.ViewHolder> {
    private List<Exercise> exercises;

    public DayViewAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @Override
    public DayViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise, parent, false);
        ViewHolder vh = new ViewHolder(v);

        return vh;
    }

    @Override
    public void onBindViewHolder(DayViewAdapter.ViewHolder holder, int position) {
        holder.setExercise(exercises.get(position));
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView weightTextView;
        TextView numSetsTextView;
        TextView numRepsTextView;

        public ViewHolder(View itemView) {
            super(itemView);

            this.nameTextView = (TextView)itemView.findViewById(R.id.exercise_name);
            this.weightTextView = (TextView)itemView.findViewById(R.id.weight);
            this.numSetsTextView = (TextView)itemView.findViewById(R.id.num_sets);
            this.numRepsTextView = (TextView)itemView.findViewById(R.id.num_reps);
        }

        public void setExercise(Exercise exercise) {
            nameTextView.setText(exercise.name);
            weightTextView.setText(exercise.weight + exercise.weightUnit.getWeightUnitString());
            numSetsTextView.setText(String.valueOf(exercise.numSets));
            numRepsTextView.setText(String.valueOf(exercise.numReps));
        }
    }
}
