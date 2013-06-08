package com.jgnation.workoutLog;

import com.jgnation.workoutLog.entities.Exercise;
import com.jgnation.workoutLog.entities.Profile;
import com.jgnation.workoutLog.entities.Routine;
import com.jgnation.workoutLog.entities.Section;

public class Global 
{
	public static int currentProfileId = 0;
	public static Profile currentProfile = new Profile();
	public static int currentRoutineId = 0;
	public static Routine currentRoutine = new Routine();
	public static int currentSectionId = 0;
	public static Section currentSection = new Section();
	public static int currentExerciseId = 0;
	public static Exercise currentExercise = new Exercise();
}
