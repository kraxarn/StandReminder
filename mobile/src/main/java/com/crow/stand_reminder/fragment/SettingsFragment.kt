package com.crow.stand_reminder.fragment

import android.content.Intent
import android.content.res.Configuration
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.InputType
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.preference.*
import com.crow.stand_reminder.*
import com.crow.stand_reminder.R
import com.crow.stand_reminder.service.KeepAliveService
import com.crow.stand_reminder.tool.*
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.concurrent.thread

class SettingsFragment : PreferenceFragmentCompat()
{
	private fun setOnPreferenceClickListener(key: String, listener: Preference.OnPreferenceClickListener)
	{
		val preference = findPreference<Preference>(key)

		if (preference != null)
			preference.onPreferenceClickListener = listener
	}

	/**
	 * Custom date to string formatter
	 */
	val Calendar.asString
		get() = CalendarTools.format(this, CalendarTools.Format.FULL)

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
			thread(true) {
				val nodes = WearTools.getNodes(context!!)
				if (nodes == null || nodes.isEmpty())
				{
					val wearCategory = findPreference<PreferenceCategory>("category_wear")
					if (activity != null && wearCategory != null)
						activity!!.runOnUiThread { wearCategory.isEnabled = false }
					return@thread
				}

				// Display names to show in settings
				wearDevice.entries = nodes.map {
					node -> node.displayName
				}.toTypedArray()

				// IDs to save to preferences
				wearDevice.entryValues = nodes.map {
					node -> node.id
				}.toTypedArray()
			}

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
		setOnPreferenceClickListener("debug_force_stop_service", Preference.OnPreferenceClickListener {
			ServiceTools.forceStop(context!!)
		})

		// Delete all values
		setOnPreferenceClickListener("debug_drop_database", Preference.OnPreferenceClickListener {
			val toast = Toast.makeText(context!!, "Database dropped", Toast.LENGTH_SHORT)
			thread(true) {
				DatabaseTools(context!!).drop()
				toast.show()
			}
			true
		})

		// Show today values
		setOnPreferenceClickListener("debug_show_values", Preference.OnPreferenceClickListener {
			activity?.let {
				AlertDialog.Builder(it).apply {
					setTitle("Select Values to Show")
					setItems(arrayOf("Today", "This Hour")) { _, i ->
						thread(true)
						{
							// Default value
							val values = mutableListOf<String>()
							var title  = "No Values"

							when (i)
							{
								// Today (get from db)
								0 -> {
									// Get the temporary values
									values += DatabaseTools(context).getCompletedToday().map { v ->
										"${v.date.asString}: ${v.source.name}"
									}
									// Set values to show in dialog
									title = "Today (${values.count()} values)"
								}
								// This hour (get from temporary values)
								1 -> {
									values += StateManager.temporaryValues.map { v ->
										v.toString()
									}
									title = "This Hour (${values.count()} values)"
								}
							}
							// Format text with builder
							val builder = StringBuilder()
							for (value in values)
								builder.append("$value\n")
							// Show dialog
							activity?.runOnUiThread {
								AlertTools.showSimple(context, title,
									if (values.count() == 0) "No values found"
									else builder.toString())
							}
						}
					}
					show()
				}
			}

			true
		})

		// Debug info
		setOnPreferenceClickListener("debug_info", Preference.OnPreferenceClickListener {
			thread {
				// Get database to show stuffs later
				val db = DatabaseTools(context!!)
				// Prepare message
				val message = "ServiceRunning=${KeepAliveService.isRunning}\n" +
						"FirstCompleted=${db.firstCompleted.let { it ?: "None" }}\n" +
						"InstanceDate=${CalendarTools.format(Calendar.getInstance(), CalendarTools.Format.DATE)}"
				// Show alert
				activity?.runOnUiThread {
					AlertTools.showSimple(context!!, "Debug Info", message)
				}
			}
			true
		})

		val blackBackground = findPreference<SwitchPreference>("black_background")

		// Enable black background switch if using dark mode
		blackBackground?.isEnabled =
			resources.configuration.uiMode and
				Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES

		blackBackground?.setOnPreferenceChangeListener { _, _ ->
			activity?.setTheme(R.style.AppTheme_Black)
			Snackbar.make(activity!!.findViewById(R.id.layout_content),
				"Restart to (properly) apply changes", Snackbar.LENGTH_LONG).apply {
				setAnchorView(R.id.view_navigation)
				setAction("Restart Now") {
					activity?.recreate()
				}
				show()
			}
			true
		}

		// Workaround for number entry not working when set from XML
		findPreference<EditTextPreference>("goal_hour")?.setOnBindEditTextListener {
			editText -> editText.inputType = InputType.TYPE_CLASS_NUMBER
		}
	}
}