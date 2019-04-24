package com.crow.stand_reminder.tool;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.crow.stand_reminder.R;

public class NavigationTools
{
	public static void setFragment(FragmentManager manager, Fragment fragment)
	{
		FragmentTransaction transaction = manager.beginTransaction();
		// Set custom animation here if you're feeling fancy
		transaction.replace(R.id.layout_content, fragment);

		// Pass data to the new fragment here if needed

		// Commit
		// TODO: Android 7.0+ only?
		transaction.commitNow();
	}
}