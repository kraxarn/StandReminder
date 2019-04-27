package com.crow.stand_reminder.data

import androidx.room.TypeConverter
import java.util.*

class DataConverter
{
	@TypeConverter
	fun toDate(value: Long): Date = value.let(::Date)

	@TypeConverter
	fun fromDate(value: Date): Long = value.time
}