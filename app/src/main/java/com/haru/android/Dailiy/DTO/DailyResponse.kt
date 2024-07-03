package com.haru.android.Dailiy.DTO

sealed class DailyResponse<T> (
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T? = null): DailyResponse<T>(data)
    class Loading<T>(data: T? = null): DailyResponse<T>(data)
    class Error<T>(message: String, data: T? = null): DailyResponse<T>(data, message)
}
