package com.crow.stand_reminder.tool

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import androidx.room.Room
import com.crow.stand_reminder.data.AppDatabase
import com.crow.stand_reminder.data.SensorValue
import java.io.Closeable
import java.util.*

class DatabaseTools(private val context: Context) : Closeable
{
    private val database: AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, "storage.db").build()

    override fun close() {
        database.close()
    }

    fun values() =
            database.values()

    fun getForDate(date: Calendar): List<SensorValue>
    {
        // I feel like there should be a better way to do this, but knowing Java, there probably isn't
        val from = Calendar.getInstance().apply {
            set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 0,  0)
        }
        val to = Calendar.getInstance().apply {
            set(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), 23,  59)
        }

        return values().getBetween(from, to)
    }

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