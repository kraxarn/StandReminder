package com.crow.stand_reminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SensorValue(val value: Float)
{
    @PrimaryKey
    var added: Calendar = Calendar.getInstance()
}