package com.core;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

import com.modules.ResponseBlock;

public class Time2FlyApp extends Application {
	private static Context context;
	protected SharedPreferences prefs;

	@Override
	public void onCreate() {
		super.onCreate();
		context = getApplicationContext();
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());

	}
	
	
	@Override
	public void onTerminate() {
		super.onTerminate();
	}

	public static Context getAppContext() {
		return context;
	}

	// ===== Settings Functions =====
	public void updateResponseVals(String unix_ts, int update_rate,
			String version) {
		Editor editor = prefs.edit();
		editor.putString(Constants.UNIX_TS, unix_ts);
		editor.putInt(Constants.UPDATE_RATE, update_rate);
		editor.putString(Constants.VERSION, version);
		editor.commit();
	}

	public ResponseBlock getResponseVals() {
		String ts = prefs.getString(Constants.UNIX_TS, "");
		int rate = prefs.getInt(Constants.UPDATE_RATE, 10);
		String ver = prefs.getString(Constants.VERSION, "");
		ResponseBlock r = new ResponseBlock(ts, rate, ver);
		return r;

	}

	// ===== Settings Functions =====
	
	
	public boolean isHomeHK()
	{
		return prefs.getBoolean(Constants.ST_HOME_AS_HK, true);
	}
	
	public boolean isWeatheroverlayEnabled(){
		return prefs.getBoolean(Constants.ST_WEATHER_ENABLED, false);
	}
	
	public boolean isWeatheroverlayAutoUpdate(){
		return prefs.getBoolean(Constants.ST_WEATHER_AUTO_UPDATE, false);
	}
	
	public boolean isSocialLoginEnabled(){
		return prefs.getBoolean(Constants.ST_SOCIAL_ENABLED, false);
	}
	
	public int getWeatheroverlayAutoupdateRate(){
		String minutes = prefs.getString(Constants.ST_WEATHER_AUTO_UPDATE_RATE, "15");
		int min = Integer.parseInt(minutes);
		int milliseconds = min * 60 * 1000;
		return milliseconds;
	}
	
	public float getWeatherOverlayTransparency(){
		String val =  prefs.getString(Constants.ST_OVERLAY_TRANSPARENCY, "0.75");
		float result = Float.parseFloat(val);
		return result;
	}
	
	public boolean isFirstTime(){
		return prefs.getBoolean(Constants.IS_FIRST_TIME, false);
	}
	
	public void setFbAccessToken(String accessToken){
		Editor editor = prefs.edit();
		editor.putString(Constants.FB_ACCESS_TOKEN, accessToken);
		editor.commit();
	}
	
	public String getFbAccessToken()
	{
		return  prefs.getString(Constants.FB_ACCESS_TOKEN, null);

	}
	
	
	public void setFbAccessExpires(long accessExpires){
		Editor editor = prefs.edit();
		editor.putLong(Constants.FB_ACCESS_EXPIRES, accessExpires);
		editor.commit();
	}
	
	public Long getFbAccessExpires()
	{
		return  prefs.getLong(Constants.FB_ACCESS_EXPIRES, 0);
	}
	
	public void setTwitterAccessToken(String twToken){
		Editor editor = prefs.edit();
		editor.putString(Constants.TW_LOGIN_PARAMS, twToken);
		editor.commit();
	}
	
	public String getTwitterAccessToken(){
		return  prefs.getString(Constants.TW_LOGIN_PARAMS, "");
	}
	
	public void setTwitterLoggedIn(boolean loggedIn){
		Editor editor = prefs.edit();
		editor.putBoolean(Constants.TWITTER_IS_LOGGED_IN,loggedIn);
		editor.commit();
	}
	
	public boolean isTwitterLoggedIn(){
		return prefs.getBoolean(Constants.TWITTER_IS_LOGGED_IN, false);
	}

}
