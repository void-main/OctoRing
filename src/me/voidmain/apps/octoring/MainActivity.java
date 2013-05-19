package me.voidmain.apps.octoring;

import me.voidmain.apps.octoring.fragments.CountdownFragment;
import me.voidmain.apps.octoring.fragments.RegisterFragment;
import me.voidmain.apps.octoring.utils.CommonUtilities;
import me.voidmain.apps.octoring.utils.FragmentTransactionUtilities;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class MainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				CommonUtilities.UPDATE_PUSH_STATUS_ACTION));

		if (GCMRegistrar.isRegistered(this)) {
			FragmentTransactionUtilities.transTo(this, new CountdownFragment(),
					"CountdownFragment", true);
		} else { // Needs registration
			FragmentTransactionUtilities.transTo(this, new RegisterFragment(),
					"RegisterFragment", true);
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mHandleMessageReceiver);
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(
					CommonUtilities.EXTRA_MESSAGE);
			int messageType = intent.getExtras().getInt(
					CommonUtilities.EXTRA_MESSAGE_TYPE);

			if (messageType == CommonUtilities.MESSAGE_TYPE_SERVER_REGISTERED) {
				FragmentTransactionUtilities.transTo(MainActivity.this,
						new CountdownFragment(), "CountdownFragment", true);
			} else if(messageType == CommonUtilities.MESSAGE_TYPE_GOT_MESSAGE) {
				PushReminderService.stopOnGoingNotification(MainActivity.this);
			}

			if (!TextUtils.isEmpty(newMessage)) {
				Toast.makeText(MainActivity.this, newMessage, Toast.LENGTH_LONG)
						.show();
			}
		}
	};

}
