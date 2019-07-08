package com.crow.stand_reminder.tool

import android.content.Context
import android.util.Log
import androidx.annotation.WorkerThread
import com.google.android.gms.tasks.Tasks
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import java.util.concurrent.ExecutionException

object WearTools
{
	@WorkerThread
	fun getNodes(context: Context): List<Node>?
	{
		val task = Wearable.getNodeClient(context).connectedNodes

		return try
		{
			Tasks.await(task)
		}
		catch (e: ExecutionException)
		{
			Log.w("WEAR", e.message ?: "ExecutionException")
			null
		}
		catch (e: InterruptedException)
		{
			e.printStackTrace()
			null
		}
	}
}

