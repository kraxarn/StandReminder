package com.crow.stand_reminder.data

import androidx.room.TypeConverter
import java.util.*

class DataConverter
{
	@TypeConverter
	fun toCalendar(value: Long): Calendar =
			Calendar.getInstance().apply { timeInMillis = value }

	@TypeConverter
	fun fromCalendar(value: Calendar): Long =
			value.timeInMillis

	@TypeConverter
	fun toValueSource(value: String): ValueSource =
		ValueSource.valueOf(value)

	@TypeConverter
	fun fromValueSource(value: ValueSource): String =
		value.name
}