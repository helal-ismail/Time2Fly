package com.network;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.core.CacheManager;
import com.core.Constants;
import com.core.Time2FlyApp;
import com.shared.SharedResources;

public class MyLocationListener implements LocationListener {

	Time2FlyApp appInstance;
	
	public MyLocationListener(Time2FlyApp appInstance) {
		this.appInstance = appInstance;
	}
	
	@Override
	public void onLocationChanged(Location loc) {
		
		loc.getLatitude();
		loc.getLongitude();
		if(!appInstance.isHomeHK())
		{
			CacheManager.getInstance().currentLoc = loc;
		}
		else
		{
			CacheManager.getInstance().currentLoc.setLatitude(SharedResources.hkLatLng.latitude);
			CacheManager.getInstance().currentLoc.setLongitude(SharedResources.hkLatLng.longitude);
		}
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
