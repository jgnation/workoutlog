package com.jgnation.workoutLog;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GlobalPreferences 
{
    private static final String GLOBAL_SHARED_PREFS = GlobalPreferences.class.getSimpleName();
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;
    
    public static final String KEY_PREFS_PROFILE_ID = "profile_id";
    public static final String KEY_PREFS_ROUTINE_ID = "routine_id";
    public static final String KEY_PREFS_SECTION_ID = "section_id";
    public static final String KEY_PREFS_EXERCISE_ID = "exercise_id";
    
    public GlobalPreferences(Context context) 
    {
        this._sharedPrefs = context.getSharedPreferences(GLOBAL_SHARED_PREFS, Activity.MODE_PRIVATE);
        this._prefsEditor = _sharedPrefs.edit();
    }
    
    public int getProfileId() {
    	return _sharedPrefs.getInt(KEY_PREFS_PROFILE_ID, 0);
    }
    
    public void setProfileId(int id) {
    	_prefsEditor.putInt(KEY_PREFS_PROFILE_ID, id);
    	_prefsEditor.commit();
    }
    
    public int getRoutineId() {
    	return _sharedPrefs.getInt(KEY_PREFS_ROUTINE_ID, 0);
    }
    
    public void setRoutineId(int id) {
    	_prefsEditor.putInt(KEY_PREFS_ROUTINE_ID, id);
    	_prefsEditor.commit();
    }
    
    public int getSectionId() {
    	return _sharedPrefs.getInt(KEY_PREFS_SECTION_ID, 0);
    }
    
    public void setSectionId(int id) {
    	_prefsEditor.putInt(KEY_PREFS_SECTION_ID, id);
    	_prefsEditor.commit();
    }
    
    public int getExerciseId() {
    	return _sharedPrefs.getInt(KEY_PREFS_EXERCISE_ID, 0);
    }
    
    public void setExerciseId(int id) {
    	_prefsEditor.putInt(KEY_PREFS_EXERCISE_ID, id);
    	_prefsEditor.commit();
    }
}
