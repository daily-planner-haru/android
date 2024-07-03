package com.haru.android.main.ui.home.schedule

import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import com.haru.android.R

import com.haru.android.main.ui.home.schedule.placeholder.PlaceholderContent.PlaceholderItem
import com.haru.android.databinding.FragmentDailyRoutineSchedulerBinding
import com.haru.android.databinding.MainScheduleHeaderBinding

/**
 * [RecyclerView.Adapter] that can display a [PlaceholderItem].
 * TODO: Replace the implementation with code for your data type.
 */

class DailyRoutineSchedulerRecyclerViewAdapter(
    private val values: List<PlaceholderItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        private const val TYPE_HEADER = 0;
        private const val TYPE_ITEM = 1;
        private const val TYPE_FOOTER = 2;
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            values.size - 1 -> TYPE_FOOTER
            else -> TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER ->
                HeaderViewHolder(
                    MainScheduleHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ))
            TYPE_ITEM ->
                ItemViewHolder(
                    FragmentDailyRoutineSchedulerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ))
            TYPE_FOOTER ->
                FooterViewHolder(
                    MainScheduleHeaderBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    ))
            else -> throw IllegalArgumentException("Invalid view type")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                val item = values[position]
                holder.contentView.text = item.content
                holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        val spannable = SpannableString(holder.contentView.text)
                        spannable.setSpan(StrikethroughSpan(), 0, holder.contentView.text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        holder.contentView.text = spannable
                    } else {
                        holder.contentView.text = item.content
                    }
                }
            }
            is HeaderViewHolder -> {
                holder.headerTextView.text = holder.headerTextView.context.getString(R.string.main_daily_routine_schedule_title)
                holder.headerTextView.textSize = holder.headerTextView.context.resources.getDimension(R.dimen.main_schedule_title_font_size)
            }
            is FooterViewHolder -> {
                // TODO: Impl
                holder.footerTextView.text = holder.footerTextView.context.getString(R.string.main_daily_routine_schedule_title)
                holder.footerTextView.textSize = holder.footerTextView.context.resources.getDimension(R.dimen.main_schedule_title_font_size)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ItemViewHolder(binding: FragmentDailyRoutineSchedulerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val timeView: TextView =  binding.itemTime
        val contentView: TextView = binding.content
        val checkBox: CheckBox = binding.checkBox
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    inner class HeaderViewHolder(binding: MainScheduleHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val headerTextView = binding.header
    }

    inner class FooterViewHolder(binding: MainScheduleHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val footerTextView = binding.header
    }

}