package com.crow.stand_reminder.tool

import android.content.Context
import android.os.PowerManager
import android.os.PowerManager.WakeLock

object WakeLockManager
{
	private var wakeLock: WakeLock? = null

	fun aquire(context: Context)
	{
		if (wakeLock != null)
			throw IllegalStateException(
				"Tried to acquire wake lock when one is already acquired")

		wakeLock = (context.getSystemService(Context.POWER_SERVICE) as PowerManager).run {
			newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "StandReminder::UpdateWakeLock").apply {
				acquire(500)
			}
		}
	}

	fun release()
	{
		if (wakeLock == null)
			throw java.lang.IllegalStateException("Tried to release non-existent wake lock")

		wakeLock!!.release()
		wakeLock = null
	}
}