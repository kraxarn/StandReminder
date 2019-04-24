package com.crow.stand_reminder;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.preference.PreferenceDataStore;
import androidx.preference.PreferenceManager;

import java.util.Set;

public class AppPreferences extends PreferenceDataStore
{
	private SharedPreferences preferences;

	private SharedPreferences.Editor editor;

	// region General

	public enum Theme
	{
		Auto,
		Day,
		Night,
		Midnight
	}

	public Theme getTheme()
	{
		return Enum.valueOf(Theme.class, preferences.getString("general_theme", "auto"));
	}

	/**
	 * How often to refresh in minutes
	 */
	public int getBatteryFrequency()
	{
		return preferences.getInt("battery_frequency", 2);
	}

	// endregion

	// region Wear

	/**
	 * Node ID of the Wear device to use, null if none
	 */
	public String getWearDevice()
	{
		return getString("wear_device", null);
	}

	/**
	 * How many times the main device has to fail before checking Wear
	 */
	public int getWearUpdate()
	{
		return getInt("wear_update", 2);
	}

	/**
	 * If the main device should always be skipped and only use Wear
	 */
	public boolean getWearUseOnly()
	{
		return getBoolean("wear_use_only", false);
	}

	// endregion

	// region Goal

	/**
	 * @return Number of hours as goal for each day
	 */
	public int getGoalHours()
	{
		return getInt("goal_hour", 12);
	}

	/**
	 * If it should keep reminding after the goal has been reached
	 */
	public boolean getGoalRemindAfter()
	{
		return getBoolean("goal_remind_after", false);
	}

	/**
	 * How long each interval for reminders is
	 * (very likely to be removed in the near future)
	 */
	@Deprecated
	public int getGoalInterval()
	{
		return getInt("goal_interval", 60);
	}

	// endregion

	// region Notifications

	/**
	 * At what minute it should remind for the first time
	 */
	public int getNotificationRemind()
	{
		return getInt("notification_remind", 30);
	}

	/**
	 * At what minute it should remind for the second time
	 */
	public int getSecondNotificationRemind()
	{
		return getInt("notification_second_remind", 50);
	}

	// endregion

	// region Sensor

	/**
	 * When the sensor value counts as standing up, 0-100 (0-10 with sensor)
	 */
	public int getSensorSensitivity()
	{
		return getInt("sensor_sensitivity", 75);
	}

	/**
	 * If we should use the gravity sensor instead of the accelerometer
	 */
	public boolean getSensorUseGravity()
	{
		return getBoolean("sensor_use_gravity", false);
	}

	// endregion

	private SharedPreferences.Editor getEditor()
	{
		if (editor == null)
			editor = preferences.edit();

		return editor;
	}

	private void save()
	{
		if (editor != null)
			editor.apply();
	}

	public AppPreferences(Context context)
	{
		preferences = PreferenceManager.getDefaultSharedPreferences(context);
	}

	//region put overrides

	@Override
	public void putString(String key, @Nullable String value)
	{
		getEditor().putString(key, value);
	}

	@Override
	public void putStringSet(String key, @Nullable Set<String> values)
	{
		getEditor().putStringSet(key, values);
	}

	@Override
	public void putInt(String key, int value)
	{
		getEditor().putInt(key, value);
	}

	@Override
	public void putLong(String key, long value)
	{
		getEditor().putLong(key, value);
	}

	@Override
	public void putFloat(String key, float value)
	{
		getEditor().putFloat(key, value);
	}

	@Override
	public void putBoolean(String key, boolean value)
	{
		getEditor().putBoolean(key, value);
	}

	//endregion

	//region get overrides

	@Nullable
	@Override
	public String getString(String key, @Nullable String defValue)
	{
		return preferences.getString(key, defValue);
	}

	@Nullable
	@Override
	public Set<String> getStringSet(String key, @Nullable Set<String> defValues)
	{
		return preferences.getStringSet(key, defValues);
	}

	@Override
	public int getInt(String key, int defValue)
	{
		return preferences.getInt(key, defValue);
	}

	@Override
	public long getLong(String key, long defValue)
	{
		return preferences.getLong(key, defValue);
	}

	@Override
	public float getFloat(String key, float defValue)
	{
		return preferences.getFloat(key, defValue);
	}

	@Override
	public boolean getBoolean(String key, boolean defValue)
	{
		return preferences.getBoolean(key, defValue);
	}

	//endregion
}