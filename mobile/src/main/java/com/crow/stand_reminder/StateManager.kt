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

	var state: State = State.UNKNOWN

	fun getState(context: Context): State
	{
		val db          = DatabaseTools(context)
		val preferences = AppPreferences(context)

		// All values for the current hour
		//val values = db.getAllCurrentHour()

		// First, we check if we have reached our goal this hour
		val standing = db.getStandingMinutes(Calendar.getInstance())

		if (standing >= preferences.goalHours * 60)
			return State.DONE

		// TODO: For now, ignore the rest
		return State.UNKNOWN
	}

	fun isStanding(context: Context, value: SensorValue): Boolean
	{
		val threshold = AppPreferences(context).sensorSensitivity / 10f
		return value.value > threshold || value.value < -threshold
	}
}