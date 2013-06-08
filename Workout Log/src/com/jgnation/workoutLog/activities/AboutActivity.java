package com.jgnation.workoutLog.activities;

import com.jgnation.workoutLog.CustomWebChromeClient;
import com.jgnation.workoutLog.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;

public class AboutActivity extends Activity
{
	protected Handler handler = null;
	protected WebView webView;	
	protected static final String HTML_ROOT = "file:///android_asset/www/";
	
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
    
	@Override
    public void onCreate(Bundle savedInstanceState) 
    {
		super.onCreate(savedInstanceState);
    
		handler = new Handler();
  	  
        setContentView(R.layout.about_layout);
        webView = (WebView) findViewById(R.id.mainView);
        //webView.setBackgroundColor(0x00000000);
        webView.setBackgroundColor(Color.parseColor("#80000000"));
        webView.getSettings().setJavaScriptEnabled(true);  
        //webView.addJavascriptInterface(this, activityName);
        webView.setWebChromeClient(new CustomWebChromeClient(this));
        
		loadPage("AboutView.html");
    }

}
