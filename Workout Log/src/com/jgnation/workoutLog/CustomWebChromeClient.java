package com.jgnation.workoutLog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class CustomWebChromeClient extends WebChromeClient
{
	private Context appContext;
	
	public CustomWebChromeClient(Context appContext)
	{
		this.appContext = appContext;
	}
	
	public boolean onConsoleMessage(ConsoleMessage cm) 
	{
		Log.d("MyApplication", cm.message() + " -- From line "
  	           + cm.lineNumber() + " of " + cm.sourceId() );
  	    return true;
	}
	  
	/*
	@Override
	public boolean onJsAlert(WebView view, String url, String message, JsResult result)
	{
		return false;
	}
	 */
	
	@Override
	public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) 
	{
		new AlertDialog.Builder(appContext)
			.setTitle("Please confirm:")
	        .setMessage(message)
	        .setPositiveButton(android.R.string.ok,
	                new DialogInterface.OnClickListener()
			        {
			            public void onClick(DialogInterface dialog, int which)
			            {
			                result.confirm();
			            }
			        })
			        .setNegativeButton(android.R.string.cancel,
			                new DialogInterface.OnClickListener()
			        {
			            public void onClick(DialogInterface dialog, int which)
			            {
			                result.cancel();
			            }
			        })
	        .create()
	        .show();

		return true;
	}
	
	@Override
	public boolean onJsAlert(WebView view, String url, String message, final JsResult result) 
	{
		new AlertDialog.Builder(appContext)
		.setTitle("Workout Log Message:")
        .setMessage(message)
        .setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener()
		        {
		            public void onClick(DialogInterface dialog, int which)
		            {
		                result.confirm();
		            }
		        })
        .create()
        .show();

		return true;
	}
}
