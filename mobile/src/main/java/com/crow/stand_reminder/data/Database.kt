package com.crow.stand_reminder.data

import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = [SensorValue::class], version = 1)
abstract class Database : RoomDatabase()
{
    abstract fun values(): SensorValueDao
}