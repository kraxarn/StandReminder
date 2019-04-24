package com.crow.stand_reminder.tool;

import android.content.Context;

import androidx.annotation.WorkerThread;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.Wearable;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class WearTools
{
	@WorkerThread
	public static List<Node> getNodes(Context context)
	{
		Task<List<Node>> task = Wearable.getNodeClient(context).getConnectedNodes();

		try
		{
			return Tasks.await(task);
		}
		catch (ExecutionException | InterruptedException e)
		{
			e.printStackTrace();
			return null;
		}
	}
}

