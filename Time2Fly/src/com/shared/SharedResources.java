package com.shared;

import java.util.Date;
import java.util.Timer;

import com.google.android.gms.maps.model.LatLng;
import com.ui.MyAnimations;

import android.graphics.Bitmap;

public class SharedResources {

	public static int bearing_angle = 0;
	public static boolean started = false;
	public static Timer timer;
	public static Timer weatherTimer;
	public static int round_robin = 0;
	public static Bitmap weather_bmp;
	public static boolean weatherPlayed = true;
	public static float weather_transparency;
	public static int timePassed = 0;
	public static LatLng hkLatLng = new LatLng(22.3089, 113.9144);
	public static MyAnimations mAnimations;
	public static Date localTime;
	public static boolean searchFilter = false;
	public static boolean facebookLogin = false;
	
}
