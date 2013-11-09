package com.countyappstest.core;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.countyapps.network.SearchByName;

public class Home extends Activity {

	EditText searchField;
	EditText tagField;
	EditText secretNumField;
	
	LinearLayout name;
	LinearLayout tag;
	LinearLayout secretNum;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
		
		searchField = (EditText)findViewById(R.id.search_field);
		tagField = (EditText)findViewById(R.id.tag_field);
		secretNumField = (EditText)findViewById(R.id.secret_code_field);
		name = (LinearLayout)findViewById(R.id.name);
		tag = (LinearLayout)findViewById(R.id.tag);
		secretNum = (LinearLayout)findViewById(R.id.secret_code);
		
		if(Constants.isSearchByName)
		{
			tag.setVisibility(View.GONE);
			secretNum.setVisibility(View.GONE);
		}
		else
		{
			name.setVisibility(View.GONE);
		}
		TextWatcher textWatcher = new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				String val = arg0+"";
				SearchByName searchRequest = new SearchByName(val, 1,false);
				searchRequest.execute();
				Cache.getInstance().page = 1;
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				
			}
		};
		
		searchField.addTextChangedListener(textWatcher);
		tagField.addTextChangedListener(textWatcher);
		secretNumField.addTextChangedListener(textWatcher);
		
		ListView listview = (ListView)findViewById(R.id.listview);
		Cache.getInstance().adapter = new ListAdapter(this);
		listview.setAdapter(Cache.getInstance().adapter);
		
		
	}



}
