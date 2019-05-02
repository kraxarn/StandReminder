package com.crow.stand_reminder

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.crow.stand_reminder.fragment.HistoryFragment
import com.crow.stand_reminder.fragment.HomeFragment
import com.crow.stand_reminder.fragment.SettingsFragment
import com.crow.stand_reminder.tool.DatabaseTools
import com.crow.stand_reminder.tool.NavigationTools
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*
import kotlin.concurrent.fixedRateTimer
import kotlin.concurrent.thread
import kotlin.concurrent.timer
import kotlin.concurrent.timerTask

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener
{
	// Fragments for the different tabs
	private var homeFragment:     HomeFragment?     = null
	private var historyFragment:  HistoryFragment?  = null
	private var settingsFragment: SettingsFragment? = null

	override fun onCreate(savedInstanceState: Bundle?)
	{
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)

		// Set navigation bar color
		window.navigationBarColor = ResourcesCompat.getColor(resources, R.color.colorPrimary, null)

		// Preload all fragments
		homeFragment     = HomeFragment()
		historyFragment  = HistoryFragment()
		settingsFragment = SettingsFragment()

		// Default shown fragment
		NavigationTools.setFragment(supportFragmentManager, homeFragment!!)

		// Setup navigation listener
		findViewById<BottomNavigationView>(R.id.view_navigation).setOnNavigationItemSelectedListener(this)

		// Set density for hour chips
		density = resources.displayMetrics.density

		// TESTING
		thread(true) {
			Log.d("MAIN", "Waiting for sensor value...")
			val v = DatabaseTools(this).getSensorValue()
			Log.d("MAIN", "Found value: $v")
		}
	}

	private fun getFragmentFromId(id: Int): Fragment? =
		when (id)
		{
			R.id.navigation_home     -> homeFragment
			R.id.navigation_history  -> historyFragment
			R.id.navigation_settings -> settingsFragment
			else                     -> null
		}

	override fun onNavigationItemSelected(menuItem: MenuItem): Boolean
	{
		val fragment = getFragmentFromId(menuItem.itemId)

		if (fragment != null)
		{
			// Set new selected item
			NavigationTools.setFragment(supportFragmentManager, fragment)

			// Display item as selected item
			return true
		}

		return false
	}

	companion object
	{
		private var density = 0f

		// Density for use in other things
		val densityDpi: Float
			get() = this.density
	}
}