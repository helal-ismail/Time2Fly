package com.ui;

import com.bugsense.trace.BugSenseHandler;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Settings extends PreferenceActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        BugSenseHandler.initAndStartSession(this, "c417ebfa");

		ActionBar bar = getActionBar();
		bar.setDisplayShowHomeEnabled(false);
		addPreferencesFromResource(R.xml.settings);
		
	}
	
	@Override
	public void onBackPressed() {
		finish();
		Intent home = new Intent(this, Home.class);
		startActivity(home);
	}

}
