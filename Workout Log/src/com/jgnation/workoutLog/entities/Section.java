package com.jgnation.workoutLog.entities;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Section 
{
	@DatabaseField(generatedId=true)
    private int id;
    
    @DatabaseField
    private String sectionName = "";

    @DatabaseField
    private String description;
    
    public final static String ROUTINE_FIELD_NAME = "routine";
    
    @DatabaseField(foreign=true, foreignAutoRefresh=true, columnName = ROUTINE_FIELD_NAME)
    private Routine routine;
    
    @ForeignCollectionField
    private ForeignCollection<Exercise> exercises;
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.sectionName = name;
    }

    public String getName() {
        return sectionName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public void setRoutine(Routine r){
    	this.routine = r;
    }
    
    public Routine getRoutine() {
        return routine;
    }
    
    public void setExercises(ForeignCollection<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<Exercise> getExercises() {
        ArrayList<Exercise> exerciseList = new ArrayList<Exercise>();
        for (Exercise exercise : exercises) {
        	exerciseList.add(exercise);
        }
        return exerciseList;
    }
}
