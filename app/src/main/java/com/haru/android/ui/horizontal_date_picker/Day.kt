package com.haru.android.ui.horizontal_date_picker

import org.joda.time.DateTime;
import java.util.Locale

data class Day(private var date: DateTime, var isSelected: Boolean = false) {

    private var monthPattern = "MMMM YYYY"

    val day: String
        get() = date.dayOfMonth.toString()

    val weekDay: String
        get() = date.toString("EEE", Locale.getDefault()).uppercase(Locale.getDefault())

    fun getMonth(): String = getMonth("")

    fun getMonth(pattern: String): String {
        if (pattern.isNotEmpty())
            monthPattern = pattern

        return date.toString(monthPattern, Locale.getDefault())
    }

    fun getDate(): DateTime? {
        return date
    }

    val dateWithoutTime: DateTime
        get() = date.withTime(0, 0, 0, 0)

    val isToday: Boolean
        get() {
            val today = DateTime().withTime(0, 0, 0, 0)
            return dateWithoutTime.millis == today.millis
        }
}

