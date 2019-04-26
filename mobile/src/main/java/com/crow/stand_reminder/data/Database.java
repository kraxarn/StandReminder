package com.crow.stand_reminder.data;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {
		SensorValue.class
}, version = 1)
public abstract class Database extends RoomDatabase
{
	public abstract SensorValueDao values();
}