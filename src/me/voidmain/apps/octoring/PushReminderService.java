package me.voidmain.apps.octoring;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

public class PushReminderService extends Service {

	public static final int REQUEST_SETUP_NOTIFICATION = 3000;
	public static final int PUSH_NOTIFICATION_ID = 4000;
	public static final int CONGRATULATION_NOTIFICATON_IO = 4001;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startOnGoingNotification(this);
		return super.onStartCommand(intent, flags, startId);
	}

	public static void startOnGoingNotification(final Context context) {
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
		notificationManager.notify(PUSH_NOTIFICATION_ID, mBuilder.build());
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
		notificationManager.notify(PUSH_NOTIFICATION_ID, mBuilder.build());
	}

}
