package com.haru.android.Dailiy.DTO

import java.time.Duration

data class Event(
    val id: Long, // 고유 ID
    val title: String,
    val description: String,
    val location: String?,
    val isAllDay: Boolean,
    val category: String,
)

data class Reminder(
    val timeBeforeEvent: Duration,
    val type: ReminderType
)

enum class ReminderType {
    POPUP, EMAIL
}