package com.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.bugsense.trace.BugSenseHandler;
import com.core.CacheManager;
import com.core.Time2FlyApp;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;

public class Splash extends Activity implements OnClickListener {
	Context mContext = this;
	Time2FlyApp appInstance;
	ProgressDialog fbLoginDialog = null;
	LinearLayout fbLogin;
	CacheManager cache = CacheManager.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BugSenseHandler.initAndStartSession(mContext, "c417ebfa");
		appInstance = (Time2FlyApp) getApplication();

		switch (getWindowManager().getDefaultDisplay().getOrientation()) {
		case Configuration.ORIENTATION_LANDSCAPE:
			setContentView(R.layout.activity_splash2);
			break;
		default:
			setContentView(R.layout.activity_splash);
			break;
		}

		initFacebook(savedInstanceState);
		fbLogin = (LinearLayout) findViewById(R.id.fb_login);
		fbLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.fb_login:
			onClickLogin();
			break;
		default:
			break;
		}

	}

	private void initFacebook(Bundle savedInstanceState) {
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		Session session = Session.getActiveSession();
		if (session == null) {
			if (savedInstanceState != null) {
				session = Session.restoreSession(this, null, statusCallback,
						savedInstanceState);
			}
			if (session == null) {
				session = new Session(this);
			}

			Session.setActiveSession(session);
			if (session.getState().equals(SessionState.CREATED_TOKEN_LOADED)) {
				List<String> permissions = new ArrayList<String>();
				permissions.add("email");

				OpenRequest openRequest = new Session.OpenRequest(this);
				openRequest.setCallback(statusCallback);
				openRequest.setPermissions(permissions);

				openRequest.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
				session.openForRead(openRequest);

			}
		}
	}

	private void onClickLogin() {
		Session session = Session.getActiveSession();

		if (!session.isOpened() && !session.isClosed()) {

			List<String> permissions = new ArrayList<String>();
			permissions.add("email");
			OpenRequest openRequest = new Session.OpenRequest(this);
			openRequest.setCallback(statusCallback);
			openRequest.setPermissions(permissions);
			openRequest.setLoginBehavior(SessionLoginBehavior.SUPPRESS_SSO);
			session.openForRead(openRequest);

		} else {
			Session.openActiveSession(this, true, statusCallback);
		}
	}

	Session.StatusCallback statusCallback = new Session.StatusCallback() {

		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {

				fbLoginDialog = new ProgressDialog(mContext);
				fbLoginDialog.setTitle("Time2Fly");
				fbLoginDialog.setIcon(R.drawable.compass_mini);
				fbLoginDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				fbLoginDialog.setCancelable(false);
				fbLoginDialog.setMessage("Authenticating Facebook");
				fbLoginDialog.show();

				Request.executeMeRequestAsync(session,
						new Request.GraphUserCallback() {

							public void onCompleted(GraphUser user,
									Response response) {

								try {
									fbLoginDialog.dismiss();
									fbLoginDialog = null;
								} catch (Exception e) {

								}
								if (response != null) {
									// do something with <response> now
									try {
										String userID = user.getId();
										String name = user.getName();
										String profileURL = user.getLink();
										String uName = user.getUsername();
										if (uName == null
												|| uName.equalsIgnoreCase(""))
											uName = userID;

										String provider = "facebook";
										String email = (String) user
												.getProperty("email");
										JSONObject obj = user
												.getInnerJSONObject();

										// LoginTask task = new LoginTask();
										// task.execute();

										Intent homeIntent;
										homeIntent = new Intent(mContext,
												Home.class);
										startActivity(homeIntent);
										finish();

									} catch (Exception e) {
										e.printStackTrace();
										Log.d("social_login", "Exception e");

									}
								} else {
									Log.d("social_login", "response null");

								}
							}
						});
			}
		}
	};

	public void onStart() {
		super.onStart();
		Session.getActiveSession().addCallback(statusCallback);
	}

	@Override
	public void onStop() {
		super.onStop();
		Session.getActiveSession().removeCallback(statusCallback);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode,
				resultCode, data);

	}

}
