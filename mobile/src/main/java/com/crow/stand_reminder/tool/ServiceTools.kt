package com.crow.stand_reminder.tool

import android.content.Context
import android.content.Intent
import com.crow.stand_reminder.service.KeepAliveService

object ServiceTools
{
	private fun startIntent(context: Context, action: String) =
			context.startService(Intent(context, KeepAliveService::class.java).apply {
				this.action = action
			})

	private fun stopIntent(context: Context) =
			context.stopService(Intent(context, KeepAliveService::class.java))

	fun start(context: Context) =
			startIntent(context, KeepAliveService.ACTION_START_SERVICE)

	fun stop(context: Context) =
			startIntent(context, KeepAliveService.ACTION_STOP_SERVICE)

	fun forceStop(context: Context) =
			stopIntent(context)
}