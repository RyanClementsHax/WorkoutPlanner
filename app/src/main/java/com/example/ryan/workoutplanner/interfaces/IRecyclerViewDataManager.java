package com.example.ryan.workoutplanner.interfaces;

import java.util.List;

/**
 * Created by Ryan on 8/11/2017.
 */

public interface IRecyclerViewDataManager<T> {
    void removeItem(int position);
    void editItem(int position);
    void updateData(List<T> data);
}
