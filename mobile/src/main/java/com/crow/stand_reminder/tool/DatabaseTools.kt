package com.crow.stand_reminder.tool

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.room.Room
import com.crow.stand_reminder.AppPreferences
import com.crow.stand_reminder.StateManagerOld
import com.crow.stand_reminder.data.AppDatabase
import com.crow.stand_reminder.data.CompletedHour
import com.crow.stand_reminder.data.SensorValue
import java.io.Closeable
import java.util.*
import kotlin.concurrent.thread

class DatabaseTools(private val context: Context) : Closeable
{
	private val database: AppDatabase =
		Room.databaseBuilder(context, AppDatabase::class.java, "storage.db").build()

	override fun close() =
		database.close()

	fun values() =
		database.values()

	fun completed() =
		database.completed().values

	private fun compareCalendar(date1: Calendar, date2: Calendar, field: Int) =
		date1.get(field) == date2.get(field)

	fun isCompleted(date: Calendar) =
		completed().any { value ->
			compareCalendar(value.date, date, Calendar.YEAR) &&
				compareCalendar(value.date, date, Calendar.MONTH) &&
				compareCalendar(value.date, date, Calendar.DAY_OF_MONTH) &&
				compareCalendar(value.date, date, Calendar.HOUR_OF_DAY)
		}

	fun addCompleted(completed: CompletedHour)
	{
		// Ignore minutes and seconds
		completed.date.set(Calendar.MINUTE,      0)
		completed.date.set(Calendar.SECOND,      0)
		completed.date.set(Calendar.MILLISECOND, 0)

		// Add it to the database
		database.completed().insertAll(completed)
	}

	fun getForDate(date: Calendar): List<SensorValue>
	{
		// I feel like there should be a better way to do this, but knowing Java, there probably isn't
		val from = Calendar.getInstance().apply {
			set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0,  0)
		}
		val to = Calendar.getInstance().apply {
			set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 23,  59)
		}

		return values().getBetween(from, to)
	}

	private fun getAllForHour(cal: Calendar): List<SensorValue>
	{
		val from = Calendar.getInstance().apply {
			set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),  0)
		}
		val to = Calendar.getInstance().apply {
			set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),  59)
		}

		return values().getBetween(from, to)
	}

	fun getAllCurrentHour(): List<SensorValue> =
		getAllForHour(Calendar.getInstance())

	fun getAllCurrentDay(): List<SensorValue> =
		getForDate(Calendar.getInstance())

	fun getStandingMinutes(cal: Calendar): Int
	{
		/*
		 * Get the total amount of minutes counted as standing during the specific hour
		 * We use this later to determine if a specific day reached the goal or not
		 *
		 * We can do this by looping through all values until
		 * we found one that counts as standing, count how many
		 * seconds there were until the check and then continue
		 * checking
		 */

		val values = getAllForHour(cal)

		var total = 0

		for((index, value) in values.withIndex())
		{
			// TODO: Sort of temporary thing to ignore the last value
			/*
			 * (we can't get the next value to compare with)
			 * This should later be replaced with a check to
			 * see if it's comparing with the current hour,
			 * if so, ignore, but otherwise, say that the next
			 * value is the whole of the next hour
			 */
			if (index == values.size - 1)
				continue

			// If it doesn't count as standing up, ignore
			if (!StateManager.isStanding(value.value, AppPreferences(context).sensorSensitivity))
				continue

			// We found one that counts as standing up, add to total
			total += ((values[index + 1].added.timeInMillis - value.added.timeInMillis) / 1000).toInt()
		}

		return total / 60
	}

	fun drop() =
		database.clearAllTables()

	fun saveValue(value: Float) =
		database.values().insertAll(SensorValue(value))

	fun getSensorValue(): Float
	{
		val sensorTools = SensorTools(context)

		var value: Float? = null

		val t = thread(true) {
			sensorTools.sensorManager.registerListener(object : SensorEventListener
			{
				override fun onSensorChanged(event: SensorEvent)
				{
					// We only want one value
					sensorTools.sensorManager.unregisterListener(this)
					// Set value to the value we got
					value = event.values[1]
				}

				override fun onAccuracyChanged(sensor: Sensor, accuracy: Int)
				{
				}
			}, sensorTools.sensor, SensorManager.SENSOR_DELAY_NORMAL)
		}
		t.join()

		return value!!
	}

	fun saveValue()
	{
		val sensorTools = SensorTools(context)

		sensorTools.sensorManager.registerListener(object : SensorEventListener
		{
			override fun onSensorChanged(event: SensorEvent)
			{
				// We only want one value
				sensorTools.sensorManager.unregisterListener(this)
				// It's 1 on mobile, 0 on watch
				// (it needs to be saved on another thread)
				thread(true) {
					saveValue(event.values[1])
				}
			}

			override fun onAccuracyChanged(sensor: Sensor, accuracy: Int)
			{
			}
		}, sensorTools.sensor, SensorManager.SENSOR_DELAY_NORMAL)
	}
}