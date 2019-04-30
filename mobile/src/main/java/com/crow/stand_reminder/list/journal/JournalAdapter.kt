package com.crow.stand_reminder.list.journal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.crow.stand_reminder.R

import java.util.ArrayList
import java.util.Locale

class JournalAdapter(private val entries: ArrayList<JournalEntry>) : RecyclerView.Adapter<JournalViewHolder>()
{

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder
	{
		// Inflate item layout and create holder
		val inflater = LayoutInflater.from(parent.context)
		val entry = inflater.inflate(R.layout.view_journal, parent, false)

		return JournalViewHolder(entry)
	}

	override fun onBindViewHolder(holder: JournalViewHolder, position: Int)
	{
		// Set view attributes based on data
		val entry = entries[position]

		holder.textDate.text     = entry.date
		holder.textHours.text    = "${entry.hours} ${if (entry.hours == 1) "hour" else "hours"}"
		holder.textTimeSpan.text = entry.timeSpan
		holder.imageEmoticon.setImageDrawable(entry.emoticon)
	}

	override fun getItemCount(): Int =
		entries.size
}