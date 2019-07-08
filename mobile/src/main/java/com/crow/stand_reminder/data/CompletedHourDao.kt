package com.crow.stand_reminder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CompletedHourDao
{
	@get:Query("SELECT * FROM completedHour")
	val values: List<CompletedHour>

	@Insert
	fun insertAll(vararg values: CompletedHour)

	@Delete
	fun delete(value: CompletedHour)
}