package com.crow.stand_reminder.data

import androidx.room.TypeConverter
import java.util.*

class DataConverter
{
	@TypeConverter
	fun toDate(value: Long): Date = value.let(::Date)

	@TypeConverter
	fun fromDate(value: Date): Long = value.time

	@TypeConverter
	fun toCalendar(value: Long): Calendar =
			Calendar.getInstance().apply { timeInMillis = value }

	@TypeConverter
	fun fromCalendar(value: Calendar): Long =
			value.timeInMillis
}