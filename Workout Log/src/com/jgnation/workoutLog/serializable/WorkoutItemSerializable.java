package com.jgnation.workoutLog.serializable;

import java.util.ArrayList;

import com.j256.ormlite.dao.ForeignCollection;
import com.jgnation.workoutLog.converters.Converter;
import com.jgnation.workoutLog.entities.Exercise;
import com.jgnation.workoutLog.entities.WorkoutItemSet;

public class WorkoutItemSerializable 
{
    private int id;
	
	private String date;
	
    private ArrayList<WorkoutItemSetSerializable> workoutItemSets;
	
	private String notes;
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }
    
    public void setWorkoutItemSets(ArrayList<WorkoutItemSetSerializable> workoutItemSets) {
        this.workoutItemSets = workoutItemSets;
    }

    /*public ArrayList<WorkoutItemSetSerializable> getWorkoutItemSets() {
        ArrayList<WorkoutItemSet> workoutItemSetList = new ArrayList<WorkoutItemSet>();
        for (WorkoutItemSetSerializable workoutItemSet : workoutItemSets) {
        	workoutItemSetList.add(Converter.serializableToEntity(workoutItemSet));
        }
        return workoutItemSetList;
    }*/
    
    public ArrayList<WorkoutItemSetSerializable> getWorkoutItemSets() {
        return workoutItemSets;
    }
}
