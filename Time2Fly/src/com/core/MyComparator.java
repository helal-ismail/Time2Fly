package com.core;

import java.util.Comparator;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.modules.Tab;


public class MyComparator implements Comparator<Tab>{
	LatLng hkLatLng = new LatLng(22.3089, 113.9144);
	Location hkLoc = new Location("t2f");
	
	@Override
	public int compare(Tab lhs, Tab rhs) {
		hkLoc.setLatitude(hkLatLng.latitude);
		hkLoc.setLongitude(hkLatLng.longitude);
		
		float distance1 = Utils.getInstance().getDistance(lhs, hkLoc);
		float distance2 = Utils.getInstance().getDistance(lhs, hkLoc);
		
		if(distance1 == distance2)
			return 0;
		if(distance1 < distance2)
			return -1;
		else
			return 1;
	}
	

}
