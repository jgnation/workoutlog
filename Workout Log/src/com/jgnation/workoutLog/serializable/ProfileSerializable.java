package com.jgnation.workoutLog.serializable;


public class ProfileSerializable 
{
    private int id;
    
    private String profileName;

    private String description;
    
    
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
    
}
