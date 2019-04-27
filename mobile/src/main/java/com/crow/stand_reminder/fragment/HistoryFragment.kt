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
                entries.add(JournalEntry(context!!,
                        String.format(Locale.getDefault(), "%s-%s-%s",
                                df4.format((random.nextInt(100) + 2000).toLong()),
                                df2.format(random.nextInt(12).toLong()),
                                df2.format(random.nextInt(31).toLong())),
                        random.nextInt(24),
                        String.format("%s:00-%s:00",
                                df2.format(random.nextInt(12).toLong()),
                                df2.format(random.nextInt(12).toLong()))))

            return entries
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        root = inflater.inflate(R.layout.fragment_history, container, false)
        Thread(Runnable { this.fillJournal() }).start()
        return root
    }

    private fun fillJournal()
    {
        val journal = root!!.findViewById<RecyclerView>(R.id.view_journal)
        val adapter = JournalAdapter(randomJournalItems)

        journal.post {
            // Set adapter and layout
            journal.adapter       = adapter
            journal.layoutManager = LinearLayoutManager(context)
            // Hide loading
            root!!.findViewById<ProgressBar>(R.id.progress_loading_journal).visibility = View.GONE
        }
    }
}