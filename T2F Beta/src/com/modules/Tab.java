package com.modules;

import java.util.Date;

import android.location.Location;

import com.core.Utils;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class Tab implements Comparable<Tab> {
	public String addr; // unique hex code
	public float alt; // altitude value in feet
	public float lat; // Latitude
	public float lon; // Longitude
	public int track; // track of target
	public String sqw;
	public String callSign;
	public int unix_ts;
	public String user_id;
	public int vspd; // vertical rate
	public int spd; // ground speed
	public String reg; // reg code
	public String type; // aircraft type
	public String owner; // aircraft owner
	public String code; // airline code
	public Date timeStamp;
	public int cycles = 0;
	public float xLat;
	public float xLon;
	public boolean isActive = true;
	public Marker marker;

	@Override
	public int compareTo(Tab tab) {
		LatLng hkLatLng = new LatLng(22.3089, 113.9144);
		Location hkLoc = new Location("t2f");
		hkLoc.setLatitude(hkLatLng.latitude);
		hkLoc.setLongitude(hkLatLng.longitude);
		float distance1 = Utils.getInstance().getDistance(this, hkLoc);
		float distance2 = Utils.getInstance().getDistance(tab, hkLoc);

		if (distance1 < distance2)
			return -1;
		if (distance1 == distance2)
			return 0;
		else
			return 1;
	}

}
