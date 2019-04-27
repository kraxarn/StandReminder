package com.crow.stand_reminder.fragment

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import com.crow.stand_reminder.BuildConfig
import com.crow.stand_reminder.R
import com.crow.stand_reminder.tool.ServiceTools
import com.crow.stand_reminder.tool.WearTools

class SettingsFragment : PreferenceFragmentCompat()
{
    private fun setOnPreferenceClickListener(key: String, listener: Preference.OnPreferenceClickListener)
    {
        val preference = findPreference<Preference>(key)

        if (preference != null)
            preference.onPreferenceClickListener = listener
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?)
    {
        addPreferencesFromResource(R.xml.preferences)

        // Set click event for notification settings
        setOnPreferenceClickListener("notification_settings", Preference.OnPreferenceClickListener
        {
            startActivity(if (Build.VERSION.SDK_INT >= 26)
            {
                // Open notification channels on 8.0
                Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS)
                        .putExtra(Settings.EXTRA_APP_PACKAGE, context!!.packageName)
                        .putExtra(Settings.EXTRA_CHANNEL_ID, "reminders")
            }
            else
            {
                // Open generic notification settings on older
                Intent().setAction("android.settings.APP_NOTIFICATION_SETTINGS")
            })

            true
        })

        // Fill list with wear devices
        val wearDevice = findPreference<ListPreference>("wear_device")
        if (wearDevice != null && context != null)
            Thread(Runnable
            {
                val nodes = WearTools.getNodes(context!!)
                if (nodes == null || nodes.isEmpty())
                {
                    val wearCategory = findPreference<PreferenceCategory>("category_wear")
                    if (activity != null && wearCategory != null)
                        activity!!.runOnUiThread { wearCategory.isEnabled = false }
                    return@Runnable
                }

                // Node entries with strings to show in dialog
                val entries = arrayOf<String>()
                // Node IDs to save to preferences
                val values  = arrayOf<String>()

                for ((i, node) in nodes.withIndex())
                {
                    entries[i] = node.displayName
                    values[i]  = node.id
                }

                wearDevice.entries     = entries
                wearDevice.entryValues = values
            }).start()

        // Set correct version info
        val version = findPreference<Preference>("about_version")
        if (version != null)
        {
            version.title = BuildConfig.VERSION_NAME
            if (BuildConfig.BUILD_TYPE == "debug")
                version.summary = "Debug build"
        }

        // Start and stop service
        setOnPreferenceClickListener("debug_start_service", Preference.OnPreferenceClickListener {
            ServiceTools.start(context!!)
            true
        })
        setOnPreferenceClickListener("debug_stop_service", Preference.OnPreferenceClickListener {
            ServiceTools.stop(context!!)
            true
        })

    }
}