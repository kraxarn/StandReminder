package com.crow.stand_reminder.tool

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.crow.stand_reminder.R

object NavigationTools
{
	fun setFragment(manager: FragmentManager, fragment: Fragment)
	{
		val transaction = manager.beginTransaction()
		// Set custom animation here if you're feeling fancy
		transaction.replace(R.id.layout_content, fragment)

		// Pass data to the new fragment here if needed

		// Commit
		// TODO: Android 7.0+ only?
		transaction.commitNow()
	}
}