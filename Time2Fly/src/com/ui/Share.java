package com.ui;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.core.CacheManager;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;

public class Share extends Activity {

	// Facebook facebook;
	// byte[] data;
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
		// data = new byte[(int) file.length()];
		// try {
		// new FileInputStream(file).read(data);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

		Button share = (Button) findViewById(R.id.share);
		share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				postToWall();
			}
		});
	}
	
	public void postToWall(){
		
		// TEST WITH BUNDLE PARAMS
		CacheManager.getInstance().facebook.dialog(this, "feed", new DialogListener() {
			 
			   @Override
			   public void onFacebookError(FacebookError e) {
			   }
			 
			   @Override
			   public void onError(DialogError e) {
			   }
			 
			   @Override
			   public void onComplete(Bundle values) {
			   }
			 
			   @Override
			   public void onCancel() {
			   }
			  });
	}

}
