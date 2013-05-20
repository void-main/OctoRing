package me.voidmain.apps.octoring;

import me.voidmain.apps.octoring.utils.PrefsUtilities;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class PushReminderReceiver extends BroadcastReceiver {

	public static final int REQUEST_SETUP_NOTIFICATION = 3000;
	public static final int PUSH_NOTIFICATION_ID = 4000;
	public static final int CONGRATULATION_NOTIFICATON_ID = 4001;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("TAG", "PushReminder received");
		PrefsUtilities.setPrefsBoolean(context, R.string.prefs_has_update_today, false);
		startOnGoingNotification(context);
	}

	public static void startOnGoingNotification(final Context context) {
		// do not start notification 
		if(PrefsUtilities.getPrefsBoolean(context, R.string.prefs_has_update_today)) return; 
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(R.drawable.spinner_inner)
				.setContentTitle(
						context.getString(R.string.notification_content_title))
				.setContentText(
						context.getString(R.string.notification_content_text))
				.setOngoing(true);
		Intent resultIntent = new Intent(context, MainActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notif = mBuilder.build();
		notif.defaults |= Notification.DEFAULT_SOUND;
		notif.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(PUSH_NOTIFICATION_ID, notif);
	}

	public static void stopOnGoingNotification(final Context context) {
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.cancel(PUSH_NOTIFICATION_ID);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context)
				.setSmallIcon(R.drawable.spinner_inner)
				.setContentTitle(
						context.getString(R.string.notification_congra_content_title))
				.setContentText(
						context.getString(R.string.notification_congra_content_text));
		Intent resultIntent = new Intent(context, MainActivity.class);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		Notification notif = mBuilder.build();
		notif.defaults |= Notification.DEFAULT_SOUND;
		notif.defaults |= Notification.DEFAULT_VIBRATE;
		notificationManager.notify(CONGRATULATION_NOTIFICATON_ID, notif);
	}

}
