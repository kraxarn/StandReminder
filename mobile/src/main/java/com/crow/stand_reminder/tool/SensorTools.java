package com.crow.stand_reminder.tool;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;

import com.crow.stand_reminder.AppPreferences;

public class SensorTools
{
	private SensorManager sensorManager;

	private Sensor sensor;

	public SensorTools(Context context)
	{
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		sensor        = getBestSensor(context);
	}

	private int getRequestedSensorType(Context context)
	{
		return new AppPreferences(context).getSensorUseGravity()
				? Sensor.TYPE_GRAVITY
				: Sensor.TYPE_ACCELEROMETER;
	}

	private Sensor getBestSensor(Context context)
	{
		return sensorManager.getDefaultSensor(getRequestedSensorType(context));
	}

	public SensorManager getSensorManager()
	{
		return sensorManager;
	}

	public Sensor getSensor()
	{
		return sensor;
	}
}