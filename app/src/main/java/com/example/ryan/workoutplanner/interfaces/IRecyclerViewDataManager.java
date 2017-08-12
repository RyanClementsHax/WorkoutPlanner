package com.example.ryan.workoutplanner.interfaces;

import android.view.View;

import java.util.List;

/**
 * Created by Ryan on 8/11/2017.
 */

public interface IRecyclerViewDataManager<T> {
    View.OnClickListener getItemClickListener();
    void removeItem(int position);
    void editItem(int position);
    void updateItems(List<T> data);
    List<T> getItems();
}
