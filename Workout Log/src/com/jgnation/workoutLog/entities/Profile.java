package com.jgnation.workoutLog.entities;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Profile 
{
	@DatabaseField(generatedId=true)
    private int id;
    
    @DatabaseField
    private String profileName = "";

    @DatabaseField
    private String description;
    
    @ForeignCollectionField
    private ForeignCollection<Routine> routines;
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.profileName = name;
    }

    public String getName() {
        return profileName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    
    public void setRoutines(ForeignCollection<Routine> routines) {
        this.routines = routines;
    }

    public List<Routine> getRoutines() {
        ArrayList<Routine> routineList = new ArrayList<Routine>();
        for (Routine routine : routines) {
            routineList.add(routine);
        }
        return routineList;
    }
}
