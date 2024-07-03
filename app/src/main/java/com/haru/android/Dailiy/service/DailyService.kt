package com.haru.android.Dailiy.service

import com.haru.android.Dailiy.DTO.Daily
import org.joda.time.DateTime
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

object DailyService {
    // TODO : add global haru http address
    private const val DailyBaseURL ="/oauth"
    private val retrofit = Retrofit.Builder()
        .baseUrl(DailyBaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: DailyAPIService = retrofit.create(DailyAPIService::class.java)
}

interface DailyAPIService {
    @POST("create")
    suspend fun createDaily(@Body daily: Daily): Response<Daily>
    @GET("get/{id]")
    suspend fun getDaily(@Path("id") id: Long): Response<Daily>
    @GET("api/daily/day")
    suspend fun getDailyByDay(@Query("date") date: DateTime): Response<List<Daily>>
    @GET("api/daily/week")
    suspend fun getDailyByWeek(@Query("date") date: DateTime): Response<List<List<Daily>>>
    @GET("get/range/{range]")
    suspend fun getDailyByRange(
        @Query("startDate") startDate: DateTime,
        @Query("endDate") endDate: DateTime
    ): Response<List<List<Daily>>>
    @PUT("{id}")
    suspend fun updateDaily(@Path("id") id: Long, @Body daily: Daily): Response<Daily>
    @DELETE("{id}")
    suspend fun deleteDaily(@Path("id") id: Long): Response<Void>
}


