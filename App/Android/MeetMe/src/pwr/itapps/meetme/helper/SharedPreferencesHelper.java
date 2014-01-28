package pwr.itapps.meetme.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class SharedPreferencesHelper {

	private static final String PREFERANCES_NAME = "meetMe_preferences";

	public static final String REMEMBER_USER = "rememberUser";
	public static final boolean REMEMBER_USER_DEF = false;
	public static final String SYNCHRONIZE_USERS = "synchronizeUsers";
	public static final boolean SYNCHRONIZE_USERS_DEF = true;
	public static final String EVENTS_SYNCH = "eventSynch";
	public static final long EVENTS_SYNCH_DEF = 0l;
	private static SharedPreferencesHelper instance;

	private SharedPreferencesHelper() {
	}

	public static SharedPreferencesHelper getInstance() {

		if (instance == null) {

			synchronized (SharedPreferencesHelper.class) {

				if (instance == null) {
					instance = new SharedPreferencesHelper();
				}
			}
		}
		return instance;
	}

	public void putBoolean(String key, Boolean value, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putBoolean(key, value);
		edit.commit();
	}

	public Boolean getBoolean(String key, Context context) {
		return getBoolean(key, false, context);
	}

	public Boolean getBoolean(String key, Boolean def, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		return prefs.getBoolean(key, def);
	}

	public void putLong(String key, long value, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putLong(key, value);
		edit.commit();
	}

	public long getLong(String key, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		return prefs.getLong(key, 0);
	}

	public long getLong(String key, long defValue, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		return prefs.getLong(key, defValue);
	}

	public void putInt(String key, int value, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putInt(key, value);
		edit.commit();
	}

	public int getInt(String key, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		return prefs.getInt(key, 0);
	}

	public int getInt(String key, int defValue, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		return prefs.getInt(key, defValue);
	}

	public void putString(String key, String value, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		SharedPreferences.Editor edit = prefs.edit();
		edit.putString(key, value);
		edit.commit();
	}

	public String getString(String key, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		return prefs.getString(key, "");
	}

	public String getString(String key, String defValue, Context context) {
		SharedPreferences prefs = context.getSharedPreferences(
				PREFERANCES_NAME, 0);
		return prefs.getString(key, defValue);
	}

}