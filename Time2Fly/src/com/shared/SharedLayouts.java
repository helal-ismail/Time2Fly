package com.shared;

import android.app.Activity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ui.R;

public class SharedLayouts {

	
	public static LinearLayout leftSection;
	public static LinearLayout drawer;
	public static LinearLayout drawer1;
	public static LinearLayout drawer2;
	//public static Button sideTray;	
	public static Button back;
	
	public static LinearLayout searchBar;
	public static Button searchButton;
	public static EditText searchField;
	
	public static TextView timeLabel;
	
	public static void initLayouts(Activity mContext){
	
		leftSection = (LinearLayout)mContext.findViewById(R.id.left_section);
		drawer = (LinearLayout) mContext.findViewById(R.id.drawer);
		drawer1 = (LinearLayout) mContext.findViewById(R.id.drawer1);
		drawer2 = (LinearLayout) mContext.findViewById(R.id.drawer2);
		//sideTray = (Button)mContext.findViewById(R.id.side_tray);
		back = (Button) mContext.findViewById(R.id.back);
		
		
		searchBar = (LinearLayout)mContext.findViewById(R.id.search_bar);
		searchButton = (Button)mContext.findViewById(R.id.search_button);
		searchField = (EditText)mContext.findViewById(R.id.search_field);
		
		timeLabel = (TextView)mContext.findViewById(R.id.time_label);
	}

}
