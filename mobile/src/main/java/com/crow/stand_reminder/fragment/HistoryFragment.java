package com.crow.stand_reminder.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.crow.stand_reminder.R;

public class HistoryFragment extends Fragment
{
	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
	{
		// We probably want to save this value, modify the view and then return it
		return inflater.inflate(R.layout.fragment_history, container, false);
	}
}