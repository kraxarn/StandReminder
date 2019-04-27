package com.crow.stand_reminder.data

import androidx.room.RoomDatabase
import androidx.room.Database
import androidx.room.TypeConverters

@Database(entities = [SensorValue::class], version = 1)
@TypeConverters(DataConverter::class)
abstract class Database : RoomDatabase()
{
    abstract fun values(): SensorValueDao
}