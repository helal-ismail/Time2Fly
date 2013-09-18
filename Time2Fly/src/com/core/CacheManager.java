package com.core;

import android.graphics.Bitmap;
import android.location.Location;

import com.google.android.gms.maps.model.GroundOverlay;
import com.modules.MyHash;
import com.modules.Tab;

public class CacheManager {
	
	private static CacheManager instance = new CacheManager();
	public static CacheManager getInstance(){
		return instance;
	}
	public int update_rate = 5000;
	public MyHash tabs_hash = new MyHash();
	
	
	public Bitmap weather_bmp;
	public String selectedReg = "";
	public Location currentLoc = new Location("t2f"); 
	
	public float zoom= 0;
	public GroundOverlay weatherOverlay;
	public int roundRobin = 0;
	public int cyclesCount = 0;
	
	public int first = 1;
	public boolean isRunning = false;
	public int num_targets;
	
	public void addTab(Tab t){
		Tab oldTab = tabs_hash.get(t.addr);
		if(oldTab !=null)
		{
			t.xLat = oldTab.lat;
			t.xLon = oldTab.lon;
			t.marker = oldTab.marker;
		}	
		else{
			t.xLat = -1;
			t.xLon = -1;
			t.marker = null;
		}
		t.cycles = cyclesCount;
		tabs_hash.put(t.addr, t);
	}
	
	

	
}
