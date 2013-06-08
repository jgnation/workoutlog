package com.jgnation.workoutLog.converters;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.jgnation.workoutLog.entities.Exercise;
import com.jgnation.workoutLog.entities.Profile;
import com.jgnation.workoutLog.entities.Routine;
import com.jgnation.workoutLog.entities.Section;
import com.jgnation.workoutLog.entities.WorkoutItem;
import com.jgnation.workoutLog.entities.WorkoutItemSet;
import com.jgnation.workoutLog.serializable.ExerciseSerializable;
import com.jgnation.workoutLog.serializable.ProfileSerializable;
import com.jgnation.workoutLog.serializable.RoutineSerializable;
import com.jgnation.workoutLog.serializable.SectionSerializable;
import com.jgnation.workoutLog.serializable.WorkoutItemSerializable;
import com.jgnation.workoutLog.serializable.WorkoutItemSetSerializable;

public class Converter 
{	
	public static ProfileSerializable entityToSerializable(Profile p)
	{
		ProfileSerializable profileSerializable = new ProfileSerializable();
		
		profileSerializable.setId(p.getId());
		profileSerializable.setDescription(p.getDescription());
		profileSerializable.setName(p.getName());
		
		return profileSerializable;
	}
	
	public static RoutineSerializable entityToSerializable(Routine r)
	{
		RoutineSerializable routineSerializable = new RoutineSerializable();
		
		routineSerializable.setId(r.getId());
		routineSerializable.setDescription(r.getDescription());
		routineSerializable.setName(r.getName());
		
		return routineSerializable;
	}
	
	public static SectionSerializable entityToSerializable(Section s)
	{
		SectionSerializable sectionSerializable = new SectionSerializable();
		
		sectionSerializable.setId(s.getId());
		sectionSerializable.setDescription(s.getDescription());
		sectionSerializable.setName(s.getName());
		
		return sectionSerializable;
	}
	
	public static ExerciseSerializable entityToSerializable(Exercise ex)
	{
		ExerciseSerializable exerciseSerializable = new ExerciseSerializable();
		
		exerciseSerializable.setId(ex.getId());
		exerciseSerializable.setDescription(ex.getDescription());
		exerciseSerializable.setName(ex.getName());
		
		return exerciseSerializable;
	}
	
	public static WorkoutItem serializableToEntity(WorkoutItemSerializable s, Exercise e)
	{
		WorkoutItem workoutItem = new WorkoutItem();
		
		//workoutItem.setId(id);
		workoutItem.setDate(s.getDate());
		workoutItem.setNotes(s.getNotes());
		workoutItem.setExercise(e);
		//workoutItem.setWorkoutItemSets(s.getWorkoutItemSets());
		//ForeignCollection<WorkoutItemSet> workoutItemSets = s.getWorkoutItemSets();
		//ForeignCollection<WorkoutItemSet> workoutItemSets = new ForeignCollection<WorkoutItemSet>();
		
		return workoutItem;
	}
	
	public static WorkoutItemSet serializableToEntity(WorkoutItemSetSerializable s)
	{
		WorkoutItemSet workoutItemSet = new WorkoutItemSet();
		
		//workoutItemSet.setId(id);
		workoutItemSet.setReps(s.getReps());
		workoutItemSet.setWeight(s.getWeight());		
		
		return workoutItemSet;
	}
	
	public static WorkoutItemSetSerializable entityToSerializable(WorkoutItemSet w)
	{
		WorkoutItemSetSerializable serializable = new WorkoutItemSetSerializable();
		
		serializable.setId(w.getId());
		serializable.setReps(w.getReps());
		serializable.setWeight(w.getWeight());
		
		return serializable;
	}
	
	public static WorkoutItemSerializable entityToSerializable(WorkoutItem w)
	{
		WorkoutItemSerializable serializable = new WorkoutItemSerializable();
		
		serializable.setId(w.getId());
		serializable.setDate(w.getDate());
		serializable.setNotes(w.getNotes());

		ArrayList<WorkoutItemSetSerializable> serializableList = new ArrayList<WorkoutItemSetSerializable>();
		
		List<WorkoutItemSet> setEntities = w.getWorkoutItemSets();
		for (WorkoutItemSet set : setEntities)
		{
			serializableList.add(entityToSerializable(set));
		}
		
		serializable.setWorkoutItemSets(serializableList);
		
		return serializable;
	}
	
	public static List<WorkoutItemSerializable> entityToSerializable(List<WorkoutItem> wList)
	{
		List<WorkoutItemSerializable> serializableList = new ArrayList<WorkoutItemSerializable>();
		
		for (WorkoutItem w : wList)
		{
			serializableList.add(entityToSerializable(w));
		}
		
		return serializableList;
	}

}
