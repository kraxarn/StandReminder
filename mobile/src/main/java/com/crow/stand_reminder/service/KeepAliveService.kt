package com.crow.stand_reminder.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

import com.crow.stand_reminder.R
import com.crow.stand_reminder.tool.DatabaseTools

import java.util.Calendar

class KeepAliveService : Service()
{
    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int
    {
        thread = Thread {
            // TODO: For testing only, just save a value every minute

            DatabaseTools(this).saveValue()

            try
            {
                Thread.sleep(60000)
                OngoingNotificationManager.update(this,
                        String.format("Updated on %s", Calendar.getInstance()))
            }
            catch (e: InterruptedException)
            {
                e.printStackTrace()
            }
        }

        thread!!.start()
        startForeground()

        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForeground()
    {
        OngoingNotificationManager.create(this)

        val intent        = Intent(this, KeepAliveService::class.java)
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

        fun stop()
        {
            if (thread != null)
                thread!!.interrupt()
        }
    }
}