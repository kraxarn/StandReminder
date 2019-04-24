package com.crow.stand_reminder.tool;

import android.app.AlertDialog;
import android.content.Context;

public class AlertTools
{
	private static AlertDialog.Builder build(Context context)
	{
		// If we want to use a custom style for all dialogs later
		return new AlertDialog.Builder(context);
	}

	private static AlertDialog.Builder buildSimple(Context context, String title)
	{
		return build(context)
				.setTitle(title)
				.setPositiveButton("OK", null);
	}

	public static void showSimple(Context context, String title, String message)
	{
		buildSimple(context, title)
				.setMessage(message)
				.show();
	}
}