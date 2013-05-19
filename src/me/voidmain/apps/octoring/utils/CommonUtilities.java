package me.voidmain.apps.octoring.utils;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

	/**
	 * Base URL
	 */
	public static final String SERVER_URL = "http://octocaddice.herokuapp.com";

	/**
	 * Google API project id registered to use GCM.
	 */
	public static final String SENDER_ID = "346184247837";

	/**
	 * Tag used on log messages.
	 */
	public static final String TAG = "OctoRing";

	/**
	 * Intent used to display a message in the screen.
	 */
	public static final String UPDATE_PUSH_STATUS_ACTION = "me.voidmain.apps.octoring.action.UPDATE_PUSH_STATUS";

	/**
	 * Intent's extra that contains the message type
	 */
	public static final String EXTRA_MESSAGE_TYPE = "message_type";

	/**
	 * Intent's extra that contains the message
	 */
	public static final String EXTRA_MESSAGE = "message";

	public static final int MESSAGE_TYPE_SERVER_REGISTERED = 1001;
	public static final int MESSAGE_TYPE_SERVER_REGISTER_ERROR = 1002;
	public static final int MESSAGE_TYPE_SERVER_UNREGISTER_ERROR = 1003;
	public static final int MESSAGE_TYPE_GCM_ERROR = 2001;
	public static final int MESSAGE_TYPE_GCM_RECOVERABLE_ERROR = 2002;
	public static final int MESSAGE_TYPE_GOT_MESSAGE = 3001;

	/**
	 * Notifies UI to display a message.
	 * <p>
	 * This method is defined in the common helper because it's used both by the
	 * UI and the background service.
	 * 
	 * @param context
	 *            application's context.
	 * @param message
	 *            message to be displayed.
	 */
	public static void sendMessage(Context context, int msgType, String message) {
		Intent intent = new Intent(UPDATE_PUSH_STATUS_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		intent.putExtra(EXTRA_MESSAGE_TYPE, msgType);
		context.sendBroadcast(intent);
	}
}
