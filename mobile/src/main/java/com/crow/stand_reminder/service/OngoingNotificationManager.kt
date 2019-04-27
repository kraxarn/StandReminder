package com.crow.stand_reminder.service

import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.crow.stand_reminder.R
import com.crow.stand_reminder.tool.NotificationTools

object OngoingNotificationManager
{
    private var enabled = false

    const val notificationId = 1010

    const val channelId = "persistent"

    private const val name = "Persistent"

    fun create(context: Context)
    {
        // Just create a notification channel, don't show anything yet
        // TODO: NO
        NotificationTools.createNotificationChannel(context, channelId, name, NotificationCompat.PRIORITY_DEFAULT)
        // Enabled
        enabled = true
    }

    fun update(context: Context, text: String)
    {
        if (enabled)
            NotificationTools.show(
                    context,
                    notificationId,
                    channelId,
                    R.drawable.ic_launcher_foreground,
                    context.getString(R.string.app_name),
                    text,
                    NotificationCompat.PRIORITY_DEFAULT,
                    true)
    }

    fun resume()
    {
        enabled = true
    }

    fun pause(context: Context)
    {
        NotificationManagerCompat.from(context).cancel(notificationId)
        enabled = false
    }
}