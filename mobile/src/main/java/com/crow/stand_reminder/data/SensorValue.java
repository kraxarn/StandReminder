package com.crow.stand_reminder.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class SensorValue
{
	@PrimaryKey
	public Calendar added;

	public float value;

	public SensorValue(float value)
	{
		added = Calendar.getInstance();
		this.value = value;
	}
}