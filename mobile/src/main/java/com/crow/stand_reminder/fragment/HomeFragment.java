package com.crow.stand_reminder.fragment;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crow.stand_reminder.R;
import com.crow.stand_reminder.list.VerticalItemSpacing;
import com.crow.stand_reminder.list.hourChip.HourChip;
import com.crow.stand_reminder.list.hourChip.HourChipAdapter;
import com.crow.stand_reminder.list.hourChip.HourChipListAdapter;
import com.crow.stand_reminder.list.hourEntry.HourEntry;
import com.crow.stand_reminder.list.hourEntry.HourEntryAdapter;
import com.google.android.material.chip.Chip;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class HomeFragment extends Fragment
{
	private View view;

	private ProgressBar progressBarCircular;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		view = inflater.inflate(R.layout.fragment_home, container, false);

		progressBarCircular = view.findViewById(R.id.progress_circular);

		// Testing
		setProgress(40);
		fillHourEntries();
		fillHourChipsAsGridView();

		return view;
	}

	private void fillHourEntries()
	{
		// Test entries
		int defaultColor = ResourcesCompat.getColor(getResources(), R.color.colorPrimary,        null);
		int noColor      = ResourcesCompat.getColor(getResources(), android.R.color.transparent, null);
		ArrayList<HourEntry> entries = new ArrayList<>();
		Random rng = new Random();
		for (int i = 0; i < 24; i++)
			entries.add(new HourEntry(rng.nextInt(2) == 0 ? defaultColor : noColor,
					String.format(Locale.getDefault(), "%s:00",
							new DecimalFormat("00").format(i)),
					i == 0 ? 0 : 8));

		RecyclerView hourEntries = view.findViewById(R.id.list_hour_entries);
		HourEntryAdapter hourEntryAdapter = new HourEntryAdapter(entries);

		hourEntries.setAdapter(hourEntryAdapter);
		hourEntries.setLayoutManager(new LinearLayoutManager(getContext(),
				LinearLayoutManager.HORIZONTAL,
				false));
	}

	private ArrayList<HourChip> getRandomHourChips()
	{
		ArrayList<HourChip> chips = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; i < 24; i++)
			if (random.nextInt(2) == 0)
				chips.add(new HourChip(String.format(Locale.getDefault(),
						"%s:00", new DecimalFormat("00").format(i)
				)));

			return chips;
	}

	private void fillHourChips()
	{
		RecyclerView hourChips = view.findViewById(R.id.list_hour_chips);
		HourChipAdapter hourChipAdapter = new HourChipAdapter(getRandomHourChips());

		// For spacing
		// TODO: Auto set span count
		GridLayoutManager   layoutManager  = new GridLayoutManager(getContext(), 5);
		VerticalItemSpacing itemDecoration = new VerticalItemSpacing((int) (8 * HourEntryAdapter.densityDpi));

		hourChips.addItemDecoration(itemDecoration);
		hourChips.setAdapter(hourChipAdapter);
		hourChips.setLayoutManager(layoutManager);
	}

	private void fillHourChipsAsGridView()
	{
		GridView hourChips = view.findViewById(R.id.list_hour_chips_grid);
		HourChipListAdapter adapter = new HourChipListAdapter(getRandomHourChips());

		hourChips.setAdapter(adapter);
	}

	private void setProgress(int progress)
	{
		ObjectAnimator animator = ObjectAnimator.ofInt(progressBarCircular,
				"progress",
				progressBarCircular.getProgress(),
				progress);
		animator.setDuration(2000);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());
		animator.start();
	}
}