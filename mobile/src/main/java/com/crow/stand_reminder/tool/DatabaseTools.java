package com.crow.stand_reminder.tool;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import androidx.room.Room;

import com.crow.stand_reminder.data.Database;
import com.crow.stand_reminder.data.SensorValue;

public class DatabaseTools
{
	private Database database;

	private Context context;

	public DatabaseTools(Context context)
	{
		this.context = context;

		database = Room.databaseBuilder(context, Database.class, "storage.db").build();
	}

	@Override
	protected void finalize() throws Throwable
	{
		super.finalize();
		database.close();
	}

	public void drop()
	{
		database.clearAllTables();
	}

	public void saveValue(float value)
	{
		database.values().insertAll(new SensorValue(value));
	}

	public void saveValue()
	{
		SensorTools sensorTools = new SensorTools(context);

		sensorTools.getSensorManager().registerListener(new SensorEventListener()
		{
			@Override
			public void onSensorChanged(SensorEvent event)
			{
				// We only want one value
				sensorTools.getSensorManager().unregisterListener(this);
				// It's 1 on mobile, 0 on watch
				saveValue(event.values[1]);
			}

			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy)
			{
			}
		}, sensorTools.getSensor(), SensorManager.SENSOR_DELAY_NORMAL);
	}
}