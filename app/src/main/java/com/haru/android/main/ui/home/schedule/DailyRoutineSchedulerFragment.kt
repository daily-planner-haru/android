package com.haru.android.main.ui.home.schedule

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.haru.android.R
import com.haru.android.main.ui.home.schedule.placeholder.PlaceholderContent
import com.haru.android.mockup.MockEventQueue
import java.util.ArrayList

/**
 * A fragment representing a list of Items.
 */
class DailyRoutineSchedulerFragment : Fragment() {

    private var columnCount = 1
    private val values: MutableList<PlaceholderContent.PlaceholderItem> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_daily_routine_scheduler_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                MockEventQueue.getEvents().forEach{ event ->
                    values.add(PlaceholderContent.PlaceholderItem(event.id, event.title, event.description))
                }
                adapter = DailyRoutineSchedulerRecyclerViewAdapter(values)
            }
        }
        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            DailyRoutineSchedulerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}