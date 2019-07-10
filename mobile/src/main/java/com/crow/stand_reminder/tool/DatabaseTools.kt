package com.crow.stand_reminder.tool

import android.content.Context
import androidx.room.Room
import com.crow.stand_reminder.data.AppDatabase
import com.crow.stand_reminder.data.CompletedHour
import java.io.Closeable
import java.util.*

class DatabaseTools(context: Context) : Closeable
{
	private val database: AppDatabase =
		Room.databaseBuilder(context, AppDatabase::class.java, "storage.db").build()

	override fun close() =
		database.close()

	private fun completed() =
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

	/**
	 * Sets minute, second and millisecond to 0
	 */
	private fun Calendar.resetMinutes()
	{
		(this).apply {
			set(Calendar.MINUTE, 0)
			set(Calendar.SECOND, 0)
			set(Calendar.MILLISECOND, 0)
		}
	}

	private fun Calendar.toResetMinutes(): Calendar =
		this.apply {
			resetMinutes()
		}

	private fun Calendar.toResetHours(): Calendar =
		this.apply {
			resetMinutes()
			set(Calendar.HOUR_OF_DAY, 0)
		}

	fun addCompleted(completed: CompletedHour)
	{
		// Ignore minutes and seconds
		completed.date.resetMinutes()
		// Add it to the database
		database.completed().insertAll(completed)
	}

	fun getCompletedToday(): List<CompletedHour> =
		Calendar.getInstance().toResetHours().let {
			return completed().filter { c ->
				c.date.toResetHours() == it
			}
		}

	fun drop() =
		database.clearAllTables()
}