package com.example.ryan.workoutplanner.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ryan.workoutplanner.R;

import java.util.List;

/**
 * Created by Ryan on 8/8/2017.
 */
public class WeekViewAdapter extends RecyclerView.Adapter<WeekViewAdapter.ViewHolder> {
    private List<String> daysOfWeek;
    private List<String> dayDescriptions;
    private View.OnClickListener listener;

    public WeekViewAdapter(List<String> daysOfWeek, List<String> dayDescriptions, View.OnClickListener listener) {
        this.daysOfWeek = daysOfWeek;
        this.dayDescriptions = dayDescriptions;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_of_week, parent, false);
        v.setOnClickListener(listener);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.dayOfWeek.setText(daysOfWeek.get(position));
        holder.dayDescription.setText(dayDescriptions.get(position));
    }

    @Override
    public int getItemCount() {
        return daysOfWeek.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfWeek;
        TextView dayDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            this.dayOfWeek = (TextView)itemView.findViewById(R.id.day_of_week);
            this.dayDescription = (TextView)itemView.findViewById(R.id.day_description);
        }
    }
}
