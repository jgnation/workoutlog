package com.jgnation.workoutLog.serializable;

import java.util.ArrayList;

public class WorkoutItemListSerializable 
{
	private ArrayList<WorkoutItemSerializable> workoutItemList;
	
	public ArrayList<WorkoutItemSerializable> getWorkoutItemList() {
		return workoutItemList;
	}
	
	public void setWorkoutItemList(ArrayList<WorkoutItemSerializable> workoutItemList) {
		this.workoutItemList = workoutItemList;
	}
}
