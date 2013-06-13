package com.jgnation.workoutLog.activities;

import java.util.List;

import com.jgnation.workoutLog.CustomWebChromeClient;
import com.jgnation.workoutLog.R;
import com.jgnation.workoutLog.database.DatabaseManager;
import com.jgnation.workoutLog.entities.Exercise;
import com.jgnation.workoutLog.entities.Profile;
import com.jgnation.workoutLog.entities.Routine;
import com.jgnation.workoutLog.entities.Section;
import com.jgnation.workoutLog.entities.WorkoutItem;
import com.jgnation.workoutLog.entities.WorkoutItemSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint({ "SetJavaScriptEnabled", "SetJavaScriptEnabled" })
public class CommonActivity extends Activity
{
	protected Handler handler = null;
	protected WebView webView;
	ProgressDialog progress;
	
	protected static final String HTML_ROOT = "file:///android_asset/www/";
	
	protected Button selectButton;
	protected Button createButton;
	//protected Button editButton;
	protected Button deleteButton;
	protected Button historyButton;
	protected TextView headerText;
	
    protected void loadURL(final String in)
    {
    	handler.post(new Runnable() {
            public void run() {
            	webView.loadUrl(in);
            }
        });
    }
    
    protected void loadPage(String in)
    {
    	final String url = HTML_ROOT + in;
    	loadURL(url);
    }
    
