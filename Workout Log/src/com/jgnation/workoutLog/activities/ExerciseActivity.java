package com.jgnation.workoutLog.activities;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.j256.ormlite.dao.ForeignCollection;
import com.jgnation.workoutLog.WorkoutLogApplication;
import com.jgnation.workoutLog.converters.Converter;
import com.jgnation.workoutLog.database.DatabaseManager;
import com.jgnation.workoutLog.entities.WorkoutItem;
import com.jgnation.workoutLog.entities.WorkoutItemSet;
import com.jgnation.workoutLog.serializable.WorkoutItemSerializable;
import com.jgnation.workoutLog.serializable.WorkoutItemSetSerializable;

public class ExerciseActivity extends CommonActivity
{
	public static final String EMPTY_WORKOUT_ITEM_LIST = "{\"workoutItem\":[]}";
	
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        setupInterface2("ExerciseActivity");
        
        headerText.setText(((WorkoutLogApplication)getApplication()).getCurrentProfile().getName() + " > " 
        		+ ((WorkoutLogApplication)getApplication()).getCurrentRoutine().getName() + " > "
        		+ ((WorkoutLogApplication)getApplication()).getCurrentSection().getName() + " > "
        		+ ((WorkoutLogApplication)getApplication()).getCurrentExercise().getName());
        
        loadPage("ExerciseView.html");
        
        DatabaseManager.init(this);
    }
	
	//i think i can delete this
	public void reloadThisPage()
	{
		loadPage("ExerciseView.html");
	}
	
	//for some reason an extra workoutset w/ id = 0 is created with save - fix that!
	//maybe something to do with getemptyforeigncollection?
	//or maybe something wrong with annotations in entities?
	public void createWorkoutItem(final String stringifiedWorkoutItem)
	{	
		progress = ProgressDialog.show(this, "",
			    "Saving...", true);
		
		new Thread() 
		{
			public void run() 
			{
				try
				{
					WorkoutItemSerializable item = getObject(stringifiedWorkoutItem);
					WorkoutItem workoutItem = Converter.serializableToEntity(item, ((WorkoutLogApplication)getApplication()).getCurrentExercise()); 
					
					List<WorkoutItemSet> workoutItemSets = new ArrayList<WorkoutItemSet>();
					List<WorkoutItemSetSerializable> serializableSets = item.getWorkoutItemSets();
					for (WorkoutItemSetSerializable set : serializableSets)
					{
						workoutItemSets.add(Converter.serializableToEntity(set));
					}
					
					ForeignCollection<WorkoutItemSet> workoutItemSetsColl = 
							DatabaseManager.getInstance().getEmptyWorkoutItemForeignCollectionMember();
					
					//DatabaseManager.getInstance().addWorkoutItem(workoutItem);
					
					for (WorkoutItemSet s : workoutItemSets)
					{
						s.setWorkoutItem(workoutItem);
						workoutItemSetsColl.add(s);
					}
					
					workoutItem.setWorkoutItemSets(workoutItemSetsColl);
					
					//it has to be added here, not above where it is commented out!
					DatabaseManager.getInstance().addWorkoutItem(workoutItem);
					
					List<WorkoutItemSet> wList = workoutItem.getWorkoutItemSets();
					for (WorkoutItemSet w : wList)
					{
						DatabaseManager.getInstance().addWorkoutItemSet(w);
					}
					
					//doCleanup();
					//loadPage("ExerciseView.html");
					loadURL("javascript:resetScreen()");
				} 
				catch (Exception e) 
				{
					//nothing for now
				}
				// progress is dismissed on the onPageFinishedLoading overloaded method of webviewclient
				
				//I am now calling this here since I commented out calling the loadPage method
				progress.dismiss();
			}
		}.start();		
	}
	
	//this ensures that only a maximum of 20 workout items are in the DB at once.  Oldest gets deleted.
	//this is no longer being used, since I added the ability for the user to delete individual items
	/*private void doCleanup()
	{
		//right here I should delete the crap workoutItemSets that reference workoutItem w/ id = 0
		
		List<WorkoutItem> workoutItems = getWorkoutItems();
		
		if (workoutItems.size() > 20)
		{
			cascadeDeleteWorkoutItem(workoutItems.get(workoutItems.size() - 1).getId());
		}
	}*/
	
	public void getWorkoutItemAndSets(String callback)
	{
		//do null checks in here
		List<WorkoutItem> workoutItems = getWorkoutItems();
		
		List<WorkoutItemSerializable> serializableList = Converter.entityToSerializable(workoutItems);

		String json = getJSON(serializableList);
    	
		Calendar c = Calendar.getInstance();

		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		String a = df.format(c.getTime());
    	
    	final String callbackFunction = "javascript:" + callback + "('" + json + "', '" + a + "')";  


        loadURL(callbackFunction);
	}
	
	public List<WorkoutItem> getWorkoutItems()
	{
		//this should be a list of one item
		return DatabaseManager.getInstance().getAllWorkoutItemsByExerciseId(((WorkoutLogApplication)getApplication()).getCurrentExerciseId());
	}
	
	//pretty sure this can be deleted?
	public List<WorkoutItemSet> getWorkoutItemSets(int workoutItemId)
	{
		//return DatabaseManager.getInstance().getAllWorkoutItemSetsByExerciseId(Global.currentExerciseId);
		return DatabaseManager.getInstance().getAllWorkoutItemSetsByWorkoutItemId(workoutItemId);
	}
	
	//this will be hit when the user hits 'complete workout' on the ui
	public WorkoutItemSerializable getObject(String jsonObject)
	{
		ObjectMapper mapper = new ObjectMapper();
		WorkoutItemSerializable workoutItem = new WorkoutItemSerializable();
		
		try 
		{
			workoutItem = mapper.readValue(jsonObject, WorkoutItemSerializable.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return workoutItem;
	}
	
    public String getJSON(List<WorkoutItemSerializable> workoutItems)
    {
    	StringWriter writer = new StringWriter();
    	try
    	{
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(writer, workoutItems);
		}
    	catch(Exception e)
    	{
			return EMPTY_WORKOUT_ITEM_LIST;
		}
		return writer.toString();
    }
    
    public void deleteHistoryItem(final String id, final String callback)
    {
    	//delete history item here
    	progress = ProgressDialog.show(this, "",
			    "Deleting...", true);
 	
    	new Thread() 
		{
			public void run() 
			{
				try
				{
					cascadeDeleteWorkoutItem(Integer.parseInt(id));
			    	getWorkoutItemAndSets(callback);
				} 
				catch (Exception e) 
				{
					//nothing for now
				}
				progress.dismiss();
			}
		}.start();
    }
}
