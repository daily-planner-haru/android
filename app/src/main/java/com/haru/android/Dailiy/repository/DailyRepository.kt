package com.haru.android.Dailiy.repository

import com.haru.android.Dailiy.DTO.Daily
import com.haru.android.Dailiy.DTO.DailyResponse
import org.joda.time.DateTime

interface DailyRepository {
    suspend fun createDaily(daily: Daily): DailyResponse<Daily>
    suspend fun getDaily(id: Long): DailyResponse<Daily>
    suspend fun getDailyByDay(date: DateTime): DailyResponse<List<Daily>>
    suspend fun getDailyByWeek(date: DateTime): DailyResponse<List<List<Daily>>>
    suspend fun getDailyByRange(startDate: DateTime, endDate: DateTime): DailyResponse<List<List<Daily>>>
    suspend fun updateDaily(id: Long, daily: Daily): DailyResponse<Daily>
    suspend fun deleteDaily(id: Long): DailyResponse<Void>
}