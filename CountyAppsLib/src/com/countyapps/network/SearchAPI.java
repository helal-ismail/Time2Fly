package com.countyapps.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.LinearLayout;

import com.countyapps.model.Record;
import com.countyappstest.core.Cache;
import com.countyappstest.core.R;

public class SearchAPI extends AsyncTask<Void, Void, JSONObject> {

	String URL;
	Context mContext;
	boolean append;
	
	
	@Override
	protected JSONObject doInBackground(Void... params) {

		try {			
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(URL);
			HttpResponse response;
			response = httpclient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			JSONObject obj = null;
			if (entity != null) {
				InputStream instream = entity.getContent();
				String result = convertStreamToString(instream);
				obj = new JSONObject(result);
			}
			return obj;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@Override
	protected void onPostExecute(JSONObject result) {
		super.onPostExecute(result);
		if(result != null)
		{
			if(!append)
				Cache.getInstance().searchResults.clear();
			JSONArray results = result.optJSONArray("results");
			for(int i = 0 ; i < results.length() ;  i++)
			{
				JSONObject jsonRecord = results.optJSONObject(i);
				String name = jsonRecord.optString("name");
				String vehicle = jsonRecord.optString("vehicle");
				String id = jsonRecord.optString("id");
				double amount = jsonRecord.optDouble("amount");
				String vin = jsonRecord.optString("vin");
				boolean is_suspended = jsonRecord.optBoolean("is_suspended");
				boolean is_renewed = jsonRecord.optBoolean("is_renewed");
				boolean can_renew = jsonRecord.optBoolean("can_renew");
				String expiration_date = jsonRecord.optString("expiration_date");
				String street_address = jsonRecord.optString("street_address");
				String city = jsonRecord.optString("city");
				String state = jsonRecord.optString("state");
				String zip = jsonRecord.optString("zip");
				Record record = new Record(name, vehicle, id, amount, vin, is_suspended, is_renewed, can_renew, 
						expiration_date, street_address, city, state, zip);
								
				Cache.getInstance().searchResults.add(record);
			}
		}
		
		Cache.getInstance().adapter.notifyDataSetChanged();
	}

	public String convertStreamToString(InputStream is) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	
}
