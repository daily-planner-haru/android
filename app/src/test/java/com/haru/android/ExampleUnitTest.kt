package com.haru.android

import com.haru.android.Dailiy.DTO.Daily
import com.haru.android.Dailiy.DTO.DailyResponse
import com.haru.android.Dailiy.repository.DailyRepository
import com.haru.android.Dailiy.repository.DailyRepositoryImpl
import com.haru.android.Dailiy.service.DailyAPIService
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.HttpUrl
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.joda.time.DateTime
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.ArgumentMatchers.anyLong
import org.mockito.Mockito
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    private lateinit var mockApiService: DailyAPIService
    private lateinit var dailyRepository: DailyRepository
    private val exampleDaily = Daily(name = "Example", startDate = DateTime.now(), startHour = null, endHour = null, isChecked = false)

    @Before
    fun setUp() {
        mockApiService = Mockito.mock(DailyAPIService::class.java)
        dailyRepository = DailyRepositoryImpl(mockApiService)
    }

    @Test
    fun `getDaily returns Success when API call is successful`() = runBlockingTest {
        val id = 1L
        Mockito.`when`(mockApiService.getDaily(id)).thenReturn(Response.success(exampleDaily))

        val result = dailyRepository.getDaily(id)

        assert(result is DailyResponse.Success)
        assert((result as DailyResponse.Success).data == exampleDaily)
    }
    @Test
    fun `getDaily returns Error when API call is unsuccessful`() = runBlockingTest {
        val id = 1L
        val errorMessage = "Not Found"
        Mockito.`when`(mockApiService.getDaily(anyLong())).thenReturn(
            Response.error(404, okhttp3.ResponseBody.create(null, "Not Found"))
        )
        val result = dailyRepository.getDaily(id)
        assert(result is DailyResponse.Error)
    }
}