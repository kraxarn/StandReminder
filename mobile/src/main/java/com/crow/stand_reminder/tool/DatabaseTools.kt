package com.crow.stand_reminder.tool

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

import androidx.room.Room

import com.crow.stand_reminder.data.Database
import com.crow.stand_reminder.data.SensorValue
import java.io.Closeable

class DatabaseTools(private val context: Context) : Closeable
{
    private val database: Database =
            Room.databaseBuilder(context, Database::class.java, "storage.db").build()

    override fun close() =
            database.close()

    fun drop() =
            database.clearAllTables()

    fun saveValue(value: Float) =
            database.values().insertAll(SensorValue(value))

    fun saveValue()
    {
        val sensorTools = SensorTools(context)

        sensorTools.sensorManager.registerListener(object : SensorEventListener
        {
            override fun onSensorChanged(event: SensorEvent)
            {
                // We only want one value
                sensorTools.sensorManager.unregisterListener(this)
                // It's 1 on mobile, 0 on watch
                saveValue(event.values[1])
            }

            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int)
            {
            }
        }, sensorTools.sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
}