package com.crow.stand_reminder.fragment

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.GridView
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.crow.stand_reminder.R
import com.crow.stand_reminder.list.VerticalItemSpacing
import com.crow.stand_reminder.list.hourChip.HourChip
import com.crow.stand_reminder.list.hourChip.HourChipAdapter
import com.crow.stand_reminder.list.hourChip.HourChipListAdapter
import com.crow.stand_reminder.list.hourEntry.HourEntryAdapter

import java.text.DecimalFormat
import java.util.ArrayList
import java.util.Locale
import java.util.Random

class HomeFragment : Fragment()
{
	private var root: View? = null

	private var progressBarCircular: ProgressBar? = null

	private val randomHourChips: ArrayList<HourChip>
		get()
		{
			val chips  = arrayListOf<HourChip>()
			val random = Random()

			for (i in 0..23)
				if (random.nextInt(2) == 0)
					chips += HourChip("${DecimalFormat("00").format(i)}:00")

			return chips
		}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
	{
		root = inflater.inflate(R.layout.fragment_home, container, false)

		progressBarCircular = root!!.findViewById(R.id.progress_circular)

		// Testing
		setProgress(40)
		fillHourChipsAsGridView()

		return root
	}

	private fun fillHourChips()
	{
		val hourChips       = root!!.findViewById<RecyclerView>(R.id.list_hour_chips)
		val hourChipAdapter = HourChipAdapter(randomHourChips)

		// For spacing
		// TODO: Auto set span count
		val layoutManager  = GridLayoutManager(context, 5)
		val itemDecoration = VerticalItemSpacing((8 * HourEntryAdapter.densityDpi).toInt())

		hourChips.addItemDecoration(itemDecoration)
		hourChips.adapter       = hourChipAdapter
		hourChips.layoutManager = layoutManager
	}

	private fun fillHourChipsAsGridView()
	{
		val hourChips = root!!.findViewById<GridView>(R.id.list_hour_chips_grid)
		val adapter   = HourChipListAdapter(randomHourChips)

		hourChips.adapter = adapter
	}

	private fun setProgress(progress: Int)
	{
		ObjectAnimator.ofInt(progressBarCircular!!, "progress", progressBarCircular!!.progress, progress).apply {
			duration = 2000
			interpolator = AccelerateDecelerateInterpolator()
			start()
		}
	}
}