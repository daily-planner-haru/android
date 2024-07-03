package com.haru.android.Dailiy.repository

import com.haru.android.Dailiy.DTO.Daily
import com.haru.android.Dailiy.DTO.DailyResponse
import com.haru.android.Dailiy.service.DailyAPIService
import com.haru.android.Dailiy.service.DailyService
import org.joda.time.DateTime

class DailyRepositoryImpl(
    private val dailyService: DailyAPIService
): DailyRepository {
    override suspend fun createDaily(daily: Daily): DailyResponse<Daily> {
        val response = dailyService.createDaily(daily)
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return DailyResponse.Success(result)
            }
        }
        return DailyResponse.Error(response.message())
    }

    override suspend fun getDaily(id: Long): DailyResponse<Daily> {
        val response = dailyService.getDaily(id)
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return DailyResponse.Success(result)
            }
        }
        return DailyResponse.Error(response.message())
    }

    override suspend fun getDailyByDay(date: DateTime): DailyResponse<List<Daily>> {
        val response = dailyService.getDailyByDay(date)
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return DailyResponse.Success(result)
            }
        }
        return DailyResponse.Error(response.message())
    }

    override suspend fun getDailyByWeek(date: DateTime): DailyResponse<List<List<Daily>>> {
        val response = dailyService.getDailyByWeek(date)
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return DailyResponse.Success(result)
            }
        }
        return DailyResponse.Error(response.message())
    }

    override suspend fun getDailyByRange(
        startDate: DateTime,
        endDate: DateTime
    ): DailyResponse<List<List<Daily>>> {
        // TODO: FIX ME(change to range)
        val response = dailyService.getDailyByRange(startDate, endDate)
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return DailyResponse.Success(result);
            }
        }
        return DailyResponse.Error(response.message());
    }

    override suspend fun updateDaily(id: Long, daily: Daily): DailyResponse<Daily> {
        val response = dailyService.updateDaily(id,daily)
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return DailyResponse.Success(result)
            }
        }
        return DailyResponse.Error(response.message())
    }

    override suspend fun deleteDaily(id: Long): DailyResponse<Void> {
        val response = dailyService.deleteDaily(id)
        if(response.isSuccessful) {
            response.body()?.let { result ->
                return DailyResponse.Success(result)
            }
        }
        return DailyResponse.Error(response.message())
    }
}