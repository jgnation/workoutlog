package com.jgnation.workoutLog.serializable;

import com.jgnation.workoutLog.entities.WorkoutItem;

public class WorkoutItemSetSerializable 
{
    private int id;
	
	private float weight;
	
	private int reps;
	
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
}
