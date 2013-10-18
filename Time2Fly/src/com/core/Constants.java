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
	public static String ST_SOCIAL_ENABLED = "social_login_enabled";
	
	  //==== Twitter API =====
    public static String TWITTER_CONSUMER_KEY = "fsa2F7hImgHfcv1NpV8w";
    public static String TWITTER_CONSUMER_SECRET = "EqLLW9M4hy3DDDP5tyjqmYtR5Mi8LTV5Ov1sjhG6C4";
    public static String TWITTER_PREFERENCE_NAME = "twitter_oauth";
    public static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    public static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    public static final String PREF_KEY_TWITTER_LOGIN = "isTwitterLogedIn";
    public static final String TWITTER_CALLBACK_URL = "oauth://time2fly";
    public static final String URL_TWITTER_AUTH = "auth_url";
    public static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    public static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    public static final String TWITTER_IS_LOGGED_IN = "twitter_logged_in";
    public static final String TW_LOGIN_PARAMS = "tw_login_params";
    
    
    
    //FB API
    public static final String FB_APP_ID = "1418792551675833";
    public static final String FB_ACCESS_EXPIRES = "access_expires";
    public static final String FB_ACCESS_TOKEN = "access_token";
}
