package com.example.ryan.workoutplanner.unit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.example.ryan.workoutplanner.adapters.DayViewAdapter;
import com.example.ryan.workoutplanner.interfaces.IRecyclerViewDataManager;
import com.example.ryan.workoutplanner.models.Exercise;
import com.example.ryan.workoutplanner.unit.TestData;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.List;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ryan on 8/23/2017.
 */

@RunWith(PowerMockRunner.class)
@PrepareForTest(LayoutInflater.class)
public class DayViewAdapterUnitTests {
    @Mock
    IRecyclerViewDataManager<Exercise> dataManagerMock;
    @Mock
    ViewBinderHelper viewBinderHelperMock;
    @Mock
    ViewGroup viewGroupMock;
    @Mock
    Context contextMock;
    @Mock
    LayoutInflater layoutInflaterMock;
    @Mock
    View viewMock;
    @Mock
    SwipeRevealLayout swipeRevealLayoutMock;
    @Mock
    DayViewAdapter.ViewHolder viewHolderMock;

    @InjectMocks
    DayViewAdapter dayViewAdapter;

    private List<Exercise> exerciseList;
    private int viewType = 0;
    private int position = 0;

    @Before
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        PowerMockito.mockStatic(LayoutInflater.class);
        exerciseList = TestData.createExerciseList();
        dayViewAdapter = spy(dayViewAdapter);

        when(dataManagerMock.getItems()).thenReturn(exerciseList);
        when(viewGroupMock.getContext()).thenReturn(contextMock);
        when(LayoutInflater.from(contextMock)).thenReturn(layoutInflaterMock);
        when(layoutInflaterMock.inflate(anyInt(), eq(viewGroupMock), eq(false))).thenReturn(viewMock);

        dayViewAdapter.setDataManager(dataManagerMock);
        viewHolderMock.swipeRevealLayout = swipeRevealLayoutMock;
    }

    @Test
    public void onCreateViewHolder_ValidParams_InflatesView() {
        DayViewAdapter.ViewHolder response = dayViewAdapter.onCreateViewHolder(viewGroupMock, viewType);

        assertNotNull(response);
        verify(viewGroupMock).getContext();
        PowerMockito.verifyStatic(Mockito.times(1));
        LayoutInflater.from(contextMock);
        verify(layoutInflaterMock).inflate(anyInt(), eq(viewGroupMock), eq(false));
    }

    @Test
    public void onBindViewHolder_ValidParams_BindsView() {
        Exercise exercise = exerciseList.get(position);

        dayViewAdapter.onBindViewHolder(viewHolderMock, position);

        verify(viewBinderHelperMock).bind(swipeRevealLayoutMock, exercise.uuid.toString());
        verify(viewHolderMock).bind(exercise);
    }
}
