package com.crow.stand_reminder.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.*
import android.os.Handler
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.crow.stand_reminder.*
import com.crow.stand_reminder.data.ValueSource
import com.crow.stand_reminder.tool.*
import java.util.*
import kotlin.concurrent.thread

private class ServiceTask(val context: Context) : Runnable
{
	private val sensorTools = SensorTools(context)
	private val database    = DatabaseTools(context)
	private val preferences = AppPreferences(context)

	override fun run()
	{
		// Check if we should be running
		if (!KeepAliveService.isRunning())
		{
			OngoingNotificationManager.update(context,"Task running, but not service")
			//timer.cancel()
			//return
		}

		// Update notification
		OngoingNotificationManager.update(context,"Updating...")

		// Get value
		sensorTools.sensorManager.registerListener(object : SensorEventListener
		{
			override fun onAccuracyChanged(p0: Sensor?, p1: Int)
			{
			}

			override fun onSensorChanged(event: SensorEvent?)
			{
				// Ignore if null
				if (event == null)
				{
					Log.e("KeepAliveService", "Sensor updated without value")
					return
				}

				// We only want one value
				sensorTools.sensorManager.unregisterListener(this)

				// We do the rest from another thread
				thread(true)
				{
					// Do stuff with value
					val next = StateManager.update(event.values[1], ValueSource.MOBILE,
						database, preferences, context)

					// TODO: Having longer delays seem to kill the app
					/*
					var nextMs = when (next)
					{
						StateManager.CheckDelay.NONE -> minutesToNextHour * 1000L * 60L
						StateManager.CheckDelay.HALF -> 30L * 1000L
						StateManager.CheckDelay.FULL -> 60L * 1000L
						else -> -1L
					}
					 */

					// Get from preferences (in minutes) when to refresh next
					val nextMs = preferences.batteryFrequency * 60L * 1000L

					// Update notification
					OngoingNotificationManager.update(context,
						"Updated on ${CalendarTools.format(Calendar.getInstance(),
							CalendarTools.Format.FULL)} (${next.name} in ${nextMs / 60000L}m)")

					// Schedule next check (in milliseconds)
					if (!schedule(this@ServiceTask, nextMs))
						NotificationTools.showSimple(context, 1015, "general",
							"Error", "The next check failed to reschedule")
				}
			}
		}, sensorTools.sensor, SensorManager.SENSOR_DELAY_NORMAL)
	}

	companion object
	{
		var handler = Handler()

		fun schedule(runnable: Runnable, delay: Long) =
			handler.postDelayed(runnable, delay)

		private val minutesToNextHour
			get() = 60 - Calendar.getInstance().get(Calendar.MINUTE)
	}
}

class KeepAliveService : Service()
{
	override fun onBind(intent: Intent): IBinder? = null

	override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
	{
		when (intent.action)
		{
			ACTION_START_SERVICE -> start()
			ACTION_STOP_SERVICE  -> stop()
		}

		return super.onStartCommand(intent, flags, startId)
	}

	private fun start()
	{
		Log.i("SERVICE", "Starting foreground service...")
		ServiceTask.schedule(ServiceTask(this), 1000)

		startForeground()
	}

	private fun stop()
	{
		// Stop the thread
		interrupt()

		// Stop the service and remove notification
		stopForeground(true)
	}

	private fun startForeground()
	{
		OngoingNotificationManager.create(this)

		val intent        = Intent()
		val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

		startForeground(OngoingNotificationManager.notificationId,
			NotificationCompat.Builder(this, OngoingNotificationManager.channelId)
				.setOngoing(true)
				.setSmallIcon(R.drawable.ic_human_greeting)
				.setContentText("Running in the background...")
				.setContentIntent(pendingIntent)
				.build())
	}

	// Companion object for static fields
	companion object
	{
		private var thread: Thread? = null

		const val ACTION_START_SERVICE = "ACTION_START_SERVICE"
		const val ACTION_STOP_SERVICE  = "ACTION_STOP_SERVICE"

		fun isRunning() =
			thread?.isAlive ?: false

		fun interrupt()
		{
			if (thread != null)
				thread!!.interrupt()
		}
	}
}