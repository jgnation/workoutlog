package com.jgnation.workoutLog.activities;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

import com.j256.ormlite.dao.ForeignCollection;
import com.jgnation.workoutLog.CustomWebChromeClient;
import com.jgnation.workoutLog.R;
import com.jgnation.workoutLog.converters.Converter;
import com.jgnation.workoutLog.database.DatabaseManager;
import com.jgnation.workoutLog.entities.Profile;
import com.jgnation.workoutLog.entities.WorkoutItem;
import com.jgnation.workoutLog.entities.WorkoutItemSet;
import com.jgnation.workoutLog.serializable.ProfileSerializable;
import com.jgnation.workoutLog.serializable.WorkoutItemSerializable;
import com.jgnation.workoutLog.serializable.WorkoutItemSetSerializable;
import com.jgnation.workoutLog.WorkoutLogApplication;

import android.os.Bundle;
import android.os.Handler;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends CommonActivity 
{
	public static final String EMPTY_PROFILE_LIST = "{\"profiles\":[]}";
		
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {  		    	
        super.onCreate(savedInstanceState);

        setupInterface("MainActivity");
        
        headerText.setText("");
        
        loadPage("MainView.html");
        
        DatabaseManager.init(this);
    }
    
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) 
//    {    	
//    	if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) 
//    	{
//    		Airpush airpush = new Airpush(getApplicationContext());
//    		airpush.startSmartWallAd();
//    		finish();
//    	}    	
//    	return super.onKeyDown(keyCode, event);  	
//    }

    
    public void loadProfilePage(String id)
    {
    	((WorkoutLogApplication)getApplication()).setCurrentProfileId(Integer.parseInt(id));
    	Profile currentProfile = DatabaseManager.getInstance().getProfileById(Integer.parseInt(id));
    	((WorkoutLogApplication)getApplication()).setCurrentProfile(currentProfile);

    	//load ProfileActivity
    	Intent myIntent = new Intent(this, ProfileActivity.class);
    	MainActivity.this.startActivity(myIntent);
    }
    
    public void loadAboutPage()
    {
    	Intent myIntent = new Intent(this, AboutActivity.class);
    	MainActivity.this.startActivity(myIntent);
    }
    
    public void createProfile(String profileName)
    {    	
    	Profile profile = new Profile();
    	profile.setName(profileName);
    	DatabaseManager.getInstance().addProfile(profile);
        loadURL("javascript:resetScreen()");
    }
    
    //I need to make sure this is a cascading delete
    /*public void deleteProfile(String id)
    {
    	//http://stackoverflow.com/questions/6789075/deleting-using-ormlite-on-android
    	//this can be improved
    	Profile profile = DatabaseManager.getInstance().getProfileById(Integer.parseInt(id));
    	DatabaseManager.getInstance().deleteProfile(profile); 
        loadURL("javascript:resetScreen()");
    }*/
    
    public void deleteProfile(final String id)
    {
    	progress = ProgressDialog.show(this, "",
			    "Deleting...", true);
 	
    	new Thread() 
		{
			public void run() 
			{
				try
				{
			    	cascadeDeleteProfile(Integer.parseInt(id));
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
    
    public void editProfile(String id)
    {
    	int x = 20;
    	x = 30;
    }
    
    public void getProfiles(String callback)
    {   	
    	final List<Profile> profiles = DatabaseManager.getInstance().getAllProfiles();
    	
    	//checkForUserCreatedProfiles(profiles);
    	
    	List<ProfileSerializable> profileSerializableList = new ArrayList<ProfileSerializable>();
    	
    	for (Profile profile : profiles)
    	{
    		profileSerializableList.add(Converter.entityToSerializable(profile));
    	}
    	
    	String json = getJSON(profileSerializableList);
    	
    	final String callbackFunction = "javascript:" + callback + "('" + json + "')";  

        loadURL(callbackFunction);
        
        //call this method after loadURL so the screen is visible when the message appears
        checkForUserCreatedProfiles(profiles);
    }
    
    private void checkForUserCreatedProfiles(List<Profile> profiles)
    {
    	if (profiles.size() < 1)
    	{
    		loadURL("javascript:displayCreateProfileAlert()");
    	}
    	else if (profiles.size() == 1)
    	{
    		if (profiles.get(0).getName().startsWith("Sample Profile - Arnold"))
    		{
    			loadURL("javascript:displayCreateProfileAlert()");
    		}
    	}
    }
    
    public String getJSON(List<ProfileSerializable> profiles)
    {
    	StringWriter writer = new StringWriter();
    	try
    	{
			ObjectMapper mapper = new ObjectMapper();
			mapper.configure(SerializationConfig.Feature.WRAP_ROOT_VALUE, false);
			mapper.writeValue(writer, profiles);
		}
    	catch(Exception e)
		{
			return EMPTY_PROFILE_LIST;
		}
		return writer.toString();
    }     
    

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }  */  
}
