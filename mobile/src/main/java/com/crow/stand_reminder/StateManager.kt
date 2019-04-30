package com.crow.stand_reminder

import android.content.Context
import com.crow.stand_reminder.data.SensorValue
import com.crow.stand_reminder.tool.DatabaseTools
import java.util.*

object StateManager
{
	enum class State
	{
		UNKNOWN,
		SITTING,
		STANDING,
		DONE
	}

	var state         = State.UNKNOWN
	var previousState = State.UNKNOWN

	enum class StateResponse
	{
		// Ignore
		NONE,
		// Remind the user to stand up
		REMIND,
		// This hour completed
		OK
	}

	fun update(context: Context, value: Float): StateResponse
	{
		/*
		 * 1.	Get if we're standing or not
		 * 2.	Register value to database
		 * 		(assumes it doesn't go here again after goal reached for hour)
		 * 3a.	If sitting and not remind time, ignore
		 * 3b.	If sitting and remind time, remind
		 * 3c.	If standing, but not previous check, save to database
		 * 3d.	If standing and previous check, save to database and set next check for :00
		 */

		// TODO: This currently ignores the watch

		// Preferences to use later
		val preferences = AppPreferences(context)

		// Get new state and update database
		state = getState(context, value)

		// Get response depending on state
		val response: StateResponse = when (state)
		{
			// User is sitting and time to remind (first or second time)
			State.SITTING ->
				if (isRemindTime(preferences.notificationRemind) || isRemindTime(preferences.secondNotificationRemind))
					StateResponse.REMIND
				else
					StateResponse.NONE
			// User is standing up and previous minute
			State.STANDING ->
				if (previousState == State.STANDING)
					StateResponse.OK
				else
					StateResponse.NONE
			// Anything else, ignore
			else -> StateResponse.NONE
		}

		// Update previous state before returning
		previousState = state

		return response
	}

	private fun isRemindTime(remindTime: Int): Boolean
	{
		/*
		 * TODO:
		 * This sort of assumes that it will only
		 * check this once every minute, which it
		 * probably is unless we specified something
		 * else in the settings, so that should also
		 * be checked. Easiest way is to just keep a
		 * timestamp from when we checked last time
		 * and see if that was long enough ago.
		 *
		 * But, for now:
		 */
		return Calendar.getInstance().get(Calendar.MINUTE) == remindTime
	}

	private fun getState(context: Context, value: Float): State
	{
		val db          = DatabaseTools(context)
		val preferences = AppPreferences(context)

		// Save value to database
		db.values().insertAll(SensorValue(value))

		// All values for the current hour
		//val values = db.getAllCurrentHour()

		// First, we check if we have reached our goal this hour
		val standing = db.getStandingMinutes(Calendar.getInstance())

		return when
		{
			// If we have already stood up
			standing >= preferences.goalHours * 60 -> State.DONE
			// We're standing up now
			isStanding(value, preferences.sensorSensitivity) -> State.STANDING
			// Not done or standing, must be sitting
			else -> State.SITTING
		}
	}

	fun isStanding(value: Float, sensorSensitivity: Int): Boolean
	{
		val threshold = sensorSensitivity / 10f
		return value > threshold || value < -threshold
	}

	fun getMinutesStandingThisHour()
	{
		// ...
	}

	fun getHoursStandingToday() : Array<Boolean>
	{
		val hours = arrayOf<Boolean>()

		// ...

		return hours
	}

	fun getTotalHoursToday() : Int
	{
		var i = 0

		for (h in getHoursStandingToday())
			if (h)
				i++

		return i
	}
}