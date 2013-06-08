package com.jgnation.workoutLog.activities;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.ZIZtyXs.iENDTPu129642.Airpush;
import com.jgnation.workoutLog.Global;
import com.jgnation.workoutLog.R;
import com.jgnation.workoutLog.converters.Converter;
import com.jgnation.workoutLog.database.DatabaseManager;
import com.jgnation.workoutLog.entities.Profile;
import com.jgnation.workoutLog.entities.Routine;
import com.jgnation.workoutLog.serializable.RoutineSerializable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

public class ProfileActivity extends CommonActivity
{
	public static final String EMPTY_ROUTINE_LIST = "{\"routines\":[]}";
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        Airpush airpush = new Airpush(this);
		airpush.startSmartWallAd();
		
        super.onCreate(savedInstanceState);

        setupInterface("ProfileActivity");
        
        headerText.setText(Global.currentProfile.getName());        
        
        loadPage("ProfileView.html");
        
        DatabaseManager.init(this);
    }
    
    public void loadRoutinePage(String id)
    {
    	Global.currentRoutineId = Integer.parseInt(id);
    	Global.currentRoutine = DatabaseManager.getInstance().getRoutineById(Global.currentRoutineId);
    	
    	//load RoutineActivity
    	Intent myIntent = new Intent(this, RoutineActivity.class);
    	ProfileActivity.this.startActivity(myIntent);
    }
    
    public void createRoutine(String routineName)
    {
    	Routine routine = new Routine();
    	routine.setName(routineName);
    	routine.setProfile(Global.currentProfile); //associate with a profile
    	DatabaseManager.getInstance().addRoutine(routine);
    	loadURL("javascript:resetScreen()");
    }
    
    //I need to make sure this is a cascading delete
    public void deleteRoutine(final String id)
    {
    	//this can be improved
    	//Routine routine = DatabaseManager.getInstance().getRoutineById(Integer.parseInt(id));
    	//DatabaseManager.getInstance().deleteRoutine(routine); 
        
    	progress = ProgressDialog.show(this, "",
			    "Deleting...", true);
 	
    	new Thread() 
		{
			public void run() 
			{
				try
				{
			    	cascadeDeleteRoutine(Integer.parseInt(id));
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
    
    public void editRoutine(String id)
    {
    	
    }
    
    public void getRoutines(String callback)
    {
    	final List<Routine> routines = DatabaseManager.getInstance().getAllRoutinesByProfileId(Global.currentProfileId);
    	
    	List<RoutineSerializable> serializableRoutines = new ArrayList<RoutineSerializable>();
    	
    	for (Routine routine : routines)
    	{
    		serializableRoutines.add(Converter.entityToSerializable(routine));
    	}
    	
    	String json = getJSON(serializableRoutines);
    	
    	final String callbackFunction = "javascript:" + callback + "('" + json + "')";  

        loadURL(callbackFunction);
    }
    
    public String getJSON(List<RoutineSerializable> routines)
    {
    	StringWriter writer = new StringWriter();
    	try
    	{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(writer, routines);
		}
    	catch(Exception e)
    	{
			return EMPTY_ROUTINE_LIST;
		}
		return writer.toString();
    }
    
    public void getCurrentProfile(String callback)
    {
    	final String callbackFunction = "javascript:" + callback + "('" + Global.currentProfileId + "')";  

        loadURL(callbackFunction);
    }
}
