package com.crow.stand_reminder.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crow.stand_reminder.R;
import com.crow.stand_reminder.list.hourChip.HourChip;
import com.crow.stand_reminder.list.journal.JournalAdapter;
import com.crow.stand_reminder.list.journal.JournalEntry;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class HistoryFragment extends Fragment
{
	private View view;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_history, container, false);

		new Thread(this::fillJournal).start();

		return view;
	}

	private int getRandomEmoticon(Random random)
	{
		switch (random.nextInt(4))
		{
			case 0:
				return R.drawable.ic_emoticon_angry;

			case 1:
				return R.drawable.ic_emoticon_cool;

			case 2:
				return R.drawable.ic_emoticon_happy;

			default:
				return R.drawable.ic_emoticon_sad;
		}
	}

	private ArrayList<JournalEntry> getRandomJournalItems()
	{
		if (getContext() == null)
			return null;

		ArrayList<JournalEntry> entries = new ArrayList<>();
		Random random = new Random();

		DecimalFormat df4 = new DecimalFormat("0000");
		DecimalFormat df2 = new DecimalFormat("00");

		for (int i = 0; i < 2000; i++)
				entries.add(new JournalEntry(getContext(),
					String.format(Locale.getDefault(), "%s-%s-%s",
						df4.format(random.nextInt(100) + 2000),
						df2.format(random.nextInt(12)),
						df2.format(random.nextInt(31))),
					random.nextInt(24),
					String.format("%s:00-%s:00",
						df2.format(random.nextInt(12)),
						df2.format(random.nextInt(12)))));

		return entries;
	}

	private void fillJournal()
	{
		RecyclerView journal   = view.findViewById(R.id.view_journal);
		JournalAdapter adapter = new JournalAdapter(getRandomJournalItems());

		journal.post(() -> {
			// Set adapter and layout
			journal.setAdapter(adapter);
			journal.setLayoutManager(new LinearLayoutManager(getContext()));
			// Hide loading
			view.findViewById(R.id.progress_loading_journal).setVisibility(View.GONE);
		});
	}
}