    protected void setupInterface(String activityName)
    {
    	handler = new Handler();
    	  
        setContentView(R.layout.main_layout);
        webView = (WebView) findViewById(R.id.mainView);
        //webView.setBackgroundColor(0x00000000);
        webView.setBackgroundColor(Color.parseColor("#80000000"));
        webView.getSettings().setJavaScriptEnabled(true);  
        webView.addJavascriptInterface(this, activityName);        
        webView.setWebChromeClient(new CustomWebChromeClient(this));
        /*webView.setWebViewClient(new WebViewClient() {
        	@Override
     	   public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
     		  webView.setBackgroundColor(0x00000000);
     	    }
     	});*/
        
        //this is to fix the 'no keyboard' bug
        //http://stackoverflow.com/questions/3460915/webview-textarea-doesnt-pop-up-the-keyboard
        webView.requestFocus(View.FOCUS_DOWN);
        webView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus())
                        {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });
        
        headerText =(TextView)findViewById(R.id.header_text);
        
        selectButton = (Button) findViewById(R.id.selectButton);
        selectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	final String callbackFunction = "javascript:selectButtonClick()"; 
                loadURL(callbackFunction);
                selectButton.setBackgroundResource(R.drawable.tab_active);
                createButton.setBackgroundResource(R.drawable.tab_inactive);
                //editButton.setBackgroundResource(R.drawable.tab_inactive);
                deleteButton.setBackgroundResource(R.drawable.tab_inactive);
            }
        });
        
        createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //createProfile();
            	final String callbackFunction = "javascript:createButtonClick()"; 
                loadURL(callbackFunction);
                createButton.setBackgroundResource(R.drawable.tab_active);
                //editButton.setBackgroundResource(R.drawable.tab_inactive);
                deleteButton.setBackgroundResource(R.drawable.tab_inactive);
                selectButton.setBackgroundResource(R.drawable.tab_inactive);
            }
        });
        
        /*editButton = (Button) findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	final String callbackFunction = "javascript:editButtonClick()"; 
                loadURL(callbackFunction);
                editButton.setBackgroundResource(R.drawable.tab_active);
                createButton.setBackgroundResource(R.drawable.tab_inactive);
                deleteButton.setBackgroundResource(R.drawable.tab_inactive);
                selectButton.setBackgroundResource(R.drawable.tab_inactive);
            }
        });*/
        
        deleteButton = (Button) findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	final String callbackFunction = "javascript:deleteButtonClick()"; 
                loadURL(callbackFunction);
                deleteButton.setBackgroundResource(R.drawable.tab_active);
                createButton.setBackgroundResource(R.drawable.tab_inactive);
                //editButton.setBackgroundResource(R.drawable.tab_inactive);
                selectButton.setBackgroundResource(R.drawable.tab_inactive);
            }
        });
        
        selectButton.setBackgroundResource(R.drawable.tab_active);
        
       /* //setup banner
    	final WebView bannerView = (WebView) findViewById(R.id.bannerView);
    	bannerView.getSettings().setJavaScriptEnabled(true);  
    	bannerView.setWebViewClient(new WebViewClient());
    	bannerView.setBackgroundColor(Color.BLACK);
    	//load page for banner
    	final String bannerUrl = HTML_ROOT + "BannerView.html";
    	handler.post(new Runnable() {
            public void run() {
            	bannerView.loadUrl(bannerUrl);
            }
        });*/
    }
    
    protected void setupInterface2(String activityName)
    {
    	handler = new Handler();
  	  
        setContentView(R.layout.final_screen);
        webView = (WebView) findViewById(R.id.mainView);
        //transparent background
        //webView.setBackgroundColor(0x00000000);
        //dimming background
        webView.setBackgroundColor(Color.parseColor("#80000000"));
        webView.getSettings().setJavaScriptEnabled(true);  
        webView.addJavascriptInterface(this, activityName);
        webView.setWebChromeClient(new CustomWebChromeClient(this));
        webView.setWebViewClient(new WebViewClient() {
        	   public void onPageFinished(WebView view, String url) {
        		   if (progress != null) {
        			   progress.dismiss();
        		   }
        	    }
        	});
        
        //this is to fix the 'no keyboard' bug
        //http://stackoverflow.com/questions/3460915/webview-textarea-doesnt-pop-up-the-keyboard
        webView.requestFocus(View.FOCUS_DOWN);
        webView.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_UP:
                        if (!v.hasFocus())
                        {
                            v.requestFocus();
                        }
                        break;
                }
                return false;
            }
        });
        
        headerText =(TextView)findViewById(R.id.header_text);
        
        createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	final String callbackFunction = "javascript:createButtonClick()"; 
                loadURL(callbackFunction);
                createButton.setBackgroundResource(R.drawable.tab_active);
                historyButton.setBackgroundResource(R.drawable.tab_inactive);
            }
        });
          
        historyButton = (Button) findViewById(R.id.historyButton);
        historyButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	final String callbackFunction = "javascript:historyButtonClick()"; 
                loadURL(callbackFunction);
                historyButton.setBackgroundResource(R.drawable.tab_active);
                createButton.setBackgroundResource(R.drawable.tab_inactive);
            }
        });
        
        createButton.setBackgroundResource(R.drawable.tab_active);
        
       /* //setup banner
    	final WebView bannerView = (WebView) findViewById(R.id.bannerView);
    	bannerView.getSettings().setJavaScriptEnabled(true);  
    	bannerView.setWebViewClient(new WebViewClient());
    	bannerView.setBackgroundColor(Color.BLACK);
    	//load page for banner
    	final String bannerUrl = HTML_ROOT + "BannerView.html";
    	handler.post(new Runnable() {
            public void run() {
            	bannerView.loadUrl(bannerUrl);
            }
        });*/
    }
    
    //this is called from javascript
    public void resetScreen()
    {
		runOnUiThread(new Runnable() {
		    public void run() {
		    	selectButton.setBackgroundResource(R.drawable.tab_active);
		    }
		});

		runOnUiThread(new Runnable() {
		    public void run() {
		        createButton.setBackgroundResource(R.drawable.tab_inactive);
		    }
		});

		/*runOnUiThread(new Runnable() {
		    public void run() {
		        editButton.setBackgroundResource(R.drawable.tab_inactive);
		    }
		});*/

		runOnUiThread(new Runnable() {
		    public void run() {
		        deleteButton.setBackgroundResource(R.drawable.tab_inactive);
		    }
		});
    }
    
    //this is called from javascript
    public void resetScreen2()
    {
		runOnUiThread(new Runnable() {
		    public void run() {
		    	createButton.setBackgroundResource(R.drawable.tab_active);
		    }
		});

		runOnUiThread(new Runnable() {
		    public void run() {
		        historyButton.setBackgroundResource(R.drawable.tab_inactive);
		    }
		});
    }
    
    ///////////////////////////////////////////////////////
    
    public void cascadeDeleteProfile(int id)
    {
    	Profile profile = DatabaseManager.getInstance().getProfileById(id);
    	
    	//get all routines from this profile
    	List<Routine> routines = DatabaseManager.getInstance().getAllRoutinesByProfileId(id);
    	
    	for (Routine routine : routines)
    	{
    		cascadeDeleteRoutine(routine.getId());
    	}
    	
    	DatabaseManager.getInstance().deleteProfile(profile); 
    }
    
    public void cascadeDeleteRoutine(int id)
    {
    	Routine routine = DatabaseManager.getInstance().getRoutineById(id);
    	
    	//get all sections from this routine
    	List<Section> sections = DatabaseManager.getInstance().getAllSectionsByRoutineId(id);
    	
    	for (Section section : sections)
    	{
    		cascadeDeleteSection(section.getId());
    	}
    	
    	DatabaseManager.getInstance().deleteRoutine(routine); 
    }
    
    public void cascadeDeleteSection(int id)
    {
    	Section section = DatabaseManager.getInstance().getSectionById(id);
    	
    	//get all the exercises from this section
    	List<Exercise> exercises = DatabaseManager.getInstance().getAllExercisesBySectionId(id);
    	
    	for (Exercise exercise : exercises)
    	{
    		cascadeDeleteExercise(exercise.getId());
    	}
    	
    	DatabaseManager.getInstance().deleteSection(section); 
    }
    
    public void cascadeDeleteExercise(int id)
    {
    	Exercise exercise = DatabaseManager.getInstance().getExerciseById(id);
    	
    	//get all the workoutItems from this exercise
    	List<WorkoutItem> workoutItems = DatabaseManager.getInstance().getAllWorkoutItemsByExerciseId(id);
    	
    	for (WorkoutItem workoutItem : workoutItems)
    	{
    		cascadeDeleteWorkoutItem(workoutItem.getId());
    	}
    	
    	DatabaseManager.getInstance().deleteExercise(exercise);
    }
    
    public void cascadeDeleteWorkoutItem(int id)
    {
    	WorkoutItem workoutItem = DatabaseManager.getInstance().getWorkoutItemById(id);
    	
    	//get all workoutItemSets from this workoutItem
    	List<WorkoutItemSet> workoutItemSets = DatabaseManager.getInstance().getAllWorkoutItemSetsByWorkoutItemId(id);
    	
    	for (WorkoutItemSet workoutItemSet : workoutItemSets)
    	{
    		cascadeDeleteWorkoutItemSet(workoutItemSet.getId());
    	}
    	
    	DatabaseManager.getInstance().deleteWorkoutItem(workoutItem);
    }
    
    public void cascadeDeleteWorkoutItemSet(int id)
    {
    	WorkoutItemSet workoutItemSet = DatabaseManager.getInstance().getWorkoutItemSetById(id);
    	
    	DatabaseManager.getInstance().deleteWorkoutItemSet(workoutItemSet);
    }
}
