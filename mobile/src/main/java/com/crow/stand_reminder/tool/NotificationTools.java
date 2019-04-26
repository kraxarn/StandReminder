package com.crow.stand_reminder.tool;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationTools
{
	public static void createNotificationChannel(Context context, String id, String name, int importance)
	{
		// Earlier than Oreo doesn't support notification channels
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
			return;
		// Create channel
		context.getSystemService(NotificationManager.class)
				.createNotificationChannel(new NotificationChannel(id, name, importance));
	}

	public static void show(Context context, int id, String channelId, int icon, String title, String text, int priority, boolean ongoing)
	{
		NotificationManagerCompat.from(context)
				.notify(id, new NotificationCompat.Builder(context, channelId)
						.setSmallIcon(icon)
						.setContentTitle(title)
						.setContentText(text)
						.setPriority(priority)
						.setOngoing(ongoing)
						.build());
	}
}