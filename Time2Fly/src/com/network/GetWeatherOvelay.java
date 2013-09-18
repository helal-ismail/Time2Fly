package com.network;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract.Directory;
import android.util.Log;

import com.core.Constants;
import com.core.Utils;

public class GetWeatherOvelay extends AsyncTask<Void, Void, Void> {

	
	@Override
	protected Void doInBackground(Void... arg0) {
		requestData(Constants.WEATHER_064_URL, "064");
		requestData(Constants.WEATHER_128_URL, "128");
		requestData(Constants.WEATHER_256_URL, "256");
		return null;
	}
	
	private void requestData(String url, String type){
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);
			HttpResponse response;
			response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = Utils.getInstance().convertStreamToString(
						instream);
				processResult(result, type);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void processResult(String result, String type) {
		String[] urls = result.split("\n");
		File root = Environment.getExternalStorageDirectory();
		root.mkdir();
		File appDir = new File(root, "Time2Fly");
		appDir.mkdir();
		File dir = new File(appDir, type);
		dir.mkdir();
		
		while(dir.list().length > 0){
			dir.listFiles()[0].delete();
		}
			
		try {
			for (int i = 0; i < urls.length; i++) {
				if(urls[i].equalsIgnoreCase(""))
					continue;
				String image_url = "http://hk.time2fly.org/time2fly/current_radar/"
						+ urls[i];
				URL url = new URL(image_url);
				InputStream is = (InputStream) url.getContent();
				byte[] buffer = new byte[8192];
				int bytesRead;
				ByteArrayOutputStream output = new ByteArrayOutputStream();
				while ((bytesRead = is.read(buffer)) != -1) {
					output.write(buffer, 0, bytesRead);
				}
				byte[] bytes = output.toByteArray();
				File file = new File(dir, urls[i]);
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(bytes);
				fos.close();
			}
		} catch (Exception e) {
			Log.d(Constants.TAG, "Error parsing weather files");
		}

	}

}
