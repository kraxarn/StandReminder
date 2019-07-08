package com.crow.stand_reminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SensorValue(val value: Float, val source: ValueSource)
{
	@PrimaryKey
	var added: Calendar = Calendar.getInstance()
}