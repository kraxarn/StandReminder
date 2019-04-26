package com.crow.stand_reminder.list.journal;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.crow.stand_reminder.R;

public class JournalViewHolder extends RecyclerView.ViewHolder
{
	public CardView cardView;

	public TextView textDate;

	public TextView textHours;

	public TextView textTimeSpan;

	public ImageView imageEmoticon;

	public JournalViewHolder(@NonNull View itemView)
	{
		super(itemView);

		cardView      = itemView.findViewById(R.id.view_journal_card);
		textDate      = itemView.findViewById(R.id.text_journal_date);
		textHours     = itemView.findViewById(R.id.text_journal_hours);
		textTimeSpan  = itemView.findViewById(R.id.text_journal_time_span);
		imageEmoticon = itemView.findViewById(R.id.image_journal_emoticon);
	}
}