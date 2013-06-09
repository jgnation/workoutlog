package com.jgnation.workoutLog.application;

import com.jgnation.workoutLog.entities.Exercise;
import com.jgnation.workoutLog.entities.Profile;
import com.jgnation.workoutLog.entities.Routine;
import com.jgnation.workoutLog.entities.Section;

import android.app.Application;

public class WorkoutLogApplication extends Application
{
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
		currentProfileId = 0;
		currentProfile = new Profile();
		currentRoutineId = 0;
		currentRoutine = new Routine();
		currentSectionId = 0;
		currentSection = new Section();
		currentExerciseId = 0;
		currentExercise = new Exercise();		
	}
	
	public int getCurrentProfileId() {
		return currentProfileId;
	}

	public void setCurrentProfileId(int currentProfileId) {
		this.currentProfileId = currentProfileId;
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
	}

	public Exercise getCurrentExercise() {
		return currentExercise;
	}

	public void setCurrentExercise(Exercise currentExercise) {
		this.currentExercise = currentExercise;
	}
}
