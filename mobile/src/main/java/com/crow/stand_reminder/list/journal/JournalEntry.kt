package com.crow.stand_reminder.list.journal

import android.content.Context
import android.graphics.drawable.Drawable

import com.crow.stand_reminder.AppPreferences
import com.crow.stand_reminder.R

class JournalEntry(context: Context, internal var date: String, internal var hours: Int, internal var timeSpan: String)
{
	// TODO: Getting from app preference for each entry is probably a bad idea
	internal var emoticon: Drawable = context.resources.getDrawable(
		getEmoticon(hours.toFloat() / AppPreferences(context).goalHours),
		null)

	private fun getEmoticon(reached: Float): Int
	{
		/*
		 * 0-49%	-> angry
		 * 50-99%	-> sad
		 * 100-149%	-> happy
		 * 150%>	-> cool
		 */

		if (reached < 0.5)
			return R.drawable.ic_emoticon_angry

		if (reached < 1.0)
			return R.drawable.ic_emoticon_sad

		return if (reached < 1.5) R.drawable.ic_emoticon_happy else R.drawable.ic_emoticon_cool

	}
}