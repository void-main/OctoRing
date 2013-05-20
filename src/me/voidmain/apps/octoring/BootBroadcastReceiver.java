package me.voidmain.apps.octoring;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootBroadcastReceiver extends BroadcastReceiver {
	private static final String TAG = BootBroadcastReceiver.class.getCanonicalName();

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.e(TAG, "Setting up alarm...");
		GCMIntentService.startAlarmService(context);
		Log.e(TAG, "done!");
	}

}
