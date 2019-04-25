package com.crow.stand_reminder.list.hourEntry;

public class HourEntry
{
	public int backgroundColor;

	public String hour;

	public int leftMargin;

	/**
	 * Create new hour entry
	 */
	public HourEntry(int backgroundColor, String hour, int leftMargin)
	{
		this.backgroundColor = backgroundColor;
		this.hour            = hour;
		this.leftMargin      = leftMargin;
	}
}