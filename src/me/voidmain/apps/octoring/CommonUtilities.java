package me.voidmain.apps.octoring;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {

    /**
     * Base URL
     */
    static final String SERVER_URL = "http://octocaddice.herokuapp.com";

    /**
     * Google API project id registered to use GCM.
     */
    static final String SENDER_ID = "346184247837";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "OctoRing";

    /**
     * Intent used to display a message in the screen.
     */
    static final String UPDATE_PUSH_STATUS_ACTION =
            "me.voidmain.apps.octoring.action.UPDATE_PUSH_STATUS";

    /**
     * Intent's extra that contains the message to be displayed.
     */
    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(UPDATE_PUSH_STATUS_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}