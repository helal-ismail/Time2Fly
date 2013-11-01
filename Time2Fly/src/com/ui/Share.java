package com.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;
import java.util.Properties;

import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.PropertyConfiguration;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.core.CacheManager;
import com.core.Constants;
import com.core.Time2FlyApp;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class Share extends Activity {

	// Facebook facebook;
	byte[] data;
	String path;
	Context mContext = this;
	CacheManager cache = CacheManager.getInstance();
	Time2FlyApp appInstance;

	ImageButton fbShare;
	ImageButton twShare;

	CommonsHttpOAuthConsumer consumer;
	DefaultOAuthProvider provider;

	File file;
	String twAccessToken;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		StrictMode.enableDefaults();
		appInstance = (Time2FlyApp) getApplication();
		setContentView(R.layout.activity_share);
		String path = (String) getIntent().getExtras().get("path");
		if (path != null && !path.equalsIgnoreCase("")) {
			cache.sharedImagePath = path;
		}

		try {
			Bitmap bmp = BitmapFactory.decodeFile(cache.sharedImagePath);
			BitmapDrawable d = new BitmapDrawable(bmp);
			TextView image = (TextView) findViewById(R.id.image);
			image.setBackgroundDrawable(d);
			file = new File(cache.sharedImagePath);
			data = new byte[(int) file.length()];

			new FileInputStream(file).read(data);
		} catch (Exception e) {
			Toast.makeText(mContext, "Image transformation failed",
					Toast.LENGTH_LONG).show();
		}

		if (cache.twitterLoaded) {
			updateTwitterStatus task = new updateTwitterStatus();
			task.execute();
			cache.twitterLoaded = false;
		}

		fbShare = (ImageButton) findViewById(R.id.fb_share);
		fbShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loginToFb();
			}
		});

		twShare = (ImageButton) findViewById(R.id.tw_share);
		twShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				TwitterInitTask task = new TwitterInitTask();
				task.execute();
			}
		});
	}

	// =============== Facebook Functions ========
	public void loginToFb() {

		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.ui",
					PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:",
						Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (Exception e) {
			Log.d("helal", e.getMessage());
		}

		if (cache.facebook == null) {
			cache.facebook = new Facebook(Constants.FB_APP_ID);
			cache.fbAsyncRunner = new AsyncFacebookRunner(cache.facebook);

		}

		if (!cache.facebook.isSessionValid()) {
			cache.facebook.authorize(this, new String[] { "email",
					"publish_stream", "read_stream" }, new DialogListener() {
				@Override
				public void onCancel() {
					Log.d("helal", "Canceled");
				}

				@Override
				public void onComplete(Bundle values) {

					Log.d("helal", "Complete");
					// POST HERE
					// post();
				}

				@Override
				public void onError(DialogError error) {

					Log.d("helal", "error");
				}

				@Override
				public void onFacebookError(FacebookError fberror) {

					Log.d("helal", "fb error");
				}
			});
		} else
			post();
	}

	public void post() {
		new AsyncTask<Void, Void, Boolean>() {

			protected void onPreExecute() {
				Toast.makeText(mContext, "Sharing via Facebook!",
						Toast.LENGTH_LONG).show();

			};

			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					Bundle bundle = new Bundle();
					try {
						bundle.putByteArray("photo", data);
						bundle.putString("caption", "Time2Fly");
						bundle.putFloat("place:location:latitude",
								(float) cache.currentLoc.getLatitude());
						bundle.putFloat("place:location:longitude",
								(float) cache.currentLoc.getLongitude());
						bundle.putString("place", "211822778828087");

						CacheManager.getInstance().fbAsyncRunner.request(
								"me/photos", bundle, "POST",
								new PhotoUploadListener(), null);

					} catch (Exception e) {
						e.printStackTrace();
					}

					return true;
				} catch (Exception e) {
					Log.d("TAG", " Exception.." + e);
					return false;
				}

			}

			@Override
			protected void onPostExecute(Boolean result) {
				super.onPostExecute(result);
				if (result)
					Toast.makeText(mContext, "Facebook post success",
							Toast.LENGTH_LONG).show();
				else
					Toast.makeText(mContext, "Facebook post failed",
							Toast.LENGTH_LONG).show();

			}
		}.execute();
	}

	public class PhotoUploadListener implements RequestListener {

		@Override
		public void onComplete(final String response, final Object state) {
			Log.d("helal", response);
		}

		@Override
		public void onIOException(IOException e, Object state) {
			// TODO Autogenerated method stub

		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			// TODO Autogenerated method stub

		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			// TODO Autogenerated method stub

		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			// TODO Autogenerated method stub

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Autogenerated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("helal", "" + resultCode);
		if (resultCode == 1) {
			cache.facebook.authorizeCallback(requestCode, resultCode, data);
			post();
		}
	}

	// ========== Twitter Functions =======

	private class TwitterInitTask extends AsyncTask<Void, Void, Boolean> {
				ProgressDialog dialog;
		
				@Override
				protected void onPreExecute() {
					super.onPreExecute();
					dialog = new ProgressDialog(mContext);
					dialog.setTitle("Time2Fly");
					dialog.setCancelable(false);
					dialog.setIcon(R.drawable.compass_mini);
					dialog.show();
					checkForSavedLogin();
		
				}
		
				@Override
				protected Boolean doInBackground(Void... arg0) {
					return askOAuth();
				}
		
				@Override
				protected void onPostExecute(Boolean result) {
					super.onPostExecute(result);
					dialog.dismiss();
					if (result) {
						Toast.makeText(mContext, "Authorizing App", Toast.LENGTH_LONG)
								.show();
					} else {
						Toast.makeText(mContext, "Failed to authorize app",
								Toast.LENGTH_LONG).show();
					}
					cache.twitterLoaded = result;
				}
			}

	
	private void checkForSavedLogin() {
		// Get Access Token and persist it
		try {
			AccessToken a = cache.twitter.getOAuthAccessToken();
			if (a == null)
				return; // if there are no credentials stored then return to
						// usual activity

			// initialize Twitter4J

			cache.twitter = TwitterFactory.getSingleton();

			// cache.twitter = new TwitterFactory().getInstance();
			cache.twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY,
					Constants.TWITTER_CONSUMER_SECRET);
			cache.twitter.setOAuthAccessToken(a);
		} catch (Exception e) {
			// TODO: handle exception
			return;
		}
	}

	private boolean askOAuth() {
		try {
			consumer = new CommonsHttpOAuthConsumer(
					Constants.TWITTER_CONSUMER_KEY,
					Constants.TWITTER_CONSUMER_SECRET);
			provider = new DefaultOAuthProvider(
					"https://api.twitter.com/oauth/request_token",
					"https://api.twitter.com/oauth/access_token",
					"https://api.twitter.com/oauth/authorize");

			String authUrl = provider.retrieveRequestToken(consumer,
					Constants.TWITTER_CALLBACK_URL);

			startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));
		} catch (Exception e) {
			// Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			return false;
		}
		return true;
	}

	public class updateTwitterStatus extends AsyncTask<String, String, String> {
		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(mContext);
			dialog.setTitle("Time2Fly");
			dialog.setIcon(R.drawable.twitter);
			dialog.setMessage("Sharing Image via Twitter");
			dialog.show();
		}

		protected String doInBackground(String... args) {

			try {
//				ConfigurationBuilder builder = new ConfigurationBuilder();
//				builder.setOAuthConsumerKey(Constants.TWITTER_CONSUMER_KEY);
//				builder.setOAuthConsumerSecret(Constants.TWITTER_CONSUMER_SECRET);
//				twAccessToken = appInstance.getTwitterAccessToken();
//				// access_token_secret =
//				// SharedPreferences.getString(PREF_KEY_OAUTH_SECRET, "");
//				String upload_image_url = postPicture(
//						"/mnt/sdcard/Time2Fly/exports/t2f.jpg", " ");
//				Log.d("upload_image_url="
//						+ upload_image_url.toString() + "", " ");
			cache.twitter.updateStatus("Test");
				

			} catch (Exception e) {
				// Log.d("Twitter Update Error", e.getMessage());
			}
			return null;
		}

		public String postPicture(String fileName, String message) {

			try {
				Log.d("startpostPicture()", " ");
				File file = new File(fileName);
				MediaProvider mProvider = getMediaProvider();

				String accessTokenToken = twAccessToken;
				// String accessTokenSecret = access_token_secret;

				Properties props = new Properties();
				props.put(PropertyConfiguration.MEDIA_PROVIDER, mProvider);
				props.put(PropertyConfiguration.OAUTH_ACCESS_TOKEN,
						accessTokenToken);
				// props.put(PropertyConfiguration.OAUTH_ACCESS_TOKEN_SECRET,
				// accessTokenSecret);
				props.put(PropertyConfiguration.OAUTH_CONSUMER_KEY,
						Constants.TWITTER_CONSUMER_KEY);
				props.put(PropertyConfiguration.OAUTH_CONSUMER_SECRET,
						Constants.TWITTER_CONSUMER_SECRET);
				Configuration conf = new PropertyConfiguration(props);

				ImageUploadFactory factory = new ImageUploadFactory(conf);
				ImageUpload upload = factory.getInstance(mProvider);
				String url;
				url = upload.upload(file, message);
				Log.d("endpostPicture()", " ");
				return url;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		MediaProvider getMediaProvider() {

			Log.d("startgetMediaProvider()", " ");

			String provider = "twitter";

			MediaProvider mProvider;
			if (provider.equals("yfrog"))
				mProvider = MediaProvider.YFROG;
			else if (provider.equals("twitpic"))
				mProvider = MediaProvider.TWITPIC;
			else if (provider.equals("twitter"))
				mProvider = MediaProvider.TWITTER;
			else
				throw new IllegalArgumentException("Picture provider "
						+ provider + " unknown");

			Log.d("endgetMediaProvider()", " ");

			return mProvider;
		}

		protected void onPostExecute(String file_url) {
			// updating UI from Background Thread

			dialog.dismiss();

			runOnUiThread(new Runnable() {
				public void run() {
					Toast.makeText(getApplicationContext(),
							"Status tweeted successfully", Toast.LENGTH_SHORT)
							.show();
				}
			});
		}

	}

}
