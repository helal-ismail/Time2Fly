package com.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bugsense.trace.BugSenseHandler;
import com.core.CacheManager;
import com.core.Constants;
import com.core.Time2FlyApp;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.widget.LoginButton;
import com.shared.SharedResources;

public class Splash extends Activity {
	Context mContext = this;
	Time2FlyApp appInstance;
	ProgressDialog fbLoginDialog = null;
	
	CacheManager cache = CacheManager.getInstance();

	private void initUI() {
		int height = getWindowManager().getDefaultDisplay().getHeight();

		switch (getResources().getConfiguration().orientation) {
		case Configuration.ORIENTATION_LANDSCAPE:
			setContentView(R.layout.activity_splash2);
/*			ImageView logo = (ImageView) findViewById(R.id.logo);
			logo.getLayoutParams().height = (int) (0.9 * height);
			logo.getLayoutParams().width = (int) (0.9 * height);*/

			break;
		default:
			setContentView(R.layout.activity_splash);
			/*logo = (ImageView) findViewById(R.id.logo);
			logo.getLayoutParams().height = (int) (0.4 * height);
			logo.getLayoutParams().width = (int) (0.4 * height);*/

			break;
		}
		
		
			Runnable r = new Runnable() {
				@Override
				public void run() {
					Intent intent = new Intent(mContext, Home.class);
					startActivity(intent);
					finish();

					  SharedResources.facebookLogin = false;
				}
			};
			Handler handler = new Handler();
			handler.postDelayed(r, 1500);
		}

		

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(mContext, "c417ebfa");
		appInstance = (Time2FlyApp) getApplication();
		cache.facebook = new Facebook(Constants.FB_APP_ID);
		cache.fbAsyncRunner = new AsyncFacebookRunner(cache.facebook);

		initUI();

	
	}

	
	@Override
	public void onConfigurationChanged(
			android.content.res.Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		initUI();
	};

	// ========= Facebook Functions
	public void loginToFb() {
		String accessToken = appInstance.getFbAccessToken();
		long expires = appInstance.getFbAccessExpires();
		if (accessToken != null) {
			cache.facebook.setAccessToken(accessToken);
			Log.d("FB Sessions", "" + cache.facebook.isSessionValid());
		}
		if (expires != 0) {
			cache.facebook.setAccessExpires(expires);
		}
		if (!cache.facebook.isSessionValid()) {
			cache.facebook.authorize(this, new String[] { "email",
					"publish_stream" }, new DialogListener() {
				@Override
				public void onCancel() {
				}

				@Override
				public void onComplete(Bundle values) {
					appInstance.setFbAccessToken(cache.facebook
							.getAccessToken());
					appInstance.setFbAccessExpires(cache.facebook
							.getAccessExpires());
				}

				@Override
				public void onError(DialogError error) {
				}

				@Override
				public void onFacebookError(FacebookError fberror) {
				}
			});
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		cache.facebook.authorizeCallback(requestCode, resultCode, data);
		if (resultCode == -1) {
			Intent intent = new Intent(mContext, Home.class);
			startActivity(intent);
			finish();
			SharedResources.facebookLogin = true;
		} else {
			Toast.makeText(mContext, "Facbook login failed", Toast.LENGTH_LONG)
					.show();
		}
	}

	
	
	
}
