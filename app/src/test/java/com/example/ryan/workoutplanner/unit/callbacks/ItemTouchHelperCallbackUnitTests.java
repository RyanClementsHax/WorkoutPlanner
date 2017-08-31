package com.example.ryan.workoutplanner.unit.callbacks;

import android.support.v7.widget.RecyclerView;

import com.example.ryan.workoutplanner.callbacks.ItemTouchHelperCallback;
import com.example.ryan.workoutplanner.interfaces.IItemTouchHelperAdapter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ryan on 8/30/2017.
 */

public class ItemTouchHelperCallbackUnitTests {
    @Mock
    RecyclerView recyclerViewMock;
    @Mock
    RecyclerView.ViewHolder viewHolderMock;
    @Mock
    RecyclerView.ViewHolder viewHolderSourceMock;
    @Mock
    RecyclerView.ViewHolder viewHolderTargetMock;
    @Mock
    IItemTouchHelperAdapter itemTouchHelperAdapterMock;

    ItemTouchHelperCallback itemTouchHelperCallback;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        itemTouchHelperCallback = new ItemTouchHelperCallback();
        itemTouchHelperCallback.setItemTouchHelperAdapter(itemTouchHelperAdapterMock);
    }

    @Test
    public void isLongPressDragEnabled_NoParams_True() {
        boolean response = itemTouchHelperCallback.isLongPressDragEnabled();
        assertTrue(response);
    }

    @Test
    public void getMovementFlags_ValidParams_MovementFlagsCreated() {
        int response = itemTouchHelperCallback.getMovementFlags(recyclerViewMock, viewHolderMock);
        assertEquals(response, 196611);
    }

    @Test
    public void onMove_ValidParams_True() {
        boolean response = itemTouchHelperCallback.onMove(recyclerViewMock, viewHolderSourceMock, viewHolderTargetMock);
        assertTrue(response);
        verify(itemTouchHelperAdapterMock).onItemMove(anyInt(), anyInt());
    }
}
