package com.crow.stand_reminder.list;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VerticalItemSpacing extends RecyclerView.ItemDecoration
{
	private final int height;

	public VerticalItemSpacing(int height)
	{
		this.height = height;
	}

	@Override
	public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state)
	{
		// Insert spacing if not last item
		if (parent.getAdapter() != null && parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1)
			outRect.bottom = height;
	}
}