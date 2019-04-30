package com.crow.stand_reminder

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceDataStore
import androidx.preference.PreferenceManager

class AppPreferences(context: Context) : PreferenceDataStore()
{
	private val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

	private var editor: SharedPreferences.Editor? = null

	enum class Theme
	{
		Auto,
		Day,
		Night,
		Midnight
	}

	// region General

	val theme: Theme
		get() = Theme.valueOf(preferences.getString("general_theme", null) ?: "auto")

	/**
	 * How often to refresh in minutes
	 */
	val batteryFrequency: Int
		get() = preferences.getInt("battery_frequency", 2)

	// endregion

	// region Wear

	/**
	 * Node ID of the Wear device to use, null if none
	 */
	val wearDevice: String?
		get() = getString("wear_device", null)

	/**
	 * How many times the main device has to fail before checking Wear
	 */
	val wearUpdate: Int
		get() = getInt("wear_update", 2)

	/**
	 * If the main device should always be skipped and only use Wear
	 */
	val wearUseOnly: Boolean
		get() = getBoolean("wear_use_only", false)

	// endregion

	// region Goal

	/**
	 * @return Number of hours as goal for each day
	 */
	val goalHours: Int
		get() = Integer.parseInt(getString("goal_hour", "12")!!)

	/**
	 * If it should keep reminding after the goal has been reached
	 */
	val goalRemindAfter: Boolean
		get() = getBoolean("goal_remind_after", false)

	// endregion

	// region Notifications

	/**
	 * At what minute it should remind for the first time
	 */
	val notificationRemind: Int
		get() = getInt("notification_remind", 30)

	/**
	 * At what minute it should remind for the second time
	 */
	val secondNotificationRemind: Int
		get() = getInt("notification_second_remind", 50)

	// endregion

	// region Sensor

	/**
	 * When the sensor value counts as standing up, 0-100 (0-10 with sensor)
	 */
	val sensorSensitivity: Int
		get() = getInt("sensor_sensitivity", 75)

	/**
	 * If we should use the gravity sensor instead of the accelerometer
	 */
	val sensorUseGravity: Boolean
		get() = getBoolean("sensor_use_gravity", false)

	// endregion

	private fun getEditor(): SharedPreferences.Editor
	{
		if (editor == null)
			editor = preferences.edit()

		return editor!!
	}

	private fun save()
	{
		if (editor != null)
			editor!!.apply()
	}

	//region put overrides

	override fun putString(key: String?, value: String?) {
		getEditor().putString(key, value).apply()
	}

	override fun putStringSet(key: String?, values: Set<String>?) {
		getEditor().putStringSet(key, values).apply()
	}

	override fun putInt(key: String?, value: Int) {
		getEditor().putInt(key, value).apply()
	}

	override fun putLong(key: String?, value: Long) {
		getEditor().putLong(key, value).apply()
	}

	override fun putFloat(key: String?, value: Float) {
		getEditor().putFloat(key, value).apply()
	}

	override fun putBoolean(key: String?, value: Boolean) {
		getEditor().putBoolean(key, value).apply()
	}

	//endregion

	//region get overrides

	override fun getString(key: String?, defValue: String?): String? {
		return preferences.getString(key, defValue)
	}

	override fun getStringSet(key: String?, defValues: Set<String>?): Set<String>? {
		return preferences.getStringSet(key, defValues)
	}

	override fun getInt(key: String?, defValue: Int): Int {
		return preferences.getInt(key, defValue)
	}

	override fun getLong(key: String?, defValue: Long): Long {
		return preferences.getLong(key, defValue)
	}

	override fun getFloat(key: String?, defValue: Float): Float {
		return preferences.getFloat(key, defValue)
	}

	override fun getBoolean(key: String?, defValue: Boolean): Boolean {
		return preferences.getBoolean(key, defValue)
	}

	//endregion
}