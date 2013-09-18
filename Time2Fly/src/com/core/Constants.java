package com.core;

public class Constants {
	
	public static String UPDATE_RATE = "update_rate";
	public static String UNIX_TS = "unix_ts";
	public static String VERSION = "version";
	public static String JSON_URL = "http://hk.time2fly.org/time2fly/ajax_map_post.php";
	public static String TAG = "helal";
	public static long TS_THRESHOLD = 20 * 1000;
	public static long TS_REMOVE = 30 * 1000;
	
	public static String WEATHER_256_URL = "http://hk.time2fly.org/time2fly/current_radar/filelist_256.txt";
	public static String WEATHER_128_URL = "http://hk.time2fly.org/time2fly/current_radar/filelist_128.txt";
	public static String WEATHER_064_URL = "http://hk.time2fly.org/time2fly/current_radar/filelist_064.txt";
	

	//SETTINGS CONSTS
	public static String ST_WEATHER_ENABLED = "weather_enabled";
	public static String ST_HOME_AS_HK = "home_as_hk";
	public static String ST_WEATHER_AUTO_UPDATE = "weather_auto_update";
	public static String ST_WEATHER_AUTO_UPDATE_RATE = "weather_auto_update_rate";
	public static String ST_OVERLAY_TRANSPARENCY = "overlay_transparency";	
	public static String IS_FIRST_TIME = "is_first_time";
}
