package com.crow.stand_reminder.tool

import java.text.DecimalFormat
import java.util.*

object CalendarTools
{
	enum class Format
	{
		/**
		 * YYYY-MM-DD HH:MM:SS
		 */
		FULL,
		/**
		 * YYYY-MM-DD
		 */
		DATE,
		/**
		 * HH:MM:SS
		 */
		TIME
	}

	private val df2: DecimalFormat = DecimalFormat("00")

	private fun formatDate(cal: Calendar) =
			"${cal.get(Calendar.YEAR)}-${df2.format(cal.get(Calendar.MONTH))}-${df2.format(cal.get(Calendar.DAY_OF_MONTH))}"

	private fun formatTime(cal: Calendar) =
			"${df2.format(cal.get(Calendar.HOUR_OF_DAY))}:${df2.format(cal.get(Calendar.MINUTE))}:${df2.format(cal.get(Calendar.SECOND))}"

	fun format(cal: Calendar, format: Format): String
	{
		return when(format)
		{
			Format.FULL -> "${formatDate(cal)} ${formatTime(cal)}"
			Format.DATE -> formatDate(cal)
			Format.TIME -> formatTime(cal)
		}
	}
}