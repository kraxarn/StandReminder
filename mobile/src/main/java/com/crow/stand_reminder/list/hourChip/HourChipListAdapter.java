package com.crow.stand_reminder.list.hourChip;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.crow.stand_reminder.R;
import com.google.android.material.chip.Chip;

import java.util.ArrayList;

public class HourChipListAdapter extends BaseAdapter
{
	private ArrayList<HourChip> entries;

	public HourChipListAdapter(ArrayList<HourChip> entries)
	{
		this.entries = entries;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Context context = parent.getContext();
		LayoutInflater inflater = LayoutInflater.from(context);

		View gridView;

		if (convertView == null)
		{
			// Get layout
			gridView = inflater.inflate(R.layout.chip_hour, parent, false);

			// Set values
			Chip chip = gridView.findViewById(R.id.view_chip);
			chip.setText(entries.get(position).text);
		}
		else
			gridView = convertView;

		return gridView;
	}

	@Override
	public int getCount()
	{
		return entries.size();
	}

	@Override
	public Object getItem(int position)
	{
		return entries.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}
}