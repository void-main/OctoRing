package me.voidmain.apps.octoring;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private static final int REQUEST_UPDATE_GOOGLE_PLAY_SERVICE_LIB = 1001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		int errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if(errorCode != ConnectionResult.SUCCESS) {
			GooglePlayServicesUtil.getErrorDialog(errorCode, this, REQUEST_UPDATE_GOOGLE_PLAY_SERVICE_LIB);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == REQUEST_UPDATE_GOOGLE_PLAY_SERVICE_LIB) {
			if(resultCode == MainActivity.RESULT_OK) {
				Toast.makeText(this, "Installation succeeded!", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(this, "Installation failed!", Toast.LENGTH_LONG).show();
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
