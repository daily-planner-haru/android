package com.haru.android.ui.horizontal_date_picker

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import com.haru.android.R
import org.joda.time.DateTime

/**
 * Created by jhonn on 22/02/2017.
 */
class HorizontalPickerAdapter(
    private val itemWidth: Int,
    private val listener: OnItemClickedListener,
    context: Context?,
    daysToCreate: Int,
    offset: Int,
    private val mBackgroundColor: Int,
    private val mDateSelectedColor: Int,
    private val mDateSelectedTextColor: Int,
    private val mTodayDateTextColor: Int,
    private val mTodayDateBackgroundColor: Int,
    private val mDayOfWeekTextColor: Int,
    private val mUnselectedDayTextColor: Int
) : RecyclerView.Adapter<HorizontalPickerAdapter.ViewHolder>() {
    private val items: ArrayList<Day> = ArrayList()
    init {
        generateDays(daysToCreate, DateTime().minusDays(offset).millis, false)
    }


    private fun generateDays(n: Int, initialDate: Long, cleanArray: Boolean) {
        if (cleanArray) items.clear()
        for (i in 0 until n) {
            val actualDate = DateTime(initialDate + DAY_MILLIS * i)
            items.add(Day(actualDate))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun getItem(position: Int): Day {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvDay: TextView = itemView.findViewById(R.id.tvDay)
        var tvWeekDay: TextView = itemView.findViewById(R.id.tvWeekDay)

        init {
            tvDay = itemView.findViewById<View>(R.id.tvDay) as TextView
            tvDay.width = itemWidth
            tvWeekDay = itemView.findViewById<View>(R.id.tvWeekDay) as TextView
        }

        fun bind(item: Day) {
            tvDay.text = item.day
            tvWeekDay.text = item.weekDay
            tvWeekDay.setTextColor(mDayOfWeekTextColor)

            when {
                item.isSelected -> {
                    tvDay.background = getDaySelectedBackground(itemView)
                    tvDay.setTextColor(mDateSelectedTextColor)
                }
                item.isToday -> {
                    tvDay.background = getDayTodayBackground(itemView)
                    tvDay.setTextColor(mTodayDateTextColor)
                }
                else -> {
                    tvDay.setBackgroundColor(mBackgroundColor)
                    tvDay.setTextColor(mUnselectedDayTextColor)
                }
            }

            itemView.setOnClickListener {
                listener.onClickView(it, adapterPosition)
            }
        }
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getDaySelectedBackground(view: View): Drawable {
        val drawable = view.resources.getDrawable(R.drawable.background_day_selected, null)
        DrawableCompat.setTint(drawable, mDateSelectedColor)
        return drawable
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun getDayTodayBackground(view: View): Drawable {
        val drawable = view.resources.getDrawable(R.drawable.background_day_today, null)
        if (mTodayDateBackgroundColor != -1) {
            DrawableCompat.setTint(drawable, mTodayDateBackgroundColor)
        }
        return drawable
    }
    companion object {
        private const val DAY_MILLIS: Long = AlarmManager.INTERVAL_DAY
    }
}