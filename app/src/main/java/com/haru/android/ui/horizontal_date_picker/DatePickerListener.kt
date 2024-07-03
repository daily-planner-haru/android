package com.haru.android.ui.horizontal_date_picker

import org.joda.time.DateTime

/**
 * Created by jhonn on 02/03/2017.
 */
interface DatePickerListener {
    fun onDateSelected(dateSelected: DateTime?)
}