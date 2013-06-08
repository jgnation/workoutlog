package com.jgnation.workoutLog.serializable;


public class RoutineSerializable 
{
    private int id;
    
    private String routineName;

    private String description;
    
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
}
