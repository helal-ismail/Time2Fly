package com.ui;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.bugsense.trace.BugSenseHandler;
import com.ui.R;

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
	}

}
