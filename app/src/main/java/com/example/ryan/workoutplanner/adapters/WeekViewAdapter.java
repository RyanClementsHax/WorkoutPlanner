package com.example.ryan.workoutplanner.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.ryan.workoutplanner.R;
import com.example.ryan.workoutplanner.interfaces.IRecyclerViewDataManager;
import com.example.ryan.workoutplanner.models.DayOfWeek;

import java.util.List;

/**
 * Created by Ryan on 8/8/2017.
 */
public class WeekViewAdapter extends RecyclerView.Adapter<WeekViewAdapter.ViewHolder> {
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private IRecyclerViewDataManager<DayOfWeek> dataManager;
    private List<DayOfWeek> daysOfWeek;

    public WeekViewAdapter(IRecyclerViewDataManager<DayOfWeek> dataManager) {
        this.dataManager = dataManager;
        this.daysOfWeek = dataManager.getItems();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.day_of_week, parent, false);
        CardView cardView = (CardView) v.findViewById(R.id.card_view);
        cardView.setOnClickListener(dataManager.getItemClickListener());
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DayOfWeek dayOfWeek = daysOfWeek.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, dayOfWeek.uuid.toString());
        holder.bind(dayOfWeek);
    }

    @Override
    public int getItemCount() {
        return daysOfWeek.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayOfWeek;
        TextView dayDescription;
        SwipeRevealLayout swipeRevealLayout;
        RelativeLayout editLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            this.dayOfWeek = (TextView)itemView.findViewById(R.id.day_of_week);
            this.dayDescription = (TextView)itemView.findViewById(R.id.day_description);
            this.swipeRevealLayout = (SwipeRevealLayout)itemView.findViewById(R.id.week_view_swipe_reveal_layout);
            this.editLayout = (RelativeLayout)itemView.findViewById(R.id.edit_layout);
        }

        public void bind(DayOfWeek dayOfWeek) {
            this.dayOfWeek.setText(dayOfWeek.day);
            this.dayDescription.setText(dayOfWeek.description);
        }
    }
}
