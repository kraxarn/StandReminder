package com.crow.stand_reminder.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.crow.stand_reminder.BuildConfig;
import com.crow.stand_reminder.R;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat
{
	private void setOnPreferenceClickListener(String key, Preference.OnPreferenceClickListener listener)
	{
		Preference preference = findPreference(key);
		if (preference != null)
			preference.setOnPreferenceClickListener(listener);
	}

	@Override
	public void onCreatePreferences(Bundle savedInstanceState, String rootKey)
	{
		addPreferencesFromResource(R.xml.preferences);

		// Set click event for notification settings
		setOnPreferenceClickListener("notification_settings", preference ->
		{
			Intent intent;

			if (getContext() == null)
				return false;
			else if (Build.VERSION.SDK_INT >= 26)
			{
				// Open notification channels on 8.0
				intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
						.putExtra(Settings.EXTRA_APP_PACKAGE, getContext().getPackageName())
						.putExtra(Settings.EXTRA_CHANNEL_ID, "reminders");
			}
			else
			{
				// Open generic notification settings on older
				intent = new Intent()
						.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
			}

			startActivity(intent);

			return true;
		});

		// Set correct version info
		Preference version = findPreference("about_version");
		if (version != null)
		{
			version.setTitle(BuildConfig.VERSION_NAME);
			if (BuildConfig.BUILD_TYPE.equals("debug"))
				version.setSummary("Debug build");
		}
	}
}