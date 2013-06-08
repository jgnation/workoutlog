package com.jgnation.workoutLog.serializable;

public class SectionSerializable 
{
    private int id;
    
    private String sectionName;

    private String description;
    
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
}
