package com.crow.stand_reminder.list.hourChip;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crow.stand_reminder.R;

import java.util.ArrayList;

public class HourChipAdapter extends RecyclerView.Adapter
{
	private ArrayList<HourChip> entries;

	public HourChipAdapter(ArrayList<HourChip> entries)
	{
		this.entries = entries;
	}

	@NonNull
	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
	{
		// Inflate item layout and create holder
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.chip_hour, parent, false);

		return new HourChipViewHolder(view);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position)
	{
		// Set view attributes based on data
		HourChip chip = entries.get(position);

		HourChipViewHolder viewHolder = (HourChipViewHolder) holder;

		viewHolder.chipView.setText(chip.text);
	}

	@Override
	public int getItemCount()
	{
		return entries.size();
	}
}