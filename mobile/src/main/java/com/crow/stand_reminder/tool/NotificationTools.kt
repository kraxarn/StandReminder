package com.crow.stand_reminder.tool

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.crow.stand_reminder.R

object NotificationTools
{
	fun createNotificationChannel(context: Context, id: String, name: String, importance: Int)
	{
		// Earlier than Oreo doesn't support notification channels
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
			return

		// Create channel
		context.getSystemService(NotificationManager::class.java)
				.createNotificationChannel(NotificationChannel(id, name, importance))
	}

	fun show(context: Context, id: Int, channelId: String, icon: Int, title: String,
			 text: String, priority: Int, ongoing: Boolean)
	{
		NotificationManagerCompat.from(context)
				.notify(id, NotificationCompat.Builder(context, channelId)
						.setSmallIcon(icon)
						.setContentTitle(title)
						.setContentText(text)
						.setPriority(priority)
						.setOngoing(ongoing)
						.build())
	}

	fun showSimple(context: Context, id: Int, channelId: String, title: String, text: String)
	{
		show(context, id, channelId, R.drawable.ic_human_greeting,
			title, text, NotificationCompat.PRIORITY_DEFAULT, false)
	}

	fun showSimple(context: Context, id: Int, channelId: String, titleId: Int, textId: Int)
	{
		show(context, id, channelId, R.drawable.ic_human_greeting, context.getString(titleId),
			context.getString(textId), NotificationCompat.PRIORITY_DEFAULT, false)
	}
}