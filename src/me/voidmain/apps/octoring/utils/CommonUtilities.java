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
    public static final String UPDATE_PUSH_STATUS_ACTION =
            "me.voidmain.apps.octoring.action.UPDATE_PUSH_STATUS";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    public static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(UPDATE_PUSH_STATUS_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
