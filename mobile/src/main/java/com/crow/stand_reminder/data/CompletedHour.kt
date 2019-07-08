package com.crow.stand_reminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class CompletedHour(@PrimaryKey val date: Calendar, val source: ValueSource)