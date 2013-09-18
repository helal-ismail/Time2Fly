package com.network;

import com.core.Constants;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

public class MyLocationListener implements LocationListener {
	@Override
	public void onLocationChanged(Location loc) {
		
		loc.getLatitude();
		loc.getLongitude();
		Log.d(Constants.TAG, "Loc Changed : "+loc.getLongitude() + " - " + loc.getLatitude());
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {

	}

}
