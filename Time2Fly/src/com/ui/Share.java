package com.ui;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.core.CacheManager;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.FacebookError;

public class Share extends Activity {

	// Facebook facebook;
	byte[] data;
	String path;
	Context mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
			e.printStackTrace();
		}

		Button share = (Button) findViewById(R.id.share);
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				post();
			}
		});
	}

	public void post() {
		new AsyncTask<Void, Void, Boolean>() {
			@Override
			protected Boolean doInBackground(Void... params) {
				try {
					Bundle bundle = new Bundle();
					try {
						bundle.putByteArray("photo", data);
						bundle.putString("caption", "test caption");

						
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
				if (!result) {

				}

			}
		}.execute();
	}

	public class PhotoUploadListener implements RequestListener {

		@Override
		public void onComplete(final String response, final Object state) {		
			Log.d("helal",response);
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

	

}
