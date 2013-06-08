package com.jgnation.workoutLog.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class WorkoutItemSet 
{
	@DatabaseField(generatedId=true)
    private int id;
	
	@DatabaseField
	private float weight;
	
	@DatabaseField
	private int reps;
	
	public final static String WORKOUT_ITEM_FIELD_NAME = "workoutItem";
	
    @DatabaseField(foreign=true, foreignAutoRefresh=true, columnName = WORKOUT_ITEM_FIELD_NAME)
    private WorkoutItem workoutItem;
	
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
	public float getWeight() {
		return weight;
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public int getReps() {
		return reps;
	}
	
	public void setReps(int reps) {
		this.reps = reps;
	}
	
	public WorkoutItem getWorkoutItem() {
		return workoutItem;
	}
	
	public void setWorkoutItem(WorkoutItem workoutItem) {
		this.workoutItem = workoutItem;
	}
}
