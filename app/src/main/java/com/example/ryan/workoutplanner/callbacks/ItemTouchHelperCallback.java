package com.example.ryan.workoutplanner.callbacks;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.ryan.workoutplanner.interfaces.IItemTouchHelperAdapter;

/**
 * Created by Ryan on 8/12/2017.
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private final IItemTouchHelperAdapter itemTouchHelperAdapter;

    public ItemTouchHelperCallback(IItemTouchHelperAdapter adapter) {
        itemTouchHelperAdapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        final int swipeFlags = 0;
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        itemTouchHelperAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int position) {
        //don't care about this event
    }
}
