package com.crow.stand_reminder.data

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters

@Database(entities = [SensorValue::class, CompletedHour::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase()
{
	@Deprecated(message = "Replaced by temporary list")
	abstract fun values(): SensorValueDao

	abstract fun completed(): CompletedHourDao
}