package com.haru.android.main.ui.home.schedule

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.haru.android.Dailiy.DTO.DailyResponse
import com.haru.android.R
import com.haru.android.main.ui.home.AdderListener
import com.haru.android.main.ui.home.schedule.placeholder.PlaceholderContent
import com.haru.android.main.ui.home.schedule.placeholder.PlaceholderContent.ITEMS
import com.haru.android.mockup.MockEventQueue
import com.haru.android.ui.horizontal_date_picker.OnItemClickedListener
import java.util.ArrayList
import java.util.HashMap

/**
 * A fragment representing a list of Items.
 */
class DailySchedulerFragment : Fragment(), OnItemClickedListener {
    private lateinit var adapter: DailySchedulerRecyclerViewAdapter
    private var listener: AdderListener? = null
    private var columnCount = 1
    private val values: MutableList<PlaceholderContent.PlaceholderItem> = ArrayList()
    private val viewModel: DailySchedulerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }

        // 3초 지연을 추가
        Handler(Looper.getMainLooper()).postDelayed({
            // 지연 후에 실제 작업 수행
            createFragment()
        }, 3000)
    }

    private fun createFragment() {
        viewModel.events.observe(this, Observer { response ->
            when (response) {
                is DailyResponse.Success -> {
                    values.clear()
                    response.data?.forEach { daily ->
                        values.add(
                            PlaceholderContent.PlaceholderItem(
                                daily.hashCode().toLong(),
                                daily.name,
                                daily.startDate.toString()
                            )
                        )
                    }
                    adapter.notifyDataSetChanged()
                }
                is DailyResponse.Loading -> {
                    // Show loading indicator if needed
                }
                is DailyResponse.Error -> {
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_daily_scheduler_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.list)
        // Set the adapter
        if (recyclerView is RecyclerView) {
            with(recyclerView) {
                val decoration = DividerItemDecoration(this.context, RecyclerView.VERTICAL)
                addItemDecoration(decoration)
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                values.add(PlaceholderContent.createPlaceholderItem(0));
                // TODO: move View Model
//                MockEventQueue.getEvents().forEach { event ->
//                    values.add(
//                        PlaceholderContent.PlaceholderItem(
//                            event.id,
//                            event.title,
//                            event.description
//                        )
//                    )
//                }
                adapter = DailySchedulerRecyclerViewAdapter(values, this@DailySchedulerFragment)
                this.adapter = adapter
//                viewModel.events.observe(viewLifecycleOwner, Observer { response ->
//                    when (response) {
//                        is DailyResponse.Success -> {
//                            values.clear()
//                            response.data?.forEach { daily ->
//                                values.add(
//                                    PlaceholderContent.PlaceholderItem(
//                                        1,
//                                        daily.name,
//                                        "hahaha"
//                                    )
//                                )
//                            }
////                            (adapter as DailySchedulerRecyclerViewAdapter).notifyDataSetChanged()
//                        }
//                        is DailyResponse.Loading -> {
//                            // Show loading indicator if needed
//                        }
//                        is DailyResponse.Error -> {
//                            Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                })
//                adapter = DailySchedulerRecyclerViewAdapter(ITEMS, listener)
            }
        }
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            DailySchedulerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (parentFragment is AdderListener) {
            listener = parentFragment as AdderListener
        }
    }
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onClickView(v: View?, adapterPosition: Int) {
        val item = values[adapterPosition]
        Toast.makeText(context, "Clicked: ${item.content}", Toast.LENGTH_SHORT).show()
    }
}