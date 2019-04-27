package com.crow.stand_reminder.tool

import android.app.AlertDialog
import android.content.Context

object AlertTools
{
    private fun build(context: Context): AlertDialog.Builder
    {
        // If we want to use a custom style for all dialogs later
        return AlertDialog.Builder(context)
    }

    private fun buildSimple(context: Context, title: String): AlertDialog.Builder
    {
        return build(context)
                .setTitle(title)
                .setPositiveButton("OK", null)
    }

    fun showSimple(context: Context, title: String, message: String)
    {
        buildSimple(context, title)
                .setMessage(message)
                .show()
    }
}