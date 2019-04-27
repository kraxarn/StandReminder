package com.crow.stand_reminder.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.util.*

@Dao
interface SensorValueDao
{
    @get:Query("SELECT * FROM sensorValue")
    val values: List<SensorValue>

    @Query("SELECT * FROM sensorValue WHERE added=:date")
    fun get(date: Calendar): List<SensorValue>

    @Query("SELECT * FROM sensorValue WHERE added BETWEEN :from AND :to")
    fun getBetween(from: Calendar, to: Calendar): List<SensorValue>

    @Query("SELECT * FROM sensorValue WHERE value > :threshold OR value < -:threshold")
    fun getAllStanding(threshold: Float): List<SensorValue>

    @get:Query("SELECT * FROM sensorValue ORDER BY added ASC")
    val valuesAddedAsc: List<SensorValue>

    @Insert
    fun insertAll(vararg values: SensorValue)

    @Delete
    fun delete(value: SensorValue)
}