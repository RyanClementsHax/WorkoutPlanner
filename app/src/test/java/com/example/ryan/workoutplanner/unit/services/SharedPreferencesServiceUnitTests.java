package com.example.ryan.workoutplanner.unit.services;

import android.content.SharedPreferences;

import com.example.ryan.workoutplanner.interfaces.ISharedPreferencesService;
import com.example.ryan.workoutplanner.services.SharedPreferencesService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ryan on 8/22/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesServiceUnitTests {
    ISharedPreferencesService sharedPreferencesService;
    @Mock
    SharedPreferences sharedPreferencesMock;
    @Mock
    SharedPreferences.Editor sharedPreferencesEditorMock;

    private Gson gson;
    private List<Object> objectList;
    private String serializedObjectList;
    private Type objectListType;

    private final String TEST_PREFERENCE_KEY = "TEST_PREFERENCE_KEY";

    @Before
    public void setUp() {
        this.gson = new Gson();
        this.objectList = new ArrayList<>();
        this.objectList.add(new Object());
        this.objectListType = new TypeToken<List<Object>>(){}.getType();
        this.serializedObjectList = gson.toJson(objectList, objectListType);

        when(sharedPreferencesMock.getString(TEST_PREFERENCE_KEY, "")).thenReturn(serializedObjectList);
        when(sharedPreferencesMock.edit()).thenReturn(sharedPreferencesEditorMock);

        sharedPreferencesService = new SharedPreferencesService(sharedPreferencesMock, gson);
    }

    @Test
    public void getObject_ValidParams_ObjectList() {
        List<Object> result = (List<Object>)sharedPreferencesService.getObject(TEST_PREFERENCE_KEY, objectListType);
        assertFalse(result.isEmpty());
        verify(sharedPreferencesMock).getString(TEST_PREFERENCE_KEY, "");
    }

    @Test
    public void updateObject_ValidParams_SavedToSharedPreferences() {
        sharedPreferencesService.updateObject(TEST_PREFERENCE_KEY, objectListType, objectList);
        verify(sharedPreferencesMock).edit();
        verify(sharedPreferencesEditorMock).putString(TEST_PREFERENCE_KEY, serializedObjectList);
        verify(sharedPreferencesEditorMock).commit();
    }
}
