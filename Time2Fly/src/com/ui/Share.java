package com.ui;

import java.io.File;
import java.io.FileInputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.core.Constants;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class Share extends Activity {

	Facebook facebook;
	byte[] data;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share);
		String path = (String) getIntent().getExtras().get("path");
		Bitmap bmp = BitmapFactory.decodeFile(path);
		BitmapDrawable d = new BitmapDrawable(bmp);
		TextView image = (TextView) findViewById(R.id.image);
		image.setBackgroundDrawable(d);
		facebook = new Facebook("1418792551675833");
		File file = new File((String) getIntent().getExtras().get("path"));
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

	private void post() {
		facebook.authorize(this, new String[] { "publish_stream" },
				new DialogListener() {

					@Override
					public void onFacebookError(FacebookError e) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onError(DialogError dialogError) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onComplete(Bundle values) {
						postToWall(values.getString(Facebook.TOKEN));
					}

					@Override
					public void onCancel() {
						// TODO Auto-generated method stub
					}
				});

	}

	private void postToWall(String accessToken) {
		Bundle params = new Bundle();

		params.putString(Facebook.TOKEN, accessToken);

		// The byte array is the data of a picture.
		params.putByteArray("picture", data);

		try {
			facebook.request("me/photos", params, "POST");

		} catch (Exception e) {
			Log.d(Constants.TAG, e.getMessage());
		}
	}

}
