/**
 * This file was auto-generated by the Titanium Module SDK helper for Android
 * Appcelerator Titanium Mobile
 * Copyright (c) 2009-2013 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the Apache Public License
 * Please see the LICENSE included with this distribution for details.
 *
 */
package com.alexmany.gcm;

import static com.alexmany.gcm.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.alexmany.gcm.CommonUtilities.EXTRA_MESSAGE;
import static com.alexmany.gcm.CommonUtilities.SENDER_ID;

import org.appcelerator.kroll.KrollModule;
import org.appcelerator.kroll.annotations.Kroll;
import org.appcelerator.kroll.common.Log;
import org.appcelerator.titanium.TiApplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;

import com.google.android.gcm.GCMRegistrar;

@Kroll.module(name = "Gcm", id = "com.alexmany.gcm")
public class GcmModule extends KrollModule {

	static AsyncTask<Void, Void, Void> mRegisterTask;
	// Standard Debugging variables
	private static final String TAG = "GcmModule";

	// You can define constants with @Kroll.constant, for example:
	// @Kroll.constant public static final String EXTERNAL_NAME = value;

	public GcmModule() {
		super();
	}

	@Kroll.onAppCreate
	public static void onAppCreate(TiApplication app) {
		Log.d(TAG, "inside onAppCreate");
		GCMRegistrar.checkDevice(app);
		Log.d(TAG, "inside Registre");
		GCMRegistrar.checkManifest(app);
		Log.d(TAG, "inside mmmm");

		app.registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));
		final String regId = GCMRegistrar.getRegistrationId(app);
		Log.d(TAG, "inside mmmm" + regId);
		if (regId.equals("")) {
			// Automatically registers application on startup.
			GCMRegistrar.register(app, SENDER_ID);
			Log.d(TAG, "inside mmmm  register");
		} else {
			// Device is already registered on GCM, check server.
			if (GCMRegistrar.isRegisteredOnServer(app)) {

			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = app;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						ServerUtilities.register(context, regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}

	// Methods
	@Kroll.method
	public String example() {
		Log.d(TAG, "example called");
		return "hello world";
	}

	// Properties
	@Kroll.getProperty
	public String getExampleProp() {
		Log.d(TAG, "get example property");
		return "hello world";
	}

	private final static BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
		}
	};

	@Kroll.setProperty
	public void setExampleProp(String value) {
		Log.d(TAG, "set example property: " + value);
	}

}
