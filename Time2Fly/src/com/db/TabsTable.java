package com.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.core.CacheManager;
import com.modules.Tab;


public class TabsTable extends TableHelper{
	
	
	static final String CREATE_TABLE = "create table tabs(" +
			"_id integer primary key autoincrement, " +
			"addr text, " +
			"alt real, " +
			"lat real, " +
			"lon real, " +
			"track integer, " +
			"sqw text, " +
			"callSign text, " +
			"unix_ts integer, " +
			"user_id text, " +
			"vspd integer, " +
			"spd integer, " +
			"reg text, " +
			"type text, " +
			"owner text, " +
			"code text);";

	static final String DROP_TABLE = "drop table if exists tabs";
	
	
	public TabsTable(Context ctx) {
		super(ctx);
		TABLE = "tabs";
		COLUMNS = new String[] { "_id", "addr", "alt", "lat", "lon",
				"track", "sqw", "callSign", "unix_ts", "user_id", "vspd", "spd",
				"reg", "type", "owner", "code"};
		PKEY= "id";
	}

	@Override
	public String getTableName() {
		return "tabs";
	}

	@Override
	protected void insert(Object[] args) {
		String addr = (String)args[0] ; 
		double alt = (Double)args[1];  // altitude value in feet
		double lat = (Double)args[2]; // Latitude
		double lon = (Double)args[3]; // Longitude
		int track  = (Integer)args[4]; // track of target
		String sqw = (String)args[5];
		String callSign = (String)args[6];
		int unix_ts = (Integer)args[7];
		String user_id = (String)args[8];
		int vspd = (Integer)args[9]; //vertical rate
		int spd = (Integer)args[10]; //ground speed
		String reg = (String)args[11]; // reg code
		String type = (String)args[12] ; //aircraft type
		String owner = (String)args[13] ; //aircraft owner
		String code = (String)args[14]; //airline code
		
		//Name-Values pairs
		ContentValues vals = new ContentValues();
		vals.put("addr", addr);
		vals.put("alt", alt);
		vals.put("lat", lat);
		vals.put("lon", lon);
		vals.put("track", track);
		vals.put("sqw", sqw);
		vals.put("callSign", callSign);
		vals.put("unix_ts", unix_ts);
		vals.put("user_id", user_id);
		vals.put("vspd", vspd);
		vals.put("spd", spd);
		vals.put("reg", reg);
		vals.put("type", type);
		vals.put("owner", owner);
		vals.put("code", code);
		db.insert(TABLE, null, vals);
		
	}

	@Override
	void updateCash() {
		Cursor c = queryDB();
		if (c.moveToFirst()) {
			do {
				CacheManager.getInstance().addTab(hydrateNewObject(c));
			} while (c.moveToNext());
		}
		c.close();
	}
	
	public void insertTab(Tab tab){
		Object[] array = {
				tab.addr,
				tab.alt,
				tab.lat,
				tab.lon,
				tab.track,
				tab.sqw,
				tab.callSign,
				tab.unix_ts,
				tab.user_id,
				tab.vspd,
				tab.spd,
				tab.reg,
				tab.type,
				tab.owner,
				tab.code
		};
		insertRecord(array);
	}
	
	private Tab hydrateNewObject(Cursor c) {
		Tab tab = new Tab();
		tab.addr = c.getString(0);
		tab.alt = c.getFloat(1);  // altitude value in feet
		tab.lat = c.getFloat(2); // Latitude
		tab.lon = c.getFloat(3); // Longitude
		tab.track  = c.getInt(4); // track of target
		tab.sqw = c.getString(5);
		tab.callSign = c.getString(6);
		tab.unix_ts = c.getInt(7);
		tab.user_id = c.getString(8);
		tab.vspd = c.getInt(9); //vertical rate
		tab.spd = c.getInt(10); //ground speed
		tab.reg = c.getString(11); // reg code
		tab.type = c.getString(12) ; //aircraft type
		tab.owner = c.getString(13) ; //aircraft owner
		tab.code = c.getString(14); //airline code
		return tab;
	}



}
