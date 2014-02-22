package com.alexmany.gcm;

import android.content.Context;

public class AppStoreBroadCastReciever extends
		com.google.android.gcm.GCMBroadcastReceiver {

	@Override
	protected String getGCMIntentServiceClassName(Context context) {
		return "com.alexmany.gcm.GCMIntentService";
	}
}