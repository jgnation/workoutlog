package com.jgnation.workoutLog.activities;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.jgnation.workoutLog.WorkoutLogApplication;
import com.jgnation.workoutLog.converters.Converter;
import com.jgnation.workoutLog.database.DatabaseManager;
import com.jgnation.workoutLog.entities.Section;
import com.jgnation.workoutLog.serializable.SectionSerializable;

public class RoutineActivity extends CommonActivity 
{
	public static final String EMPTY_ROUTINE_LIST = "{\"sections\":[]}";
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setupInterface("RoutineActivity");
        
        headerText.setText(((WorkoutLogApplication)getApplication()).getCurrentProfile().getName() + " > " 
        		+ ((WorkoutLogApplication)getApplication()).getCurrentRoutine().getName());
        
        loadPage("RoutineView.html");
        
        DatabaseManager.init(this);
    }
	
    public void loadSectionPage(String id)
    {
    	((WorkoutLogApplication)getApplication()).setCurrentSectionId(Integer.parseInt(id));
    	Section currentSection = DatabaseManager.getInstance().getSectionById(Integer.parseInt(id));
    	((WorkoutLogApplication)getApplication()).setCurrentSection(currentSection);
    	
    	//load SectionActivity
    	Intent myIntent = new Intent(this, SectionActivity.class);
    	RoutineActivity.this.startActivity(myIntent);
    }
    
    public void createSection(String sectionName)
    {
    	Section section = new Section();
    	section.setName(sectionName);
    	section.setRoutine(((WorkoutLogApplication)getApplication()).getCurrentRoutine()); //associate with a routine
    	DatabaseManager.getInstance().addSection(section);
    	loadURL("javascript:resetScreen()");
    }
    
    //I need to make sure this is a cascading delete
    public void deleteSection(final String id)
    {
    	//this can be improved
    	//Section section = DatabaseManager.getInstance().getSectionById(Integer.parseInt(id));
    	//DatabaseManager.getInstance().deleteSection(section); 

        
    	progress = ProgressDialog.show(this, "",
			    "Deleting...", true);
 	
    	new Thread() 
		{
			public void run() 
			{
				try
				{
			    	cascadeDeleteSection(Integer.parseInt(id));
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
    
    public void getSections(String callback)
    {
    	final List<Section> sections = DatabaseManager.getInstance().getAllSectionsByRoutineId(((WorkoutLogApplication)getApplication()).getCurrentRoutineId());
    	
    	List<SectionSerializable> serializableSections = new ArrayList<SectionSerializable>();
    	
    	for (Section section : sections)
    	{
    		serializableSections.add(Converter.entityToSerializable(section));
    	}
    	
    	String json = getJSON(serializableSections);
    	
    	final String callbackFunction = "javascript:" + callback + "('" + json + "')";  

        loadURL(callbackFunction);
    }
    
    public String getJSON(List<SectionSerializable> sections)
    {
    	StringWriter writer = new StringWriter();
    	try
    	{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(writer, sections);
		}
    	catch(Exception e)
    	{
			return EMPTY_ROUTINE_LIST;
		}
		return writer.toString();
    }
    
    public void getCurrentRoutine(String callback)
    {
    	final String callbackFunction = "javascript:" + callback + "('" + ((WorkoutLogApplication)getApplication()).getCurrentRoutineId() + "')";  

        loadURL(callbackFunction);
    }
}
