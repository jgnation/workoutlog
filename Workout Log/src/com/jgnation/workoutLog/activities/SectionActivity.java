package com.jgnation.workoutLog.activities;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.jgnation.workoutLog.Global;
import com.jgnation.workoutLog.converters.Converter;
import com.jgnation.workoutLog.database.DatabaseManager;
import com.jgnation.workoutLog.entities.Exercise;
import com.jgnation.workoutLog.serializable.ExerciseSerializable;

public class SectionActivity extends CommonActivity
{
	public static final String EMPTY_EXERCISE_LIST = "{\"exercises\":[]}";
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setupInterface("SectionActivity");
        
        headerText.setText(Global.currentProfile.getName() + " > " 
        		+ Global.currentRoutine.getName() + " > "
        		+ Global.currentSection.getName());
        
        loadPage("SectionView.html");
        
        DatabaseManager.init(this);
    }
	
    public void loadExercisePage(String id)
    {
    	Global.currentExerciseId = Integer.parseInt(id);
    	Global.currentExercise = DatabaseManager.getInstance().getExerciseById(Global.currentExerciseId);
    	
    	//load SectionActivity
    	Intent myIntent = new Intent(this, ExerciseActivity.class);
    	SectionActivity.this.startActivity(myIntent);
    }
    
    public void createExercise(String exerciseName)
    {
    	Exercise exercise = new Exercise();
    	exercise.setName(exerciseName);
    	exercise.setSection(Global.currentSection); //associate with a routine
    	DatabaseManager.getInstance().addExercise(exercise);
    	loadURL("javascript:resetScreen()");
    }
    
    //I need to make sure this is a cascading delete
    public void deleteExercise(final String id)
    {
    	//this can be improved
    	//Exercise exercise = DatabaseManager.getInstance().getExerciseById(Integer.parseInt(id));
    	//DatabaseManager.getInstance().deleteExercise(exercise); 
        
    	progress = ProgressDialog.show(this, "",
			    "Deleting...", true);
 	
    	new Thread() 
		{
			public void run() 
			{
				try
				{
			    	cascadeDeleteExercise(Integer.parseInt(id));
			        loadURL("javascript:resetScreen()");
				} 
				catch (Exception e) 
				{
					//nothing for now
				}
				progress.dismiss();
			}
		}.start();
    }
    
    public void editSection(String id)
    {
    	
    }
    
    public void getExercises(String callback)
    {
    	final List<Exercise> exercises = DatabaseManager.getInstance().getAllExercisesBySectionId(Global.currentSectionId);
    	
    	List<ExerciseSerializable> serializableExercises = new ArrayList<ExerciseSerializable>();
    	
    	for (Exercise exercise : exercises)
    	{
    		serializableExercises.add(Converter.entityToSerializable(exercise));
    	}
    	
    	String json = getJSON(serializableExercises);
    	
    	final String callbackFunction = "javascript:" + callback + "('" + json + "')";  

        loadURL(callbackFunction);
    }
    
    public String getJSON(List<ExerciseSerializable> exercises)
    {
    	StringWriter writer = new StringWriter();
    	try
    	{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(writer, exercises);
		}
    	catch(Exception e)
    	{
			return EMPTY_EXERCISE_LIST;
		}
		return writer.toString();
    }
    
    public void getCurrentSection(String callback)
    {
    	final String callbackFunction = "javascript:" + callback + "('" + Global.currentSectionId + "')";  

        loadURL(callbackFunction);
    }
}
