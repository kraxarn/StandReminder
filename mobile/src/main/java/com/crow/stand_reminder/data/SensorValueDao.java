package com.crow.stand_reminder.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Calendar;
import java.util.List;

@Dao
public interface SensorValueDao
{
	@Query("SELECT * FROM sensorvalue")
	List<SensorValue> getAll();

	@Query("SELECT * FROM sensorvalue WHERE added=:date")
	List<SensorValue> get(Calendar date);

	@Insert
	void insertAll(SensorValue... values);

	@Delete
	void delete(SensorValue value);
}