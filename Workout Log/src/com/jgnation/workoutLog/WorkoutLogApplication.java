package com.jgnation.workoutLog;

import com.jgnation.workoutLog.database.DatabaseManager;
import com.jgnation.workoutLog.entities.Exercise;
import com.jgnation.workoutLog.entities.Profile;
import com.jgnation.workoutLog.entities.Routine;
import com.jgnation.workoutLog.entities.Section;

import android.app.Application;

public class WorkoutLogApplication extends Application
{
	private GlobalPreferences globalPreferences;
	
	public int currentProfileId;
	public Profile currentProfile;
	public int currentRoutineId;
	public Routine currentRoutine;
	public int currentSectionId;
	public Section currentSection;
	public int currentExerciseId;
	public Exercise currentExercise;
	
	@Override
	public void onCreate()
	{
		globalPreferences = new GlobalPreferences(getApplicationContext());
		DatabaseManager.init(getApplicationContext());
		
		currentProfileId = globalPreferences.getProfileId();
		if (currentProfileId != 0)
			currentProfile = DatabaseManager.getInstance().getProfileById(currentProfileId);
		else
			currentProfile = new Profile();
		
		currentRoutineId = globalPreferences.getRoutineId();
		if (currentRoutineId != 0)
			currentRoutine = DatabaseManager.getInstance().getRoutineById(currentRoutineId);
		else
			currentRoutine = new Routine();
		
		currentSectionId = globalPreferences.getSectionId();
		if (currentSectionId != 0)
			currentSection = DatabaseManager.getInstance().getSectionById(currentSectionId);
		else
			currentSection = new Section();
		
		currentExerciseId = globalPreferences.getExerciseId();
		if (currentExerciseId != 0)
			currentExercise = DatabaseManager.getInstance().getExerciseById(currentExerciseId);		
		else
			currentExercise = new Exercise();
	}
	
	public int getCurrentProfileId() {
		return currentProfileId;
	}

	public void setCurrentProfileId(int currentProfileId) {
		this.currentProfileId = currentProfileId;
		globalPreferences.setProfileId(currentProfileId);
	}

	public Profile getCurrentProfile() {
		return currentProfile;
	}

	public void setCurrentProfile(Profile currentProfile) {
		this.currentProfile = currentProfile;
	}

	public int getCurrentRoutineId() {
		return currentRoutineId;
	}

	public void setCurrentRoutineId(int currentRoutineId) {
		this.currentRoutineId = currentRoutineId;
		globalPreferences.setRoutineId(currentRoutineId);
	}

	public Routine getCurrentRoutine() {
		return currentRoutine;
	}

	public void setCurrentRoutine(Routine currentRoutine) {
		this.currentRoutine = currentRoutine;
	}

	public int getCurrentSectionId() {
		return currentSectionId;
	}

	public void setCurrentSectionId(int currentSectionId) {
		this.currentSectionId = currentSectionId;
		globalPreferences.setSectionId(currentSectionId);
	}

	public Section getCurrentSection() {
		return currentSection;
	}

	public void setCurrentSection(Section currentSection) {
		this.currentSection = currentSection;
	}

	public int getCurrentExerciseId() {
		return currentExerciseId;
	}

	public void setCurrentExerciseId(int currentExerciseId) {
		this.currentExerciseId = currentExerciseId;
		globalPreferences.setExerciseId(currentExerciseId);
	}

	public Exercise getCurrentExercise() {
		return currentExercise;
	}

	public void setCurrentExercise(Exercise currentExercise) {
		this.currentExercise = currentExercise;
	}
}
