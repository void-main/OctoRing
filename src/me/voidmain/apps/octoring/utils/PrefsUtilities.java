package me.voidmain.apps.octoring.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PrefsUtilities {
	
	public static void setPrefsString(final Context context, final String key, final String value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	public static String getPrefsString(final Context context, final String key) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getString(key, "");
	}
	
	public static void setPrefsBoolean(final Context context, final String key, final boolean value) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		Editor editor = prefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	
	public static boolean getPrefsBoolean(final Context context, final String key) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		return prefs.getBoolean(key, false);
	}
	
	
	public static void setPrefsString(final Context context, final int keyResId, final String value) {
		setPrefsString(context, context.getString(keyResId), value);
	}
	
	public static String getPrefsString(final Context context, final int keyResId) {
		return getPrefsString(context, context.getString(keyResId));
	}
	
	public static void setPrefsBoolean(final Context context, final int keyResId, final boolean value) {
		setPrefsBoolean(context, context.getString(keyResId), value);
	}
	
	public static boolean getPrefsBoolean(final Context context, final int keyResId) {
		return getPrefsBoolean(context, context.getString(keyResId));
	}
	
}
