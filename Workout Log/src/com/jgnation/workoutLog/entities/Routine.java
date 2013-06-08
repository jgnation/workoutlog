package com.jgnation.workoutLog.entities;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Routine 
{
	@DatabaseField(generatedId=true)
    private int id;
    
    @DatabaseField
    private String routineName = "";

    @DatabaseField
    private String description;

    public final static String PROFILE_FIELD_NAME = "profile";
    
    @DatabaseField(foreign=true, foreignAutoRefresh=true, columnName = PROFILE_FIELD_NAME)
    private Profile profile;
    
    @ForeignCollectionField
    private ForeignCollection<Section> sections;
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.routineName = name;
    }

    public String getName() {
        return routineName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    } 
    
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Profile getProfile() {
        return profile;
    }
    
    public void setSections(ForeignCollection<Section> sections) {
        this.sections = sections;
    }

    public List<Section> getSections() {
        ArrayList<Section> sectionList = new ArrayList<Section>();
        for (Section section : sections) {
            sectionList.add(section);
        }
        return sectionList;
    }
}
