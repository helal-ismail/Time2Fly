package com.countyappstest.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.countyapps.model.Record;

public class ListAdapter extends BaseAdapter{

	Context mContext;
	
	public ListAdapter(Context context) {
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return Cache.getInstance().searchResults.size();
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int index, View view, ViewGroup arg2) {
		
		Record record = Cache.getInstance().searchResults.get(index);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		LinearLayout customView = (LinearLayout)inflater.inflate(R.layout.custom_record, null);
		
		TextView nameField = (TextView) customView.findViewById(R.id.name_field);
		nameField.setText(record.name);
		
		TextView vehicleField = (TextView) customView.findViewById(R.id.vehicle_field);
		vehicleField.setText(record.vehicle + " - $"+record.amount);
		
		return customView;
	}
	

}
