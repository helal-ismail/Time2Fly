package com.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.security.MessageDigest;

import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.app.Activity;
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
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		appInstance = (Time2FlyApp)getApplication();
		setContentView(R.layout.activity_share);
		String path = (String) getIntent().getExtras().get("path");
		Bitmap bmp = BitmapFactory.decodeFile(path);
		BitmapDrawable d = new BitmapDrawable(bmp);
		TextView image = (TextView) findViewById(R.id.image);
		image.setBackgroundDrawable(d);
		File file = new File((String) getIntent().getExtras().get("path"));
		path = file.getPath();
		data = new byte[(int) file.length()];
		try {
			new FileInputStream(file).read(data);
		} catch (Exception e) {
			Toast.makeText(mContext, "Image transformation failed",
					Toast.LENGTH_LONG).show();
		}

		fbShare = (ImageButton) findViewById(R.id.fb_share);
		fbShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				loginToFb();
			}
		});

		checkForSavedLogin();
		twShare = (ImageButton) findViewById(R.id.tw_share);
		twShare.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				askOAuth();
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
			// TODO Auto-generated method stub

		}

		@Override
		public void onFileNotFoundException(FileNotFoundException e,
				Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onMalformedURLException(MalformedURLException e,
				Object state) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onFacebookError(FacebookError e, Object state) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("helal", "" + resultCode);
		if (resultCode == -1) {
			cache.facebook.authorizeCallback(requestCode, resultCode, data);
			post();
		}
	}

	// ========== Twitter Functions =======
	
	private void checkForSavedLogin()  {
		 // Get Access Token and persist it
		try{
		 AccessToken a = cache.twitter.getOAuthAccessToken();
		 if (a==null) 
			 return; //if there are no credentials stored then return to usual activity
		
		 // initialize Twitter4J
		 cache.twitter = new TwitterFactory().getInstance();
		 cache.twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY, Constants.TWITTER_CONSUMER_SECRET);
		 cache.twitter.setOAuthAccessToken(a);
		}
		catch (Exception e) {
			// TODO: handle exception
			return;
		}
		}

	private void askOAuth() {  
		 try {  
		  consumer = new CommonsHttpOAuthConsumer(Constants.TWITTER_CONSUMER_KEY, Constants.TWITTER_CONSUMER_SECRET);  
		  provider = new DefaultOAuthProvider("http://twitter.com/oauth/request_token", "http://twitter.com/oauth/access_token", "http://twitter.com/oauth/authorize");  
		  String authUrl = provider.retrieveRequestToken(consumer, Constants.TWITTER_CALLBACK_URL);  
		  Toast.makeText(this, "Please authorize this app!", Toast.LENGTH_LONG).show();  
		  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(authUrl)));  
		 } catch (Exception e) {  
		  Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();  
		 }  
		}
	
	@Override
	protected void onResume() {
	 super.onResume();
	 if (this.getIntent()!=null && this.getIntent().getData()!=null){
	  Uri uri = this.getIntent().getData();
	  if (uri != null && uri.toString().startsWith(Constants.TWITTER_CALLBACK_URL)) {
	   String verifier = uri.getQueryParameter(oauth.signpost.OAuth.OAUTH_VERIFIER);
	   try {
	    // this will populate token and token_secret in consumer
	    provider.retrieveAccessToken(consumer, verifier);

	    // Get Access Token and persist it
	    AccessToken a = new AccessToken(consumer.getToken(), consumer.getTokenSecret());
	    
	    // initialize Twitter4J
	    cache.twitter = new TwitterFactory().getInstance();
	    cache.twitter.setOAuthConsumer(Constants.TWITTER_CONSUMER_KEY, Constants.TWITTER_CONSUMER_SECRET);
	    cache.twitter.setOAuthAccessToken(a);
	    
	    startFirstActivity();

	   } catch (Exception e) {
	    //Log.e(APP, e.getMessage());
	    e.printStackTrace();
	    Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
	   }
	  }
	 }
	}
	
	public void startFirstActivity(){
		Toast.makeText(mContext, "Twitter Authorized", Toast.LENGTH_LONG).show();
	}
	

}
