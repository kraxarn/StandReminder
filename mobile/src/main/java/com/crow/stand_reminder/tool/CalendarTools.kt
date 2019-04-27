package com.crow.stand_reminder.tool

import java.text.DecimalFormat
import java.util.*

object CalendarTools
{
	private val df2: DecimalFormat = DecimalFormat("00")

	fun format(calendar: Calendar) =
			"${calendar.get(Calendar.YEAR)}-${df2.format(calendar.get(Calendar.MONTH))}-${df2.format(calendar.get(Calendar.DAY_OF_MONTH))} ${df2.format(calendar.get(Calendar.HOUR_OF_DAY))}:${df2.format(calendar.get(Calendar.MINUTE))}"
}