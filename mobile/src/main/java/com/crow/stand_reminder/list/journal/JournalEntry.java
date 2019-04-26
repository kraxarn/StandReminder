package com.crow.stand_reminder.list.journal;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.crow.stand_reminder.AppPreferences;
import com.crow.stand_reminder.R;

public class JournalEntry
{
	String date;

	int hours;

	String timeSpan;

	Drawable emoticon;

	private int getEmoticon(float reached)
	{
		/*
		 * 0-49%	-> angry
		 * 50-99%	-> sad
		 * 100-149%	-> happy
		 * 150%>	-> cool
		 */

		if (reached < 0.5)
			return R.drawable.ic_emoticon_angry;

		if (reached < 1.0)
			return R.drawable.ic_emoticon_sad;

		if (reached < 1.5)
			return R.drawable.ic_emoticon_happy;

		return R.drawable.ic_emoticon_cool;
	}

	public JournalEntry(Context context, String date, int hours, String timeSpan)
	{
		// TODO: Getting from app preference for each entry is probably a bad idea
		this.date     = date;
		this.hours    = hours;
		this.timeSpan = timeSpan;
		this.emoticon = context.getResources().getDrawable(
				getEmoticon((float) hours / new AppPreferences(context).getGoalHours()),
				null);
	}
}