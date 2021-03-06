package com.crow.stand_reminder.data

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters

@Database(entities = [CompletedHour::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase()
{
	abstract fun completed(): CompletedHourDao
}