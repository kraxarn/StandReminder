package com.crow.stand_reminder.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.crow.stand_reminder.tool.CalendarTools
import java.util.*

@Entity
data class CompletedHour(@PrimaryKey val date: Calendar, val source: ValueSource)
{
	override fun toString(): String =
		"${CalendarTools.format(date, CalendarTools.Format.FULL)}: $source"
}