package com.jgnation.workoutLog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class WorkoutItem 
{	
	@DatabaseField(generatedId=true)
    private int id;
	
	@DatabaseField
	private String date;
	
	@DatabaseField
	private Date internalDate;
	
	@ForeignCollectionField
    private ForeignCollection<WorkoutItemSet> workoutItemSets;
	
	@DatabaseField
	private String notes;
	
	public final static String EXERCISE_FIELD_NAME = "exercise";
	
    @DatabaseField(foreign=true, foreignAutoRefresh=true, columnName = EXERCISE_FIELD_NAME)
    private Exercise exercise;
    
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
    
    public void setInternalDate(Date internalDate) {
        this.internalDate = internalDate;
    }

    public Date getInternalDate() {
        return internalDate;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getNotes() {
        return notes;
    }
    
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Exercise getExercise() {
        return exercise;
    }
    
    public void setWorkoutItemSets(ForeignCollection<WorkoutItemSet> workoutItemSets) {
        this.workoutItemSets = workoutItemSets;
    }

    public List<WorkoutItemSet> getWorkoutItemSets() {
        ArrayList<WorkoutItemSet> workoutItemSetList = new ArrayList<WorkoutItemSet>();
        for (WorkoutItemSet workoutItemSet : workoutItemSets) {
        	workoutItemSetList.add(workoutItemSet);
        }
        return workoutItemSetList;
    }
}
