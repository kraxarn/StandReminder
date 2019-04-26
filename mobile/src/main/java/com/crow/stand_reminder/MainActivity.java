package com.crow.stand_reminder;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.crow.stand_reminder.fragment.HistoryFragment;
import com.crow.stand_reminder.fragment.HomeFragment;
import com.crow.stand_reminder.fragment.SettingsFragment;
import com.crow.stand_reminder.list.hourEntry.HourEntryAdapter;
import com.crow.stand_reminder.service.OngoingNotificationManager;
import com.crow.stand_reminder.tool.NavigationTools;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{
	// Fragments for the different tabs
	private HomeFragment     homeFragment;
	private HistoryFragment  historyFragment;
	private SettingsFragment settingsFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Set navigation bar color
		getWindow().setNavigationBarColor(ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null));

		// Preload all fragments
		homeFragment     = new HomeFragment();
		historyFragment  = new HistoryFragment();
		settingsFragment = new SettingsFragment();

		// Default shown fragment
		NavigationTools.setFragment(getSupportFragmentManager(), homeFragment);

		// Setup navigation listener
		((BottomNavigationView) findViewById(R.id.view_navigation)).setOnNavigationItemSelectedListener(this);

		// Set density for hour entries
		HourEntryAdapter.densityDpi = getResources().getDisplayMetrics().density;

		// Create persistent notification
		OngoingNotificationManager.create(this);
	}

	private Fragment getFragmentFromId(int id)
	{
		switch (id)
		{
			case R.id.navigation_home:
				return homeFragment;

			case R.id.navigation_history:
				return historyFragment;

			case R.id.navigation_settings:
				return settingsFragment;
		}

		return null;
	}

	@Override
	public boolean onNavigationItemSelected(@NonNull MenuItem menuItem)
	{
		Fragment fragment = getFragmentFromId(menuItem.getItemId());

		if (fragment != null)
		{
			// Set new selected item
			NavigationTools.setFragment(getSupportFragmentManager(), fragment);

			// Display item as selected item
			return true;
		}

		return false;
	}
}