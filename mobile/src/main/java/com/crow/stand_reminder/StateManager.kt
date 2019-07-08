package com.crow.stand_reminder

import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.crow.stand_reminder.data.CompletedHour
import com.crow.stand_reminder.data.SensorValue
import com.crow.stand_reminder.data.ValueSource
import com.crow.stand_reminder.tool.DatabaseTools
import com.crow.stand_reminder.tool.NotificationTools
import java.util.*

object StateManager
{
	enum class CheckDelay
	{
		/**
		 * Something unexpected happened
		 */
		INVALID,
		/**
		 * Next hour
		 */
		NONE,
		/**
		 * 30 seconds
		 */
		HALF,
		/**
		 * Next check time
		 */
		FULL
	}

	/**
	 * Temporary list of values that gets cleared
	 * after every finished hour
	 * TODO: This could possibly just be a list of values
	 * TODO: Keep track of what hour this is and delete if old
	 */
	val values: MutableList<SensorValue> = mutableListOf()

	/**
	 * Does stuff and returns when the next
	 * check should take place.
	 */
	fun update(value: Float, source: ValueSource, db: DatabaseTools,
			   preferences: AppPreferences, context: Context): CheckDelay
	{
		// Save value to temporary list
		values += SensorValue(value, source)

		// Check if current hour is already completed
		// (Due to how we schedule next check, this should
		// never happen, but who knows)
		if (db.isCompleted(Calendar.getInstance()))
		{
			// We don't need to add anything to the
			// database since it's already there
			values.clear()
			return CheckDelay.NONE
		}

		// TODO: This assumes we're checking once per minute
		// TODO: This ignores the watch

		// Check how many standing values we have in the temporary list
		return when (values.count { v -> isStanding(v.value, preferences.sensorSensitivity) })
		{
			// None yet, see if it's remind time, if it next, check again in 30 seconds
			0 -> if (isRemindTime(preferences)) {
				// TODO: Send reminder to user
				// TODO: Notification should only be sent once
				// 1010 is the persistent notification
				NotificationTools.showSimple(context, 1015, "reminders",
					R.string.reminder_title, R.string.reminder_text)
				CheckDelay.HALF
			} else {
				// Not standing, but not remind time, ignore
				CheckDelay.FULL
			}
			// We're standing now, check again in 30 seconds
			1 -> CheckDelay.HALF
			// We finished, show notification and check again next hour
			2 -> {
				// Save hour to database, clear temp variables and return NONE
				db.addCompleted(CompletedHour(Calendar.getInstance(), source))
				values.clear()
				CheckDelay.NONE
			}
			// Should never get here and could probably
			// be included in one of the above
			else -> {
				NotificationTools.showSimple(context, 1020, "general",
					"Oh no!", "Unexpected values found, this is a bug!")
				CheckDelay.INVALID
			}
		}

		// TODO: This shouldn't be here
		/*
		if (values.count { v -> isStanding(v.value, preferences.sensorSensitivity) } >= 2)
		{
			// Hour is already completed, schedule for next hour
			values.clear()
			return CheckDelay.NONE
		}
		 */
	}

	/**
	 * Checks if value is smaller than sensitivity or larger than -sensitivity
	 */
	private fun isStanding(value: Float, sensitivity: Int): Boolean
	{
		val threshold = sensitivity / 10f
		return value > threshold || value < -threshold
	}

	private fun isRemindTime(preferences: AppPreferences): Boolean
	{
		val minute = Calendar.getInstance().get(Calendar.MINUTE)
		return minute == preferences.notificationRemind
			|| minute == preferences.secondNotificationRemind
	}
}