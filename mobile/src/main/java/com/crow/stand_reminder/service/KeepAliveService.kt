package com.crow.stand_reminder.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat

import com.crow.stand_reminder.R
import com.crow.stand_reminder.tool.DatabaseTools

import java.util.Calendar

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

        thread = Thread {
            while (true)
            {
                // TODO: For testing only, just save a value every minute

                // Get value and save in a different thread
                DatabaseTools(this).saveValue()

                Log.i("SERVICE", "Updating...")

                try
                {
                    Thread.sleep(60000)
                    OngoingNotificationManager.update(this, "Updated on ${Calendar.getInstance()}")
                }
                catch (e: InterruptedException)
                {
                    e.printStackTrace()
                }
            }
        }

        thread!!.start()
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
                        .setSmallIcon(R.drawable.ic_launcher_foreground)
                        .setContentTitle(getString(R.string.app_name))
                        .setContentTitle("Running in the background...")
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