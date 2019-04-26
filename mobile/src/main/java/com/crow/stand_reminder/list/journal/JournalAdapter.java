package com.crow.stand_reminder.list.journal;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crow.stand_reminder.R;

import java.util.ArrayList;
import java.util.Locale;

public class JournalAdapter extends RecyclerView.Adapter
{
	private ArrayList<JournalEntry> entries;

	public JournalAdapter(ArrayList<JournalEntry> entries)
	{
		this.entries = entries;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		// Inflate item layout and create holder
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View entry = inflater.inflate(R.layout.view_journal, parent, false);

		return new JournalViewHolder(entry);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
	{
		// Set view attributes based on data
		JournalEntry entry = entries.get(position);

		JournalViewHolder viewHolder = (JournalViewHolder) holder;

		viewHolder.textDate.setText(entry.date);
		viewHolder.textHours.setText(String.format(Locale.getDefault(), "%d %s",
				entry.hours,
				entry.hours == 1 ? "hour" : "hours"));
		viewHolder.textTimeSpan.setText(entry.timeSpan);
		viewHolder.imageEmoticon.setImageDrawable(entry.emoticon);
	}

	@Override
	public int getItemCount()
	{
		return entries.size();
	}
}