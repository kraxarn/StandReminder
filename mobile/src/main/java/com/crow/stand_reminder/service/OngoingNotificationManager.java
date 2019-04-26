package com.crow.stand_reminder.service;

import android.app.Notification;
import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.crow.stand_reminder.R;
import com.crow.stand_reminder.tool.NotificationTools;

public class OngoingNotificationManager
{
	private static boolean enabled = false;

	private static final int notificationId = 1010;

	private static final String channelId = "persistent";

	private static final String name = "Persistent";

	public static void create(Context context)
	{
		// Just create a notification channel, don't show anything yet
		// TODO: NO
		NotificationTools.createNotificationChannel(context, channelId, name, Notification.PRIORITY_DEFAULT);
		// Enabled
		enabled = true;
	}

	public static void update(Context context, String text)
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
					true);
	}

	public static void resume()
	{
		enabled = true;
	}

	public static void pause(Context context)
	{
		NotificationManagerCompat.from(context).cancel(notificationId);
		enabled = false;
	}

	public static int getNotificationId()
	{
		return notificationId;
	}

	public static String getChannelId()
	{
		return channelId;
	}
}