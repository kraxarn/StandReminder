package com.crow.stand_reminder.tool

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

import com.crow.stand_reminder.AppPreferences

class SensorTools(context: Context)
{
	val sensorManager =
			context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

	val sensor =
			getBestSensor(context)

	private fun getRequestedSensorType(context: Context): Int =
			if (AppPreferences(context).sensorUseGravity)
				Sensor.TYPE_GRAVITY
			else
				Sensor.TYPE_ACCELEROMETER

	private fun getBestSensor(context: Context): Sensor =
			sensorManager.getDefaultSensor(getRequestedSensorType(context))
}