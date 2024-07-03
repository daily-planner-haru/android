package com.haru.android.ui.horizontal_date_picker

/**
 * Created by jhonn on 02/03/2017.
 */
interface HorizontalPickerListener {
    fun onStopDraggingPicker()
    fun onDraggingPicker()
    fun onDateSelected(item: Day?)
}