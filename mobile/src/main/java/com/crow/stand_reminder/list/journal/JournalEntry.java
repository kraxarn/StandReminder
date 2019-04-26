package com.crow.stand_reminder.list.journal;

import android.graphics.drawable.Drawable;

public class JournalEntry
{
	public String date;

	public int hours;

	public String timeSpan;

	public Drawable emoticon;

	public JournalEntry(String date, int hours, String timeSpan, Drawable emoticon)
	{
		this.date     = date;
		this.hours    = hours;
		this.timeSpan = timeSpan;
		this.emoticon = emoticon;
	}
}