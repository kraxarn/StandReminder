package com.crow.stand_reminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey

import java.util.Calendar

@Entity
class SensorValue(val value: Float)
{
    @PrimaryKey
    val added: Calendar = Calendar.getInstance()
}