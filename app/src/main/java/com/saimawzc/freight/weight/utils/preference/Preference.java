package com.saimawzc.freight.weight.utils.preference;

import android.content.Context;
import android.content.SharedPreferences;

public class Preference {

	private final String SHARED_PREFERENCE_NAME = "com.library.happycard";
	private static Preference catche;
	private SharedPreferences spf;

	public static Preference instance(Context context) {
		if (catche == null) {
			catche = new Preference(context);
		}
		return catche;
	}

	public static Preference getInstance(){
		return catche;
	}

	public Preference(Context context) {
		spf = context.getSharedPreferences(SHARED_PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	public void putBoolean(String key, boolean value) {
		spf.edit().putBoolean(key, value).commit();
	}

	public boolean getBoolean(String key) {
		return spf.getBoolean(key, false);
	}

	public boolean getBoolean(String key, boolean defa) {
		return spf.getBoolean(key, defa);
	}

	public void putString(String key, String value) {
		spf.edit().putString(key, value).commit();
	}

	public String getString(String key) {
		return spf.getString(key, "");
	}

	public String getString(String key, String defau) {
		return spf.getString(key, defau);
	}

	public void putInt(String key, int value) {
		spf.edit().putInt(key, value).commit();
	}

	public void putLong(String key, long value) {
		spf.edit().putLong(key, value).commit();
	}

	public int getInt(String key) {
		return spf.getInt(key, 0);
	}

	public int getInt(String key, int defaultValue) {
		return spf.getInt(key, defaultValue);
	}

	public long getLong(String key) {
		return spf.getLong(key, 0);
	}

	public long getLong(String key, long def) {
		return spf.getLong(key, def);
	}

	public void clearData() {
		spf.edit().clear().commit();
	}

	public void remove(String key) {
		spf.edit().remove(key).commit();
	}

	public void commit() {
		spf.edit().commit();
	}

}
