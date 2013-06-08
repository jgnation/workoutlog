package com.jgnation.workoutLog.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class Exercise 
{
	@DatabaseField(generatedId=true)
    private int id;
    
    @DatabaseField
    private String profileName = "";

    @DatabaseField
    private String description;
    
    public final static String SECTION_FIELD_NAME = "section";
    
    @DatabaseField(foreign=true, foreignAutoRefresh=true, columnName = SECTION_FIELD_NAME)
    private Section section;
    
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
    
    public void setSection(Section s){
    	this.section = s;
    }
    
    public Section getSection() {
        return section;
    }
}
