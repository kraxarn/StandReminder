package com.crow.stand_reminder.list.journal

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

import com.crow.stand_reminder.R

class JournalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
{
	var cardView:      CardView  = itemView.findViewById(R.id.view_journal_card)
	var textDate:      TextView  = itemView.findViewById(R.id.text_journal_date)
	var textHours:     TextView  = itemView.findViewById(R.id.text_journal_hours)
	var textTimeSpan:  TextView  = itemView.findViewById(R.id.text_journal_time_span)
	var imageEmoticon: ImageView = itemView.findViewById(R.id.image_journal_emoticon)
}