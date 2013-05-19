package me.voidmain.apps.octoring.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import me.voidmain.apps.octoring.R;
import android.content.Context;
import android.util.Log;

import com.google.android.gcm.GCMRegistrar;

/**
 * Helper class used to communicate with the demo server.
 */
public final class ServerUtilities {

	private static final int MAX_ATTEMPTS = 5;
	private static final int BACKOFF_MILLI_SECONDS = 2000;
	private static final Random random = new Random();

	/**
	 * Register this account/device pair within the server.
	 * 
	 * @return whether the registration succeeded or not.
	 */
	public static boolean register(final Context context, final String regId,
			final String path) {
		Log.i(CommonUtilities.TAG, "registering device (regId = " + regId + ")");
		String serverUrl = CommonUtilities.SERVER_URL + "/register";
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		params.put("path", path);
		long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
		for (int i = 1; i <= MAX_ATTEMPTS; i++) {
			Log.d(CommonUtilities.TAG, "Attempt #" + i + " to register");
			try {
				NetworkUtilities.post(serverUrl, params);
				GCMRegistrar.setRegisteredOnServer(context, true);
				return true;
			} catch (IOException e) {
				Log.e(CommonUtilities.TAG,
						"Failed to register on attempt " + i, e);
				if (i == MAX_ATTEMPTS) {
					break;
				}
				try {
					Log.d(CommonUtilities.TAG, "Sleeping for " + backoff
							+ " ms before retry");
					Thread.sleep(backoff);
				} catch (InterruptedException e1) {
					// Activity finished before we complete - exit.
					Log.d(CommonUtilities.TAG,
							"Thread interrupted: abort remaining retries!");
					Thread.currentThread().interrupt();
					return false;
				}
				// increase backoff exponentially
				backoff *= 2;
			}
		}
		String message = context.getString(R.string.server_register_error,
				MAX_ATTEMPTS);
		CommonUtilities.sendMessage(context, CommonUtilities.MESSAGE_TYPE_SERVER_REGISTER_ERROR, message);
		return false;
	}

	/**
	 * Unregister this account/device pair within the server.
	 */
	public static void unregister(final Context context, final String regId) {
		Log.i(CommonUtilities.TAG, "unregistering device (regId = " + regId
				+ ")");
		String serverUrl = CommonUtilities.SERVER_URL + "/unregister";
		Map<String, String> params = new HashMap<String, String>();
		params.put("regId", regId);
		try {
			NetworkUtilities.post(serverUrl, params);
			GCMRegistrar.setRegisteredOnServer(context, false);
		} catch (IOException e) {
			String message = context.getString(
					R.string.server_unregister_error, e.getMessage());
			CommonUtilities.sendMessage(context, CommonUtilities.MESSAGE_TYPE_SERVER_UNREGISTER_ERROR, message);
		}
	}
}
