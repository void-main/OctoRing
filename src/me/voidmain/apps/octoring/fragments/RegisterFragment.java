package me.voidmain.apps.octoring.fragments;

import java.util.Locale;

import me.voidmain.apps.octoring.R;
import me.voidmain.apps.octoring.utils.CommonUtilities;
import me.voidmain.apps.octoring.utils.FragmentTransactionUtilities;
import me.voidmain.apps.octoring.utils.PrefsUtilities;
import me.voidmain.apps.octoring.utils.ServerUtilities;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;

public class RegisterFragment extends Fragment {

	private EditText mEtGithubLogin;
	private Spinner mSpLoginType;
	private Button mBtnRegister;

	AsyncTask<Void, Void, Void> mRegisterTask;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_register, container, false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
	}

	@Override
	public void onPause() {
		super.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();

		setupView();
	}

	private void setupView() {
		mEtGithubLogin = (EditText) getView()
				.findViewById(R.id.et_github_login);
		mSpLoginType = (Spinner) getView().findViewById(R.id.sp_login_type);
		mBtnRegister = (Button) getView().findViewById(R.id.btn_register);

		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.login_type,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSpLoginType.setAdapter(adapter);
		mSpLoginType.setSelection(0);

		mBtnRegister.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				InputMethodManager inputManager = (InputMethodManager) getActivity()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.hideSoftInputFromWindow(getActivity()
						.getCurrentFocus().getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);

				Toast.makeText(getActivity(), "Registering...",
						Toast.LENGTH_LONG).show();

				String login = mEtGithubLogin.getText().toString();
				String type = mSpLoginType.getSelectedItem().toString()
						.toLowerCase(Locale.getDefault())
						+ "s";
				String path = type + "/" + login;
				PrefsUtilities.setPrefsString(getActivity(),
						R.string.prefs_key_path, path);

				startRegistration();
			}
		});
	}

	private void startRegistration() {
		final String regId = GCMRegistrar.getRegistrationId(getActivity());
		if (regId.equals("")) {
			GCMRegistrar.register(getActivity(), CommonUtilities.SENDER_ID);
		} else {
			if (GCMRegistrar.isRegisteredOnServer(getActivity())) {
				FragmentTransactionUtilities.transTo(getActivity(), new CountdownFragment(), "CountdownFragment", true);
			} else {
				setupAsyncTasks(getActivity(), regId,
						PrefsUtilities.getPrefsString(getActivity(),
								R.string.prefs_key_path));
				mRegisterTask.execute(null, null, null);
			}
		}
	}

	private void setupAsyncTasks(final Context context, final String regId,
			final String path) {
		mRegisterTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				boolean registered = ServerUtilities.register(context, regId,
						path);
				if (!registered) {
					GCMRegistrar.unregister(context);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				mRegisterTask = null;
			}

		};
	}

}
