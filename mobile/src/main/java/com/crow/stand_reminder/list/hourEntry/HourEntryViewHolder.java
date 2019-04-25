package com.crow.stand_reminder.list.hourEntry;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.crow.stand_reminder.R;

public class HourEntryViewHolder extends RecyclerView.ViewHolder
{
	public CardView cardView;

	public TextView label;

	public HourEntryViewHolder(@NonNull View itemView)
	{
		super(itemView);

		cardView = itemView.findViewById(R.id.view_hour_entry);
		label    = itemView.findViewById(R.id.view_hour_entry_label);
	}
}