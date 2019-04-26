package com.crow.stand_reminder.service;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.crow.stand_reminder.R;
import com.crow.stand_reminder.tool.DatabaseTools;

import java.util.Calendar;

public class KeepAliveService extends Service
{
	private static Thread thread;

	@Nullable
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		thread = new Thread(() ->
		{
			// TODO: For testing only, just save a value every minute

			new DatabaseTools(this).saveValue();

			try
			{
				Thread.sleep(60000);
				OngoingNotificationManager.update(this,
						String.format("Updated on %s", Calendar.getInstance()));
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		});

		thread.start();
		startForeground();

		return super.onStartCommand(intent, flags, startId);
	}

	public static void stop()
	{
		if (thread != null)
			thread.interrupt();
	}

	private void startForeground()
	{
		OngoingNotificationManager.create(this);

		Intent intent = new Intent(this, KeepAliveService.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

		startForeground(OngoingNotificationManager.getNotificationId(),
				new NotificationCompat.Builder(this, OngoingNotificationManager.getChannelId())
						.setOngoing(true)
						.setSmallIcon(R.drawable.ic_launcher_foreground)
						.setContentTitle(getString(R.string.app_name))
						.setContentTitle("Running in the background...")
						.setContentIntent(pendingIntent)
						.build());
	}
}