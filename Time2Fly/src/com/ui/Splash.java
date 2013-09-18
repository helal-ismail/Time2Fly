package com.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;

import com.bugsense.trace.BugSenseHandler;
import com.core.Time2FlyApp;

public class Splash extends Activity{
	Context mContext = this;
	Time2FlyApp appInstance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        BugSenseHandler.initAndStartSession(mContext, "c417ebfa");

		appInstance = (Time2FlyApp)getApplication();
		
		switch (getWindowManager().getDefaultDisplay().getOrientation()) {
		case Configuration.ORIENTATION_PORTRAIT:
			setContentView(R.layout.activity_splash);
			break;
			
		case Configuration.ORIENTATION_LANDSCAPE:
			setContentView(R.layout.activity_splash2);
			break;
		default:
			setContentView(R.layout.activity_splash);
			break;
		}
			
		Handler handler = new Handler();
		Runnable r = new Runnable() {
			@Override
			public void run() {
				Intent homeIntent;
				if(appInstance.isFirstTime())
					homeIntent = new Intent(mContext, Settings.class);
				else
					homeIntent = new Intent(mContext, Home.class);
				startActivity(homeIntent);
				finish();
			}
		};
		handler.postDelayed(r, 2000);
	}
	
	

}
