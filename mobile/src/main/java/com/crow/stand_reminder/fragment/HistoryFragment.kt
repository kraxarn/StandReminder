package com.crow.stand_reminder.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.crow.stand_reminder.R
import com.crow.stand_reminder.list.journal.JournalAdapter
import com.crow.stand_reminder.list.journal.JournalEntry

import java.text.DecimalFormat
import java.util.ArrayList
import java.util.Locale
import java.util.Random
import kotlin.concurrent.thread

class HistoryFragment : Fragment()
{
	private var root: View? = null

	private val randomJournalItems: ArrayList<JournalEntry>?
		get()
		{
			if (context == null)
				return null

			val entries = arrayListOf<JournalEntry>()
			val random  = Random()

			val df4 = DecimalFormat("0000")
			val df2 = DecimalFormat("00")

			for (i in 0..2000)
				entries += JournalEntry(context!!,
					"${df4.format(random.nextInt(100) + 2000)}-${df2.format(random.nextInt(12))}-${df2.format(random.nextInt(31))}",
					random.nextInt(24),
					"${df2.format(random.nextInt(12))}:00-${df2.format(random.nextInt(12))}:00")

			return entries
		}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
	{
		root = inflater.inflate(R.layout.fragment_history, container, false)
		thread(true) { this.fillJournal() }
		return root
	}

	private fun fillJournal()
	{
		val journal = root!!.findViewById<RecyclerView>(R.id.view_journal)
		val adapter = JournalAdapter(randomJournalItems ?: arrayListOf())

		journal.post {
			// Set adapter and layout
			journal.adapter       = adapter
			journal.layoutManager = LinearLayoutManager(context)
			// Hide loading
			root!!.findViewById<ProgressBar>(R.id.progress_loading_journal).visibility = View.GONE
		}
	}
}