package com.crow.stand_reminder.list.hourChip;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.crow.stand_reminder.R;
import com.google.android.material.chip.Chip;

class HourChipViewHolder extends RecyclerView.ViewHolder
{
	Chip chipView;

	HourChipViewHolder(@NonNull View itemView)
	{
		super(itemView);
		chipView = itemView.findViewById(R.id.view_chip);
	}
}