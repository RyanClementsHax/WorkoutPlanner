package com.example.ryan.workoutplanner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.ryan.workoutplanner.R;
import com.example.ryan.workoutplanner.interfaces.IItemTouchHelperAdapter;
import com.example.ryan.workoutplanner.interfaces.IRecyclerViewDataManager;
import com.example.ryan.workoutplanner.models.Exercise;

import java.util.List;

/**
 * Created by Ryan on 8/8/2017.
 */
public class DayViewAdapter extends RecyclerView.Adapter<DayViewAdapter.ViewHolder> implements IItemTouchHelperAdapter {
    private ViewBinderHelper viewBinderHelper;
    private List<Exercise> exercises;
    private IRecyclerViewDataManager<Exercise> dataManager;

    public DayViewAdapter(ViewBinderHelper viewBinderHelper) {
        this.viewBinderHelper = viewBinderHelper;
    }

    @Override
    public DayViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exercise, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DayViewAdapter.ViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, exercise.uuid.toString());
        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Exercise item = exercises.remove(fromPosition);
        exercises.add(toPosition, item);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void setDataManager(IRecyclerViewDataManager<Exercise> dataManager) {
        this.dataManager = dataManager;
        this.exercises = dataManager.getItems();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView nameTextView;
        public TextView weightTextView;
        public TextView numSetsTextView;
        public TextView numRepsTextView;
        public RelativeLayout editLayout;
        public RelativeLayout clearLayout;
        public SwipeRevealLayout swipeRevealLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            this.nameTextView = (TextView)itemView.findViewById(R.id.exercise_name);
            this.weightTextView = (TextView)itemView.findViewById(R.id.weight);
            this.numSetsTextView = (TextView)itemView.findViewById(R.id.num_sets);
            this.numRepsTextView = (TextView)itemView.findViewById(R.id.num_reps);
            this.editLayout = (RelativeLayout)itemView.findViewById(R.id.edit_layout);
            this.clearLayout = (RelativeLayout)itemView.findViewById(R.id.clear_layout);
            this.swipeRevealLayout = (SwipeRevealLayout)itemView.findViewById(R.id.day_view_swipe_reveal_layout);
        }

        public void bind(Exercise exercise) {
            nameTextView.setText(exercise.name);
            if(exercise.weightUnit == Exercise.WeightUnit.NONE) {
                weightTextView.setText(String.valueOf(exercise.weight));
            } else {
                weightTextView.setText(exercise.weight + exercise.weightUnit.getWeightUnitString());
            }
            numSetsTextView.setText(String.valueOf(exercise.numSets));
            numRepsTextView.setText(String.valueOf(exercise.numReps));

            clearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position >= 0) {
                        dataManager.removeItem(position);
                        notifyItemRemoved(position);
                    }
                }
            });

            editLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position >= 0) {
                        swipeRevealLayout.close(true);
                        dataManager.editItem(position);
                        notifyDataSetChanged();
                    }
                }
            });
        }
    }
}
