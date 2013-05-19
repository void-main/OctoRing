package me.voidmain.apps.octoring;

import me.voidmain.apps.octoring.utils.CommonUtilities;
import me.voidmain.apps.octoring.utils.PrefsUtilities;
import me.voidmain.apps.octoring.utils.ServerUtilities;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;

/**
 * IntentService responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(CommonUtilities.SENDER_ID);
	}

	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		ServerUtilities
				.register(context, registrationId, PrefsUtilities
						.getPrefsString(context, R.string.prefs_key_path));
		CommonUtilities.sendMessage(context,
				CommonUtilities.MESSAGE_TYPE_SERVER_REGISTERED, "");
	}

	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		if (GCMRegistrar.isRegisteredOnServer(context)) {
			ServerUtilities.unregister(context, registrationId);
		} else {
			// This callback results from the call to unregister made on
			// ServerUtilities when the registration to the server failed.
			Log.i(TAG, "Ignoring unregister callback");
		}
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.i(TAG, "Received message");
		String message = getString(R.string.gcm_message);
		CommonUtilities.sendMessage(context, CommonUtilities.MESSAGE_TYPE_GOT_MESSAGE, message);
	}

	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		// generateNotification(context, message);
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		CommonUtilities.sendMessage(context,
				CommonUtilities.MESSAGE_TYPE_GCM_ERROR,
				getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		CommonUtilities.sendMessage(context,
				CommonUtilities.MESSAGE_TYPE_GCM_RECOVERABLE_ERROR,
				getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

}
