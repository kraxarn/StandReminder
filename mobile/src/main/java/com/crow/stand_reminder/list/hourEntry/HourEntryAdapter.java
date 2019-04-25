package com.crow.stand_reminder.list.hourEntry;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.crow.stand_reminder.R;

import java.util.ArrayList;

public class HourEntryAdapter extends RecyclerView.Adapter
{
	private ArrayList<HourEntry> entries;

	public static float densityDpi;

	public HourEntryAdapter(ArrayList<HourEntry> hourEntries)
	{
		entries = hourEntries;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		// Inflate item layout and create holder
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View entry = inflater.inflate(R.layout.list_hour_entry, parent, false);

		return new HourEntryViewHolder(entry);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
	{
		// Set view attributes based on data
		HourEntry entry = entries.get(position);

		HourEntryViewHolder viewHolder = (HourEntryViewHolder) holder;

		viewHolder.cardView.setCardBackgroundColor(entry.backgroundColor);
		viewHolder.label.setText(entry.hour);

		// Set correct margin
		if (entry.leftMargin > 0)
		{
			ViewGroup.LayoutParams p = viewHolder.cardView.getLayoutParams();
			CardView.LayoutParams params = new CardView.LayoutParams(p);
			params.setMargins((int) (entry.leftMargin * densityDpi), 0, 0, 0);
			viewHolder.cardView.setLayoutParams(params);
		}
	}

	@Override
	public int getItemCount()
	{
		return entries.size();
	}
}