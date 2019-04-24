package com.crow.stand_reminder.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;

import com.crow.stand_reminder.BuildConfig;
import com.crow.stand_reminder.R;
import com.crow.stand_reminder.tool.WearTools;
import com.google.android.gms.wearable.Node;

import java.util.List;

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

		// Fill list with wear devices
		ListPreference wearDevice = findPreference("wear_device");
		if (wearDevice != null)
		{
			new Thread(() ->
			{
				List<Node> nodes = WearTools.getNodes(getContext());
				if (nodes == null || nodes.size() == 1)
				{
					PreferenceCategory wearCategory = findPreference("category_wear");
					if (getActivity() != null && wearCategory != null)
						getActivity().runOnUiThread(() -> wearCategory.setEnabled(false));
					return;
				}

				// Node entries with strings to show in dialog
				String[] entries = new String[nodes.size()];
				int entryIndex = 0;
				// Node IDs to save to preferences
				String[] values = new String[nodes.size()];
				int valueIndex = 0;

				for (Node node : nodes)
				{
					entries[entryIndex++] = node.getDisplayName();
					values[valueIndex++]  = node.getId();
				}

				wearDevice.setEntries(entries);
				wearDevice.setEntryValues(values);
			}).start();
		}

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