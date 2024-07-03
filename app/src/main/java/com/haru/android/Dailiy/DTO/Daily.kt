package com.haru.android.Dailiy.DTO

import org.joda.time.DateTime

data class Daily(
    val name:String,
    val startDate: DateTime,
    val startHour: DateTime?,
    val endHour: DateTime?,
    var isChecked: Boolean)
