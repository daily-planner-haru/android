package com.haru.android.login.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haru.android.login.repository.NaverRepository

class NaverViewModel : ViewModel() {

    // Fix Me: handle suitable error code
    private var loginResult:MutableLiveData<Int> = MutableLiveData()
    // TODO : get JWT from naver

    fun login(context: Context){
        NaverRepository.login(context)
    }
}