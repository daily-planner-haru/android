package com.haru.android.login.api

import com.haru.android.login.DTO.LoginWithKakaoRequest
import com.haru.android.login.DTO.LoginWithKakaoResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

object LoginRetrofit {
    // TODO : add global haru http address
    private val loginBaseURL ="/oauth"
    private val retrofit = Retrofit.Builder()
        .baseUrl(loginBaseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val apiService: KakaoLoginInterface = retrofit.create(KakaoLoginInterface::class.java)
    // TODO : implement interface login & signup
}

interface KakaoLoginInterface {
    @POST("/oauth/kakao/singin")
    fun login(@Body request: LoginWithKakaoRequest): Call<LoginWithKakaoResponse>
}