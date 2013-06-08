package com.jgnation.workoutLog.serializable;

public class ExerciseSerializable 
{
    private int id;
    
    private String exerciseName;

    private String description;
    
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.exerciseName = name;
    }

    public String getName() {
        return exerciseName;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    } 
}
